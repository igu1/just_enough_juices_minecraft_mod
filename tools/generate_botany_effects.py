"""Import the three Blockbench plants/items and draw every registered effect icon."""
import base64
import copy
import json
import math
import re
from pathlib import Path
from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / "src/main/resources/assets/jej"
GENERATED = ROOT / "src/generated/resources/assets/jej"


def write(path, data):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(data, indent=2) + "\n")


def subtree(project, node):
    return [item for child in node["children"] for item in (subtree(project, child) if isinstance(child, dict) else [child])]


def tree(project):
    def walk(nodes):
        for node in nodes:
            if isinstance(node, dict):
                yield node
                yield from walk(node["children"])
    return {node["uuid"]: node for node in walk(project["outliner"])}


def textures(project, directory):
    result = {}
    for index, texture in enumerate(project["textures"]):
        name = texture["name"].removesuffix(".png")
        relative = f"{directory}/{name}"
        path = ASSETS / f"textures/{relative}.png"
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_bytes(base64.b64decode(texture["source"].split(",", 1)[1]))
        result[str(index)] = "jej:" + relative
    result["particle"] = next(iter(result.values()))
    return result


def element(source, offset):
    result = {"name": source["name"], "shade": source.get("shade", True)}
    for key in ("from", "to"):
        result[key] = [round(v + offset[i], 6) for i, v in enumerate(source[key])]
    rotation = source.get("rotation", [0, 0, 0])
    axes = [i for i, value in enumerate(rotation) if value]
    if axes:
        assert len(axes) == 1 and abs(rotation[axes[0]]) in (22.5, 45), "Unsupported Java cube rotation"
        result["rotation"] = {"origin": [v+offset[i] for i, v in enumerate(source["origin"])],
                              "axis": "xyz"[axes[0]], "angle": rotation[axes[0]], "rescale": source.get("rescale", False)}
    result["faces"] = {}
    for direction, face in source["faces"].items():
        if face.get("texture") is None or face.get("texture") is False: continue
        result["faces"][direction] = {"uv": face["uv"], "texture": "#" + str(face["texture"])}
        if face.get("rotation"): result["faces"][direction]["rotation"] = face["rotation"]
    return result


def bounds(elements):
    vertices = []
    for element in elements:
        for x in (element["from"][0], element["to"][0]):
            for y in (element["from"][1], element["to"][1]):
                for z in (element["from"][2], element["to"][2]):
                    point = [x, y, z]
                    if "rotation" in element:
                        rotation = element["rotation"]
                        axis = "xyz".index(rotation["axis"])
                        origin = rotation["origin"]
                        i, j = [(1, 2), (2, 0), (0, 1)][axis]
                        a, b = point[i]-origin[i], point[j]-origin[j]
                        t = math.radians(rotation["angle"])
                        point[i] = origin[i]+a*math.cos(t)-b*math.sin(t)
                        point[j] = origin[j]+a*math.sin(t)+b*math.cos(t)
                    vertices.append(point)
    return [round(min(v[i] for v in vertices), 6) for i in range(3)] + [round(max(v[i] for v in vertices), 6) for i in range(3)]


def botany():
    project = json.loads((ROOT / "art/blockbench/bushes.bbmodel").read_text())
    groups = {group["name"]: group for group in project["groups"]}
    branches = tree(project)
    cubes = {cube["uuid"]: cube for cube in project["elements"]}
    maps = textures(project, "block/botany")
    shapes = []
    for flavor in ("wildberry", "iceberry", "sunberry"):
        prefix = flavor.capitalize() + " Bush"
        flavor_shapes = []
        for age, stage in enumerate(("Young", "Growing", "Harvested", "Ripe")):
            group = groups[prefix + " - " + stage + " stage"]
            offset = [8-group["origin"][0], 0, 8-group["origin"][2]]
            elements = [element(cubes[uuid], offset) for uuid in subtree(project, branches[group["uuid"]]) if cubes[uuid].get("export", True)]
            local_maps = {**maps, "particle": maps[str(("wildberry", "iceberry", "sunberry").index(flavor)*2)]}
            write(ASSETS / f"models/block/{flavor}_bush_blockbench_stage{age}.json", {"ambientocclusion": False, "textures": local_maps, "elements": elements})
            flavor_shapes.append(bounds(elements))
        shapes.append(flavor_shapes)
    write(ASSETS / "botany_shapes.json", shapes)
    source = "package me.ez.jej.common;\n\nimport me.ez.jej.Init;\nimport net.minecraft.world.level.block.Block;\nimport net.minecraft.world.phys.shapes.VoxelShape;\n\n// Generated from the imported Blockbench stages.\npublic final class BushShapes {\n    private static final VoxelShape[][] SHAPES = {\n"
    for flavor in shapes:
        source += "        {" + ", ".join("Block.box(" + ", ".join(map(str, shape)) + ")" for shape in flavor) + "},\n"
    source += "    };\n    public static VoxelShape get(Block block, int age) {\n        int row = block == Init.WILD_BERRY_BUSH.get() ? 0 : block == Init.ICE_BERRY_BUSH.get() ? 1 : block == Init.SUN_BERRY_BUSH.get() ? 2 : -1;\n        return row < 0 ? null : SHAPES[row][age];\n    }\n}\n"
    (ROOT / "src/main/java/me/ez/jej/common/BushShapes.java").write_text(source)
    berries = json.loads((ROOT / "art/blockbench/berries.bbmodel").read_text())
    berry_maps = textures(berries, "item/botany")
    branches = tree(berries)
    cubes = {cube["uuid"]: cube for cube in berries["elements"]}
    for index, (group, item) in enumerate(zip(berries["groups"], ("wild_berry", "ice_berry", "sun_berry"))):
        offset = [8-group["origin"][0], 0, 8-group["origin"][2]]
        elements = [element(cubes[uuid], offset) for uuid in subtree(berries, branches[group["uuid"]])]
        write(ASSETS / f"models/item/{item}_blockbench.json", {"gui_light": "front", "ambientocclusion": False,
              "textures": {**berry_maps, "particle": berry_maps[str(index)]}, "elements": elements, "display": berries.get("display", {})})
        write(GENERATED / f"models/item/{item}.json", {"parent": f"jej:item/{item}_blockbench"})


def effects():
    init = (ROOT / "src/main/java/me/ez/jej/Init.java").read_text()
    names = re.findall(r'EFFECT.register\("([a-z_]+)"', init)
    color_map = {name: "#" + color[2:] for name, color in re.findall(r'EFFECT.register\("([a-z_]+)",\s*\(\) -> new [\w.]+\((0x[0-9a-fA-F]{6})', init)}
    palette = ["e46367", "ce5486", "ef983a", "c8a87b", "8ec363", "e48b36", "85dfea", "a778cc", "608b56", "f0cd68"]
    sheet = Image.new("RGBA", (180, math.ceil(len(names)/10)*18), "#142c32")
    for index, name in enumerate(names):
        rgb = color_map.get(name, "#" + palette[(index//2) % len(palette)])
        image = Image.new("RGBA", (18, 18), (0, 0, 0, 0))
        d = ImageDraw.Draw(image)
        d.rounded_rectangle((1, 1, 16, 16), radius=3, fill="#253f42", outline="#e8ca62" if "boosted" in name else "#82aea0")
        if name == "icyfooteffect":
            d.rounded_rectangle((4, 4, 7, 12), radius=1, fill="#bdf6ff")
            d.rounded_rectangle((10, 6, 13, 14), radius=1, fill="#bdf6ff")
        elif name == "frostbite":
            for x in (4, 8, 12): d.polygon([(x, 4), (x+2, 4), (x+1, 13)], fill="#bdf6ff")
            d.line((4, 5, 14, 5), fill="#eaffff")
        elif name.startswith("iceberry"):
            for a, b in [((4, 9), (13, 9)), ((9, 4), (9, 13)), ((5, 5), (12, 12)), ((5, 12), (12, 5))]: d.line((a, b), fill="#bdf6ff", width=1)
            d.rectangle((8, 8, 10, 10), fill="#eaffff")
        elif name in ("solar_charge", "caffeinated") or name.startswith("sunberry"):
            d.ellipse((6, 6, 12, 12), fill="#f3d75d")
            for x, y in [(9, 3), (9, 15), (3, 9), (15, 9), (4, 4), (14, 14), (4, 14), (14, 4)]: d.point((x, y), fill="#fff1ac")
            if name == "solar_charge":
                d.line((7, 9, 11, 9), fill="#fffdf0"); d.line((9, 7, 9, 11), fill="#fffdf0")
        elif name in ("orchard_guard", "golem_effect", "golem_boosted_effect"):
            d.polygon([(5, 4), (13, 4), (13, 10), (9, 14), (5, 10)], fill="#82b39b", outline="#e8efbd")
            d.line((9, 6, 9, 11), fill="#f4da85", width=2)
            if name == "orchard_guard": d.ellipse((7, 7, 11, 11), fill="#e46367", outline="#fff1bc")
        elif name == "foragers_luck":
            d.ellipse((3, 7, 8, 12), fill="#a778cc", outline="#e9d6ff")
            d.ellipse((7, 5, 12, 10), fill="#a778cc", outline="#e9d6ff")
            d.ellipse((7, 3, 12, 5), fill="#9ecc83")
            d.line((11, 12, 15, 12), fill="#f3d75d"); d.line((13, 10, 13, 14), fill="#f3d75d")
        elif name == "magnet":
            d.arc((4, 3, 14, 13), 0, 180, fill="#b992ee", width=3)
            d.rectangle((4, 5, 6, 9), fill="#e56670"); d.rectangle((12, 5, 14, 9), fill="#89c5ed")
        elif name == "float":
            d.polygon([(5, 12), (5, 8), (3, 8), (9, 3), (15, 8), (13, 8), (13, 12)], fill="#a5e5ee")
        elif name == "spicy" or name.startswith(("spicy", "netherwart")):
            d.polygon([(5, 13), (3, 9), (7, 5), (8, 9), (11, 3), (14, 9), (12, 14)], fill="#ed8240")
            d.polygon([(7, 13), (8, 9), (11, 10), (11, 13)], fill="#ffe597")
        else:
            d.ellipse((5, 6, 13, 13), fill=rgb, outline="#f4ddb6")
            d.line((9, 4, 9, 6), fill="#9ecc83", width=1)
            d.ellipse((10, 3, 13, 5), fill="#9ecc83")
            d.point((7, 8), fill="#fff5cc")
            # Distinct flavor sigils, independent of normal/boosted borders.
            for bit in range(5):
                if index & (1 << bit): d.point((5 + bit*2, 15), fill="#fff1bc")
        if "boosted" in name:
            d.line((13, 3, 15, 3), fill="#fff7bf"); d.line((14, 2, 14, 4), fill="#fff7bf")
        for bit in range(6):
            if index & (1 << bit): d.point((3 + (bit % 3)*2, 14 + bit//3), fill="#cad9bd")
        path = ASSETS / f"textures/mob_effect/{name}.png"
        path.parent.mkdir(parents=True, exist_ok=True)
        image.save(path)
        sheet.paste(image, ((index%10)*18, (index//10)*18), image)
    sheet.resize((720, sheet.height*4), Image.Resampling.NEAREST).save(ROOT / "art/effect_icons.png")


def generate():
    botany()
    effects()


if __name__ == "__main__": generate()
