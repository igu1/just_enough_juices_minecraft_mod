"""Draw a candied-fruit Juice Booster texture. Run with Python 3 and Pillow."""
from pathlib import Path
from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
PALETTE = {
    ".": (0, 0, 0, 0),
    "K": "#123c31",  # forest-green outline
    "D": "#398547",  # fruit rind
    "G": "#78be58",  # fruit flesh
    "L": "#b9df75",  # juicy pulp
    "M": "#def3a8",  # pale pulp
    "O": "#b87524",  # syrup shadow
    "Y": "#ffc64b",  # golden syrup
    "W": "#fff3c4",  # sugar and pith
}
PIXELS = [
    "................",
    "....W...........",
    ".........W......",
    "..KKKKKKKKKKKK..",
    ".KYYYYWWYYYYYYK.",
    ".KYWWWWWWWWWWYK.",
    ".KYWMLWLLWLGWYK.",
    ".KYWLLWLWLLGWYK.",
    "..KYWLLWLLGWYK..",
    "..KYWLWWWLGWYK..",
    "...KYWLLLGWYK...",
    "....KYWWWYYK....",
    ".....KYYYOK.....",
    "......KYYK......",
    ".......KK.......",
    "................",
]


def main():
    image = Image.new("RGBA", (16, 16))
    for y, row in enumerate(PIXELS):
        assert len(row) == 16
        for x, pixel in enumerate(row):
            color = PALETTE[pixel]
            if isinstance(color, str):
                color = tuple(bytes.fromhex(color[1:])) + (255,)
            image.putpixel((x, y), color)
    texture = ROOT / "src/main/resources/assets/jej/textures/item/juice_booster.png"
    texture.parent.mkdir(parents=True, exist_ok=True)
    image.save(texture)
    preview = ROOT / "art/juice_booster_preview.png"
    image.resize((256, 256), Image.Resampling.NEAREST).save(preview)


if __name__ == "__main__":
    main()
