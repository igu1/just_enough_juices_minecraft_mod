"""Verify juice model coverage, texture references, OBJ indices, and GUI alignment."""
import json
import re
from pathlib import Path

from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
MAIN = ROOT / "src/main/resources"
GENERATED = ROOT / "src/generated/resources"


def resource(kind, location, extension):
    namespace, path = location.split(":")
    relative = Path("assets") / namespace / kind / (path + extension)
    for root in (MAIN, GENERATED):
        if (root / relative).is_file():
            return root / relative
    raise AssertionError(f"Missing {kind}: {location}")


def model(location):
    data = json.loads(resource("models", location, ".json").read_text())
    if "parent" in data:
        parent = model(data["parent"])
        return {**parent, **data, "textures": {**parent.get("textures", {}), **data.get("textures", {})}}
    return data


init = (ROOT / "src/main/java/me/ez/jej/Init.java").read_text()
juices = re.findall(r'ITEMS.register\("([a-z_]+)",\s*\(\) -> new JuiceClass', init)
assert len(juices) == 40, f"Expected 40 registered juices, found {len(juices)}"
liquids = set()
labels = set()
for juice in juices:
    data = model("jej:item/" + juice)
    assert len(data["elements"]) == 22, f"{juice}: expected complete table bottle"
    textures = data["textures"]
    for texture in textures.values():
        if texture.startswith("#"):
            texture = textures[texture[1:]]
        resource("textures", texture, ".png")
    liquids.add(textures["liquid"])
    labels.add(textures["label"])
    for element in data["elements"]:
        name = element["name"].lower()
        expected = "seal" if any(word in name for word in ("wax", "ribbon")) else "label" if "label" in name else "liquid" if "juice" in name else "cap" if "brass" in name else "cork" if "cork" in name else "reflection" if any(word in name for word in ("glint", "reflection")) else "glass"
        assert all(face["texture"] == "#" + expected for face in element["faces"].values()), f"Wrong material on {juice}: {name}"
        for bound in ("from", "to"):
            assert all(0 <= value <= 16 for value in element[bound]), f"Bottle outside item bounds: {juice}"
assert len(liquids) == len(labels) == 40, "Variants must have distinct liquid and label textures"

obj = resource("models", "jej:block/juice_table", ".obj").read_text()
objects = re.findall(r"^o (.+)$", obj, re.MULTILINE)
assert len(objects) == len(set(objects)) and all(" " not in name for name in objects), "Forge OBJ objects would overwrite each other"
materials = set(re.findall(r"^usemtl (.+)$", obj, re.MULTILINE))
mtl = resource("models", "jej:block/juice_table", ".mtl").read_text()
assert materials <= set(re.findall(r"^newmtl (.+)$", mtl, re.MULTILINE))
for texture in re.findall(r"^map_Kd (.+)$", mtl, re.MULTILINE):
    resource("textures", texture, ".png")
counts = [len(re.findall(r"^" + token + r" ", obj, re.MULTILINE)) for token in ("v", "vt", "vn")]
for face in re.findall(r"^f (.+)$", obj, re.MULTILINE):
    for vertex in face.split():
        for index, count in zip(vertex.split("/"), counts):
            assert not index or 1 <= int(index) <= count, "Invalid OBJ vertex index"
for vertex in re.findall(r"^v (.+)$", obj, re.MULTILINE):
    x, y, z = map(float, vertex.split())
    assert 0 <= x <= 2 and 0 <= y <= 2 and 0 <= z <= 1, "Table exceeds authored bounds"
assert obj.count("\nf ") == (ROOT / "art/blockbench/table.obj").read_text().count("\nf "), "Lost table geometry"
for part in ("left", "right"):
    half = resource("models", f"jej:block/juice_table_{part}", ".obj").read_text()
    for vertex in re.findall(r"^v (.+)$", half, re.MULTILINE):
        x, y, z = map(float, vertex.split())
        assert 0 <= x <= 1 and 0 <= y <= 2 and 0 <= z <= 1, "Half crosses block seam"
    half_counts = [len(re.findall(r"^" + token + r" ", half, re.MULTILINE)) for token in ("v", "vt", "vn")]
    for face in re.findall(r"^f (.+)$", half, re.MULTILINE):
        assert len(face.split()) == 3
        for vertex in face.split():
            assert all(1 <= int(index) <= count for index, count in zip(vertex.split("/"), half_counts))
states = json.loads(resource("blockstates", "jej:juice_table", ".json").read_text())["variants"]
assert len(states) == 8, "Missing two-block orientation variants"
for state in states.values(): model(state["model"])
for part in ("screw", "ram"):
    data = model(f"jej:block/juice_table_{part}")
    assert data["loader"] == "forge:obj"
    moving = resource("models", f"jej:block/juice_table_{part}", ".obj").read_text()
    assert "\nf " in moving, "Missing animated geometry"
animation = json.loads(resource("animations", "jej:juice_table", ".json").read_text())
assert animation["length"] == 4 and animation["times"] == [0, 1.75, 2.25, 4]
assert animation["rotation"] == [0, 540, 540, 0] and animation["ram_position"] == [0, -.6, -.6, 0]
source_table = json.loads((ROOT / "art/blockbench/table.bbmodel").read_text())
plate = next(e for e in source_table["elements"] if e["name"] == "Rounded eight-sided pressing plate")
pot = next(e for e in source_table["elements"] if e["name"] == "Low-poly eight-sided press barrel")
assert plate["type"] == "mesh" and len(plate["faces"]) == 24
assert plate["origin"][0::2] == pot["origin"][0::2], "Press head must be centered over the pot"
for name, vertex in plate["vertices"].items():
    assert all(abs(vertex[i] - pot["vertices"][name][i] * scale) < 1e-5 for i, scale in enumerate((.7, .225, .7))), "Press head must match the pot's round profile"
for flavor in ("wildberry", "iceberry", "sunberry"):
    for age in range(4):
        data = model(f"jej:block/{flavor}_bush_blockbench_stage{age}")
        assert len(data["elements"]) >= 12
        for texture in data["textures"].values(): resource("textures", texture, ".png")
for berry in ("wild_berry", "ice_berry", "sun_berry"):
    data = model("jej:item/" + berry)
    assert len(data["elements"]) >= 30
    for texture in data["textures"].values(): resource("textures", texture, ".png")
effect_names = re.findall(r'EFFECT.register\("([a-z_]+)"', init)
assert len(effect_names) == 49
effect_pixels = set()
for effect in effect_names:
    icon = Image.open(resource("textures", "jej:mob_effect/" + effect, ".png"))
    assert icon.size == (18, 18) and icon.getbbox(), "Invalid effect icon: " + effect
    effect_pixels.add(icon.tobytes())
assert len(effect_pixels) == 49, "Effects should have distinguishable icons"

gui = Image.open(resource("textures", "jej:gui/juice_table", ".png"))
assert gui.size == (256, 256)
menu = (ROOT / "src/main/java/me/ez/jej/common/JuiceTableMenu.java").read_text()
machine = re.search(r"MACHINE_SLOTS = \{(.+?)\};", menu).group(1)
coords = [tuple(map(int, pair)) for pair in re.findall(r"\{(\d+), (\d+)\}", machine)]
coords += [(17 + col * 18, 108 + row * 18) for row in range(3) for col in range(9)]
coords += [(17 + col * 18, 166) for col in range(9)]
assert len(coords) == 41
for x, y in coords:
    assert gui.getpixel((x, y))[:3] == (128, 152, 138), f"GUI slot misaligned at {(x, y)}"
    assert x + 16 < 194 and y + 16 < 190
print("PASS: 40 bottles, two-block table + authored animation, 12 bush stages, 3 berry items, 49 effect icons, 41 slots.")
