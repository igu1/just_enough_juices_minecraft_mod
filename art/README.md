# Juice table assets

`juice_table.bbmodel` preserves the original **Special Juice Making Table** project
exported from Blockbench. `juice_table.obj` is its unmodified geometry export.
The older source remains preserved separately.

The current exports are in `art/blockbench/`: bushes, berries, bottle, and table.
The table OBJ was exported in its default pose, not from a running animation frame.
The active table is **Minimal Juice Table - 2 Block**, with its single texture atlas.
Its square press head was replaced in Blockbench with an eight-sided round plate,
centered over the pot and using the same polygon profile at a smaller radius.
The original minimal project and previous artisan exports are preserved alongside it.
The separate **Juicing Extras** project is deliberately not exported or integrated.

Run `python3 tools/generate_juice_assets.py` to regenerate the resources (requires
Pillow). The generator uses the updated standalone bottle for all 40 juice variants,
including the cork, brass collar, labels and glass trims. Material tiles are extracted
from its atlas, preserving the new geometry and display transforms.
Normal and boosted liquids have separate textures; boosted bottles have gold seals.
The source bottle is texture-atlased (every face uses atlas index 22), so exported
item materials are assigned by part name, not by obsolete texture indices.

The table uses Forge's OBJ loader to keep all cube and mesh details. OBJ positions
are used without rescaling. Its authored dimensions are
32 × 29.12 × 16 Minecraft model units (32 units of render/collision clearance): two blocks wide, one deep, with the press
extending above the tabletop. The mesh is clipped at the center seam into left and
right block models with interpolated UVs. Texture images are extracted losslessly from
the saved Blockbench project. Bottle coordinates are already in the Java item's
0–16 coordinate space. The animated screw/crank and ram are removed from the static
halves and rendered by a block-entity renderer. The original four-second linear
keyframes/pivots are imported into `JuiceTableAnimation` and an animation manifest.
Progress packets animate the press even when its inventory screen is closed; it
returns to its default pose when processing stops. No animation library is needed.

## Plants and powers

The three bushes retain all four authored stages, rotations, UVs, and nine source
textures. Ages 0–3 map to young, growing, harvested, and ripe. Harvesting a ripe bush
returns it to the harvested model (age 2). Bonemeal and natural growth ripen it again.
Selection shapes are generated from the rotated geometry. The three harvested
berry items retain their pixel-extruded models, textures, and display settings.
The existing glowberry assets remain unchanged because no new version was supplied.

There are 49 registered effect icons (45 existing + 4 new), all 18 × 18 PNGs in
`assets/jej/textures/mob_effect/`. New powers are granted by normal/boosted juices:

| Juice | Power | Normal / boosted |
| --- | --- | --- |
| Apple | Orchard Guard | 15% / 30% damage reduction; never protects against void damage |
| Iceberry | Frostbite | Chill and slow attackers for 4 / 6 seconds |
| Sunberry | Solar Charge | Heal 0.5 / 1 health every 2 seconds with daylight, open sky, and no rain |
| Wildberry | Forager's Luck | Harvest 1 / 2 extra berries |

Normal powers last 60 seconds; boosted powers last 120 seconds, adjusted by the
existing effect-duration config. Existing juice effects and recipes remain intact.

## Inventory

The 194 × 190 screen uses a 256 × 256 texture. Coordinates are relative to the
screen's top-left corner and denote the item origin (slot borders start one pixel
earlier). `JuiceTableMenu.MACHINE_SLOTS` is also read by the art generator.

| Index | Purpose | X | Y |
| --- | --- | --- | --- |
| 0 | Fruit / base juice | 26 | 35 |
| 1 | Milk bucket | 62 | 35 |
| 2 | Empty juice bottle / emerald dust | 98 | 35 |
| 3 | Finished juice (output only) | 152 | 35 |
| 4 | Returned buckets (output only) | 152 | 65 |

Player inventory starts at `(17, 108)`, with 18-pixel spacing; hotbar starts at
`(17, 166)`. The 24 × 16 progress sprite is at texture UV `(0, 192)` and drawn at
screen `(121, 36)`. Server processing takes 100 ticks (5 seconds), without fuel.

Both halves open the same inventory; only the left/master half has a block entity.
Placement needs room for the second half to the player's left (the model's right).
Breaking either half removes both, drops the table once, and drops stored items.
Old single-block tables should be emptied, broken, and placed again after updating.

Hoppers connect to the master half and insert fruit/base juice from above and milk/bottles/dust from the sides;
they extract finished juice and buckets from below. Inventory and progress survive
world reloads; breaking the block drops its contents. A comparator reads fullness.
Existing crafting recipes remain available, and the table matches those recipes
through the recipe manager rather than duplicating a hardcoded ingredient list.

## Verification

- `python3 tools/verify_juice_assets.py`: texture/model coverage, OBJ geometry and
  indices, two-block bounds/parts, bottle material assignments, and alignment of all 41 menu slots.
- `JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 bash ./gradlew runGameTestServer --offline`:
  all 40 recipes, saved progress/inventory, full outputs, invalid inputs, and hopper
  capability lifecycle, shift-click, and paired placement/breaking in all orientations.
- `JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 bash ./gradlew runClient -PjejAssetSmokeTest=true --offline`:
  real-client model baking and atlas checks, including all imported growth stages,
  animated geometry, berry/bottle models, and every effect icon. The opt-in client
  closes itself after writing `build/reports/asset-smoke.json`; normal play is unaffected.
- `bash ./gradlew build --offline`: compile and package the mod.

The GUI, palette, and effect-icon previews are in this directory. Client asset loading
is tested automatically; an interactive world/multiplayer visual review is separate.
In this environment, Gradle sometimes loses its daemon after a launched game exits;
use the game's test results/client report to distinguish passed tests from that
post-shutdown harness error. The standalone packaging build runs successfully.
