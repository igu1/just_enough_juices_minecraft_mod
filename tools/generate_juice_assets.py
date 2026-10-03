"""Rebuild the AI-designed juice art and adapt the preserved Blockbench exports.

Requires Python 3 and Pillow. Run from any directory after editing art/ sources.
"""
import base64
import copy
import json
import random
import re
import gzip
import struct
import io
from pathlib import Path

from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / "src/main/resources/assets/jej"
GENERATED = ROOT / "src/generated/resources/assets/jej"
PROJECT = json.loads((ROOT / "art/blockbench/table.bbmodel").read_text())


def descendants(node):
    return [element for child in node["children"] for element in (descendants(child) if isinstance(child, dict) else [child])]


def nodes(project):
    def walk(outliner):
        for node in outliner:
            if isinstance(node, dict):
                yield node
                yield from walk(node["children"])
    return {node["uuid"]: node for node in walk(project["outliner"])}


def write_json(path, data):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(data, indent=2) + "\n")


def save_image(path, image):
    path.parent.mkdir(parents=True, exist_ok=True)
    image.save(path)


def slug(name):
    return re.sub(r"[^a-z0-9]+", "_", name.lower()).strip("_")


def table():
    materials = []
    particle = "jej:block/juice_table/" + slug(PROJECT["textures"][0]["name"])
    for texture in PROJECT["textures"]:
        name = slug(texture["name"])
        path = ASSETS / f"textures/block/juice_table/{name}.png"
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_bytes(base64.b64decode(texture["source"].split(",", 1)[1]))
        materials += [f"newmtl m_{texture['uuid']}", "Kd 1 1 1", "Ka 0 0 0", f"map_Kd jej:block/juice_table/{name}", ""]
    model_dir = ASSETS / "models/block"
    model_dir.mkdir(parents=True, exist_ok=True)
    (model_dir / "juice_table.mtl").write_text("\n".join(materials))
    lines = []
    object_index = 0
    for line in (ROOT / "art/blockbench/table.obj").read_text().splitlines():
        if line.startswith("mtllib "):
            line = "mtllib juice_table.mtl"
        elif line.startswith("o "):
            # Forge 1.18 only reads the first whitespace-delimited object token.
            # Unique names prevent objects such as all "Sunburst ..." parts overwriting each other.
            object_index += 1
            line = f"o part_{object_index}_{slug(line[2:])}"
        elif line.startswith("v "):
            x, y, z = map(float, line.split()[1:])
            # The new Blockbench project is already in its final 32x16 footprint.
            line = f"v {x:.8f} {y:.8f} {z:.8f}"
        lines.append(line)
    (model_dir / "juice_table.obj").write_text("\n".join(lines) + "\n")
    animation = PROJECT["animations"][0]
    moving = {}
    tree = nodes(PROJECT)
    for part, name in [("screw", "Press crank"), ("ram", "Press ram")]:
        group = next(g for g in PROJECT["groups"] if g["name"] == name)
        selected = set(descendants(tree[group["uuid"]]))
        moving[part] = {slug(e["name"]) for e in PROJECT["elements"] if e["uuid"] in selected}
    def filter_objects(wanted, include):
        active = False
        result = []
        for line in lines:
            if line.startswith("o "):
                active = (re.sub(r"^o part_\d+_", "", line) in wanted) == include
            if line.startswith(("o ", "usemtl ", "f ")) and not active: continue
            result.append(line)
        return result
    split_table(filter_objects(set.union(*moving.values()), False), model_dir)
    for part, names in moving.items():
        (model_dir / f"juice_table_{part}.obj").write_text("\n".join(filter_objects(names, True)) + "\n")
        write_json(model_dir / f"juice_table_{part}.json", {
            "loader": "forge:obj", "model": f"jej:models/block/juice_table_{part}.obj", "flip-v": True,
            "detectCullableFaces": False, "diffuseLighting": True, "ambientToFullbright": False,
            "textures": {"particle": particle}
        })
    animation_data(animation)
    # Keep inventory/hand rendering centered and compact, independent of the world footprint.
    item_lines = []
    for line in lines:
        if line.startswith("v "):
            x, y, z = map(float, line.split()[1:])
            line = f"v {(x - 1) * .5 + .5:.8f} {y * .5:.8f} {(z - .5) * .5 + .5:.8f}"
        item_lines.append(line)
    (model_dir / "juice_table_item.obj").write_text("\n".join(item_lines) + "\n")
    write_json(model_dir / "juice_table.json", {
        "loader": "forge:obj", "model": "jej:models/block/juice_table_item.obj",
        "flip-v": True, "detectCullableFaces": False, "diffuseLighting": True,
        "ambientToFullbright": False, "textures": {"particle": particle},
        "display": {
            "gui": {"rotation": [25, 225, 0], "translation": [0, 0, 0], "scale": [.85, .85, .85]},
            "ground": {"translation": [0, 3, 0], "scale": [.4, .4, .4]},
            "fixed": {"scale": [.6, .6, .6]},
            "thirdperson_righthand": {"rotation": [75, 45, 0], "translation": [0, 2.5, 0], "scale": [.375, .375, .375]},
            "firstperson_righthand": {"rotation": [0, 45, 0], "scale": [.5, .5, .5]}
        }
    })
    for part in ("left", "right"):
        write_json(model_dir / f"juice_table_{part}.json", {
            "loader": "forge:obj", "model": f"jej:models/block/juice_table_{part}.obj",
            "flip-v": True, "detectCullableFaces": False, "diffuseLighting": True,
            "ambientToFullbright": False, "textures": {"particle": particle}
        })
    write_json(ASSETS / "blockstates/juice_table.json", {"variants": {
        f"facing={direction},part={part}": {"model": f"jej:block/juice_table_{model_part}", "y": angle}
        for direction, angle in [("north", 0), ("east", 90), ("south", 180), ("west", 270)]
        for part, model_part in [("foot", "left"), ("head", "right")]
    }})
    write_json(GENERATED / "models/item/juice_table.json", {"parent": "jej:block/juice_table"})


def split_table(lines, model_dir):
    """Clip polygons at the seam, interpolating UVs/normals without losing mesh detail."""
    positions, uvs, normals = [], [], []
    outputs = {side: ["mtllib juice_table.mtl"] for side in ("left", "right")}
    counts = {side: 0 for side in outputs}
    for line in lines:
        if line.startswith("v "): positions.append(list(map(float, line.split()[1:])))
        elif line.startswith("vt "): uvs.append(list(map(float, line.split()[1:])))
        elif line.startswith("vn "): normals.append(list(map(float, line.split()[1:])))
        elif line.startswith(("o ", "usemtl ")):
            for output in outputs.values(): output.append(line)
        elif line.startswith("f "):
            vertices = []
            for index in line.split()[1:]:
                p, uv, n = map(int, index.split("/"))
                vertices.append(positions[p-1] + uvs[uv-1] + normals[n-1])
            for side, output in outputs.items():
                clipped = []
                previous = vertices[-1]
                inside = lambda v: v[0] <= 1 if side == "left" else v[0] >= 1
                for current in vertices:
                    if inside(previous) != inside(current):
                        t = (1 - previous[0]) / (current[0] - previous[0])
                        clipped.append([a + (b-a)*t for a, b in zip(previous, current)])
                    if inside(current): clipped.append(current)
                    previous = current
                if len(clipped) < 3: continue
                # Triangulate: Forge's OBJ loader only accepts triangles/quads.
                for i in range(1, len(clipped)-1):
                    triangle = [clipped[0], clipped[i], clipped[i+1]]
                    a, b, c = triangle
                    ab = [b[j]-a[j] for j in range(3)]
                    ac = [c[j]-a[j] for j in range(3)]
                    cross = [ab[1]*ac[2]-ab[2]*ac[1], ab[2]*ac[0]-ab[0]*ac[2], ab[0]*ac[1]-ab[1]*ac[0]]
                    if sum(v*v for v in cross) < 1e-20: continue
                    indices = []
                    for vertex in triangle:
                        x, y, z, u, v, nx, ny, nz = vertex
                        x -= 1 if side == "right" else 0
                        output.extend([f"v {x:.8f} {y:.8f} {z:.8f}", f"vt {u:.8f} {v:.8f}", f"vn {nx:.8f} {ny:.8f} {nz:.8f}"])
                        counts[side] += 1
                        index = counts[side]
                        indices.append(f"{index}/{index}/{index}")
                    output.append("f " + " ".join(indices))
    for side, output in outputs.items():
        (model_dir / f"juice_table_{side}.obj").write_text("\n".join(output) + "\n")


def animation_data(animation):
    screw = next(g for g in PROJECT["groups"] if g["name"] == "Press crank")
    ram = next(g for g in PROJECT["groups"] if g["name"] == "Press ram")
    def frames(group, channel):
        return sorted((frame for frame in animation["animators"][group["uuid"]]["keyframes"] if frame["channel"] == channel), key=lambda frame: frame["time"])
    rotation = frames(screw, "rotation")
    position = frames(screw, "position")
    ram_position = frames(ram, "position")
    assert [f["time"] for f in rotation] == [f["time"] for f in position] == [f["time"] for f in ram_position]
    assert all(f["interpolation"] == "linear" for f in rotation + position + ram_position)
    def values(frames, axis): return [float(f["data_points"][0][axis]) for f in frames]
    data = {"length": animation["length"], "times": [f["time"] for f in rotation], "rotation": values(rotation, "y"),
            "screw_position": values(position, "y"), "ram_position": values(ram_position, "y"),
            "screw_pivot": [v/16 for v in screw["origin"]], "ram_pivot": [v/16 for v in ram["origin"]]}
    write_json(ASSETS / "animations/juice_table.json", data)
    def array(values): return "{" + ", ".join(str(v) + "F" for v in values) + "}"
    # Deterministic generated constants avoid runtime dependence on an animation library.
    source = "package me.ez.jej.client;\n\n// Generated from art/blockbench/table.bbmodel; do not edit by hand.\npublic final class JuiceTableAnimation {\n"
    source += f"    public static final float LENGTH = {data['length']}F;\n"
    for key, value in data.items():
        if isinstance(value, list): source += f"    public static final float[] {key.upper()} = {array(value)};\n"
    source += "    public static float sample(float[] values, float time) {\n        time = Math.max(0, time) % LENGTH;\n        for (int i = 1; i < TIMES.length; i++) {\n            if (time <= TIMES[i]) return values[i-1] + (values[i] - values[i-1]) * (time - TIMES[i-1]) / (TIMES[i] - TIMES[i-1]);\n        }\n        return values[values.length-1];\n    }\n}\n"
    (ROOT / "src/main/java/me/ez/jej/client/JuiceTableAnimation.java").write_text(source)


COLORS = {
    "apple": "d84b35", "sweetberry": "cf365f", "carrot": "f08c29", "bakedpotato": "c7a064",
    "melon": "ed6d75", "pumpkin": "ce711d", "iceberry": "73cfe6", "wildberry": "793fb2",
    "driedkelp": "4c7940", "goldenapple": "edc545", "goldencarrot": "e3a732", "glistering_melon": "efd477",
    "chorus": "b479cf", "glowberry": "e5bb41", "spicy": "d84c24", "golem": "879a97",
    "sunberry": "f7d95c", "beetroot": "962d53", "netherwart": "8e3135", "cocoa": "8a593b"
}


def bottles():
    bottle = json.loads((ROOT / "art/blockbench/bottle.bbmodel").read_text())
    atlas = Image.open(io.BytesIO(base64.b64decode(bottle["textures"][0]["source"].split(",", 1)[1]))).convert("RGBA")
    save_image(ASSETS / "textures/item/juice_bottle_source.png", atlas)
    save_image(ASSETS / "textures/item/bottle_brass.png", atlas.crop((0, 16, 16, 32)))
    save_image(ASSETS / "textures/item/bottle_cork.png", atlas.crop((48, 0, 64, 16)))
    save_image(ASSETS / "textures/item/bottle_reflection.png", atlas.crop((16, 16, 32, 32)))
    elements = []
    for source in bottle["elements"]:
        element = {key: copy.deepcopy(source[key]) for key in ("name", "from", "to")}
        element["faces"] = {}
        for direction, face in source["faces"].items():
            # The source was atlased: ALL parts now use texture 22, not the old
            # per-material indices. Identify materials semantically instead.
            name = element["name"].lower()
            if any(word in name for word in ("wax", "ribbon")):
                texture = "seal"
            elif "label" in name: texture = "label"
            elif "juice" in name: texture = "liquid"
            elif "brass" in name: texture = "cap"
            elif "cork" in name: texture = "cork"
            elif any(word in name for word in ("reflection", "glint")): texture = "reflection"
            else: texture = "glass"
            element["faces"][direction] = {"uv": [0, 0, 16, 16], "texture": "#" + texture}
        elements.append(element)
    model = {
        "ambientocclusion": False, "gui_light": "front",
        "textures": {"particle": "#liquid", "liquid": "jej:item/apple_liquid", "label": "jej:item/apple_juice_label",
                     "seal": "jej:item/bottle_seal", "glass": "jej:item/bottle_glass_trim",
                     "cap": "jej:item/bottle_brass", "cork": "jej:item/bottle_cork", "reflection": "jej:item/bottle_reflection"},
        "elements": elements,
        "display": {
            "gui": {"rotation": [15, 210, 0], "translation": [0, 2, 0], "scale": [1.15, 1.15, 1.15]},
            "ground": {"translation": [0, 3, 0], "scale": [.5, .5, .5]},
            "fixed": {"translation": [0, 2, 0], "scale": [.85, .85, .85]},
            "thirdperson_righthand": {"rotation": [0, 0, 0], "translation": [0, 3, 0], "scale": [.65, .65, .65]},
            "thirdperson_lefthand": {"rotation": [0, 0, 0], "translation": [0, 3, 0], "scale": [.65, .65, .65]},
            "firstperson_righthand": {"rotation": [0, -20, 0], "translation": [1, 3, 1], "scale": [.8, .8, .8]},
            "firstperson_lefthand": {"rotation": [0, 20, 0], "translation": [1, 3, 1], "scale": [.8, .8, .8]}
        }
    }
    model["display"] = bottle["display"]
    write_json(ASSETS / "models/item/juice_bottle.json", model)
    empty = copy.deepcopy(model)
    empty["textures"].update(liquid="jej:item/empty_bottle_glass", label="jej:item/empty_bottle_glass", particle="jej:item/empty_bottle_glass")
    empty["elements"] = [e for e in empty["elements"] if not any(w in e["name"].lower() for w in ["label", "ribbon", "inside neck"]) ]
    write_json(ASSETS / "models/item/empty_juice_bottle.json", empty)
    write_json(GENERATED / "models/item/glass_bottle.json", {"parent": "jej:item/empty_juice_bottle"})
    glass = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    d = ImageDraw.Draw(glass)
    d.rectangle((0, 0, 15, 15), outline=(126, 177, 184, 255))
    d.line((2, 2, 2, 13), fill=(217, 245, 240, 255))
    d.line((3, 2, 6, 2), fill=(217, 245, 240, 255))
    save_image(ASSETS / "textures/item/empty_bottle_glass.png", glass)
    trim = atlas.crop((0, 0, 16, 16))
    save_image(ASSETS / "textures/item/bottle_glass_trim.png", trim)
    for boosted, color in [(False, "227d79"), (True, "eac44c")]:
        seal = Image.new("RGBA", (16, 16), "#" + color)
        d = ImageDraw.Draw(seal)
        for x in range(0, 16, 4): d.line((x, 0, x, 15), fill="#fff1ac" if boosted else "#49b0a1")
        save_image(ASSETS / f"textures/item/{'boosted_seal' if boosted else 'bottle_seal'}.png", seal)
    preview = Image.new("RGBA", (320, 128), "#162e32")
    for number, (flavor, hex_color) in enumerate(COLORS.items()):
        rgb = tuple(bytes.fromhex(hex_color))
        for boosted in (False, True):
            rng = random.Random(flavor + str(boosted))
            liquid = Image.new("RGBA", (16, 16))
            for y in range(16):
                for x in range(16):
                    shade = rng.randrange(-9, 10) + (14 if boosted else 0) + (10 if x < 3 else -10 if x > 12 else 0)
                    liquid.putpixel((x, y), tuple(max(0, min(255, c + shade)) for c in rgb) + (255,))
            d = ImageDraw.Draw(liquid)
            d.line((0, 0, 15, 0), fill=tuple(min(255, c + 40) for c in rgb) + (255,))
            if boosted:
                for x, y in [(4, 4), (11, 10), (7, 13)]:
                    d.line((x-1, y, x+1, y), fill="#fff4b0")
                    d.line((x, y-1, x, y+1), fill="#fff4b0")
            suffix = "_boosted" if boosted else ""
            save_image(ASSETS / f"textures/item/{flavor}_liquid{suffix}.png", liquid)
            label = Image.new("RGBA", (16, 16), "#f0e4be")
            d = ImageDraw.Draw(label)
            d.rectangle((0, 0, 15, 15), outline="#d7ad43" if boosted else "#765336", width=2)
            d.ellipse((4, 4, 11, 11), fill="#" + hex_color, outline="#503b30")
            d.rectangle((7, 2, 8, 4), fill="#4a7340")
            d.point((6, 6), fill="#fff2cf")
            # Distinct seal markings even for flavors with similar liquid colors.
            for bit in range(5):
                if number & (1 << bit): d.point((3 + bit * 2, 13), fill="#765336")
            name = flavor + "_juice" + suffix
            save_image(ASSETS / f"textures/item/{name}_label.png", label)
            write_json(GENERATED / f"models/item/{name}.json", {"parent": "jej:item/juice_bottle", "textures": {
                "liquid": f"jej:item/{flavor}_liquid{suffix}", "label": f"jej:item/{name}_label",
                "seal": "jej:item/boosted_seal" if boosted else "jej:item/bottle_seal"
            }})
            preview.paste(liquid.resize((32, 32), Image.Resampling.NEAREST), ((number % 10) * 32, (number // 10) * 64 + (32 if boosted else 0)))
    save_image(ROOT / "art/juice_palette.png", preview)


def gui():
    image = Image.new("RGBA", (256, 256), (0, 0, 0, 0))
    d = ImageDraw.Draw(image)
    # Cream enamel housing, brass edging, and a recessed teal work surface.
    d.rectangle((0, 0, 193, 189), fill="#193b39", outline="#102a29", width=2)
    d.rectangle((3, 3, 190, 186), fill="#a57540")
    d.rectangle((4, 4, 189, 185), fill="#d3ad65")
    d.line((5, 4, 188, 4), fill="#fff0bc")
    d.line((4, 5, 4, 184), fill="#f5df9d")
    d.rectangle((7, 7, 186, 182), fill="#e6d8b4")
    d.rectangle((10, 8, 183, 22), fill="#284e47")
    d.line((11, 9, 182, 9), fill="#547668")
    # A small citrus medallion replaces the title; no lettering is baked in.
    d.ellipse((89, 7, 104, 22), fill="#a57540")
    d.ellipse((91, 9, 102, 20), fill="#fff1bf")
    d.ellipse((92, 10, 101, 19), fill="#edab48")
    for end in ((96, 10), (101, 14), (96, 19), (92, 14)):
        d.line((96, 14, *end), fill="#fff1bf")
    d.polygon([(101, 8), (105, 5), (110, 5), (106, 9)], fill="#638855")
    for x1, x2 in ((18, 80), (113, 175)):
        d.line((x1, 14, x2, 14), fill="#718974")
        d.line((x1 + 8, 17, x2 - 8, 17), fill="#3d6154")
    d.rectangle((9, 25, 184, 91), fill="#a57540")
    d.rectangle((10, 26, 183, 90), fill="#224b47")
    d.line((11, 27, 182, 27), fill="#729183")
    d.rectangle((12, 29, 181, 88), fill="#35645a")
    d.line((13, 88, 180, 88), fill="#163e39")
    # Machine plumbing connects the inputs to a little juicer illustration.
    for x in (34, 70, 106):
        d.line((x, 69, x, 74), fill="#c5aa69", width=2)
    d.line((34, 74, 118, 74), fill="#c5aa69", width=2)
    d.line((118, 74, 118, 80), fill="#c5aa69", width=2)
    d.rectangle((111, 79, 126, 82), fill="#183f3a")
    d.rectangle((113, 79, 124, 80), fill="#e4c783")
    d.rectangle((113, 66, 124, 68), fill="#efdaa2")
    d.rectangle((117, 61, 120, 65), fill="#c5aa69")
    d.rectangle((115, 69, 122, 77), fill="#94b6a0")
    d.rectangle((116, 73, 121, 77), fill="#e8a448")
    d.line((116, 70, 116, 72), fill="#e1efcd")
    d.line((125, 70, 132, 70), fill="#c5aa69", width=2)
    d.line((132, 70, 132, 74), fill="#c5aa69")
    d.rectangle((131, 77, 133, 79), fill="#edb850")
    # Framed inventory tray and separate quick-access strip.
    d.rectangle((13, 104, 180, 162), fill="#c1ae82")
    d.line((14, 105, 179, 105), fill="#faf0cf")
    d.rectangle((13, 163, 180, 184), fill="#c1ae82")
    d.line((14, 164, 179, 164), fill="#faf0cf")
    for x1, x2 in ((16, 83), (110, 177)):
        d.line((x1, 97, x2, 97), fill="#bb9c64")
        d.line((x1, 98, x2, 98), fill="#fff0cd")
    d.polygon([(94, 94), (98, 94), (101, 97), (98, 100), (94, 100), (91, 97)], fill="#bf914a")
    d.rectangle((95, 96, 97, 98), fill="#f9e5af")
    # Brass corner screws, kept outside all clickable slots.
    for x in (7, 186):
        for y in (7, 86, 182):
            d.rectangle((x-1, y-1, x+2, y+2), fill="#a57540")
            d.rectangle((x, y, x+1, y+1), fill="#f5d991")
    menu = (ROOT / "src/main/java/me/ez/jej/common/JuiceTableMenu.java").read_text()
    coords = re.search(r"MACHINE_SLOTS = \{(.+?)\};", menu).group(1)
    machine_slots = [tuple(map(int, pair)) for pair in re.findall(r"\{(\d+), (\d+)\}", coords)]
    def slot(x, y):
        d.rectangle((x-1, y-1, x+16, y+16), fill="#182f31")
        d.rectangle((x, y, x+15, y+15), fill="#80988a")
        d.line((x, y+16, x+16, y+16), fill="#f4eaca")
        d.line((x+16, y, x+16, y+16), fill="#f4eaca")
    for x, y in machine_slots: slot(x, y)
    for row in range(3):
        for col in range(9): slot(17 + col*18, 108 + row*18)
    for col in range(9): slot(17 + col*18, 166)
    # Dark unfilled arrow and green/gold progress sprite at UV (0,192).
    for ox, oy, color in [(121, 36, "#203f3e"), (0, 192, "#edce72")]:
        d.polygon([(ox, oy+5), (ox+15, oy+5), (ox+15, oy+1), (ox+23, oy+8), (ox+15, oy+14), (ox+15, oy+10), (ox, oy+10)], fill=color)
    # Ingredient pictograms sit below, never inside, the unchanged item slots.
    d.rectangle((31, 56, 32, 58), fill="#b88b4c")
    d.polygon([(33, 57), (35, 54), (39, 54), (36, 57)], fill="#9bbe74")
    d.polygon([(28, 59), (31, 58), (34, 59), (37, 58), (40, 60), (39, 65), (36, 67), (31, 67), (28, 64)], fill="#eeac4f")
    d.line((29, 60, 29, 63), fill="#ffdb82")
    d.rectangle((66, 55, 73, 57), fill="#d6c79d")
    d.polygon([(66, 58), (73, 58), (75, 61), (74, 67), (65, 67), (64, 61)], fill="#f5ebcf")
    d.rectangle((65, 62, 74, 64), fill="#98b9a6")
    d.rectangle((104, 55, 107, 57), fill="#cda459")
    d.rectangle((104, 58, 107, 60), fill="#bad3b4")
    d.rectangle((102, 61, 109, 67), fill="#98b9a6")
    d.rectangle((103, 64, 108, 66), fill="#e5b557")
    d.line((103, 61, 103, 63), fill="#e9f0cc")
    # Sparkle marks the booster port, with no text or item-obscuring icon.
    d.polygon([(141, 68), (142, 71), (145, 72), (142, 73), (141, 76), (140, 73), (137, 72), (140, 71)], fill="#f2d687")
    d.point((136, 78), fill="#b6d1a3")
    save_image(ASSETS / "textures/gui/juice_table.png", image)
    save_image(ROOT / "art/juice_table_gui_preview.png", image.crop((0, 0, 194, 190)).resize((582, 570), Image.Resampling.NEAREST))


def test_structure():
    # Minimal 3x3x3 structure; no external NBT library needed for the GameTests.
    def name(value):
        encoded = value.encode()
        return struct.pack(">H", len(encoded)) + encoded
    def ints(values):
        return b"".join(struct.pack(">i", value) for value in values)
    data = b"\x0a\x00\x00"
    data += b"\x03" + name("DataVersion") + ints([2975])
    data += b"\x09" + name("size") + b"\x03" + ints([3, 3, 3, 3])
    for key in ("palette", "blocks", "entities"):
        data += b"\x09" + name(key) + b"\x0a" + ints([0])
    data += b"\x00"
    path = ROOT / "src/main/resources/data/jej/structures/empty.nbt"
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_bytes(gzip.compress(data, mtime=0))


if __name__ == "__main__":
    table()
    bottles()
    gui()
    test_structure()
    from generate_botany_effects import generate
    generate()
    print("Generated table OBJ/materials, 40 bottled juices, empty bottle, and coordinate-aligned GUI.")
