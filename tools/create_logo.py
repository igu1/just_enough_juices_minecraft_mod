"""Render original JEJ branding as transparent PNGs and editable SVGs.

Run with: python3 tools/create_logo.py (requires Pillow).
"""
from pathlib import Path
from html import escape
from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parents[1] / "art" / "logo"
INK = "#123c31"
CREAM = "#fff3c4"
GOLD = "#ffc64b"
ORANGE = "#f58a35"
MINT = "#8be3a3"

# Original hand-built 5x7 lettering; no external font dependencies.
FONT = {
    "J": ["11111", "00010", "00010", "00010", "10010", "10010", "01100"],
    "U": ["10001", "10001", "10001", "10001", "10001", "10001", "01110"],
    "S": ["01111", "10000", "10000", "01110", "00001", "00001", "11110"],
    "T": ["11111", "00100", "00100", "00100", "00100", "00100", "00100"],
    "E": ["11111", "10000", "10000", "11110", "10000", "10000", "11111"],
    "N": ["10001", "11001", "11001", "10101", "10011", "10011", "10001"],
    "O": ["01110", "10001", "10001", "10001", "10001", "10001", "01110"],
    "G": ["01111", "10000", "10000", "10111", "10001", "10001", "01111"],
    "H": ["10001", "10001", "10001", "11111", "10001", "10001", "10001"],
    "I": ["11111", "00100", "00100", "00100", "00100", "00100", "11111"],
    "C": ["01111", "10000", "10000", "10000", "10000", "10000", "01111"],
}


class Canvas:
    def __init__(self, width, height):
        self.width, self.height = width, height
        self.image = Image.new("RGBA", (width, height))
        self.draw = ImageDraw.Draw(self.image)
        self.svg = []

    def rect(self, x, y, w, h, color):
        self.draw.rectangle((x, y, x + w - 1, y + h - 1), fill=color)
        self.svg.append(f'<rect x="{x}" y="{y}" width="{w}" height="{h}" fill="{color}"/>')

    def polygon(self, points, color):
        self.draw.polygon(points, fill=color)
        pts = " ".join(f"{x},{y}" for x, y in points)
        self.svg.append(f'<polygon points="{pts}" fill="{color}"/>')

    def text(self, value, x, y, scale, color):
        cells = []
        for index, letter in enumerate(value):
            for row, line in enumerate(FONT[letter]):
                for col, bit in enumerate(line):
                    if bit == "1":
                        cells.append((x + index * 6 * scale + col * scale, y + row * scale))
        for dx, dy in [(8, 14), (0, 0)]:
            for px, py in cells:
                self.rect(px + dx - 7, py + dy - 7, scale + 14, scale + 14, INK)
        for px, py in cells:
            self.rect(px, py, scale, scale, color)
            if (py - y) // scale < 2:
                self.rect(px, py, scale, 3, CREAM)

    def save(self, name, title):
        self.image.save(ROOT / f"{name}.png")
        header = (f'<svg xmlns="http://www.w3.org/2000/svg" width="{self.width}" '
                  f'height="{self.height}" viewBox="0 0 {self.width} {self.height}" '
                  f'role="img" aria-label="{escape(title)}"><title>{escape(title)}</title>')
        (ROOT / f"{name}.svg").write_text(header + "\n" + "\n".join(self.svg) + "\n</svg>\n")


def sparkle(c, x, y, size=8):
    c.rect(x - size, y, size * 3, size, GOLD)
    c.rect(x, y - size, size, size * 3, GOLD)


def bottle(c, x, y, s=1):
    def r(a, b, w, h, color):
        c.rect(x + a * s, y + b * s, w * s, h * s, color)

    def p(points, color):
        c.polygon([(x + a * s, y + b * s) for a, b in points], color)

    # Thick stepped silhouette, cork, gold collar and mint glass.
    p([(65, 22), (151, 22), (151, 92), (184, 125), (200, 125),
       (200, 325), (184, 341), (32, 341), (16, 325), (16, 125),
       (32, 125), (65, 92)], INK)
    r(77, 34, 62, 36, "#bf7846")
    r(77, 34, 62, 10, "#edbc76")
    r(77, 80, 62, 28, "#c2f0d7")
    r(62, 70, 92, 15, GOLD)
    r(73, 73, 58, 5, CREAM)
    p([(77, 104), (139, 104), (172, 137), (188, 137), (188, 316),
       (176, 329), (40, 329), (28, 316), (28, 137), (44, 137)], "#75cda0")
    r(38, 157, 140, 159, ORANGE)
    r(38, 157, 140, 18, GOLD)
    r(154, 175, 24, 141, "#da5b31")
    r(41, 178, 13, 115, "#ffd77f")
    r(39, 130, 13, 22, "#d9ffe6")
    r(65, 113, 12, 21, "#d9ffe6")
    r(57, 202, 102, 82, INK)
    r(63, 208, 90, 70, CREAM)
    # Apple-shaped wax badge, with its own little leaf.
    r(78, 231, 60, 26, "#d94c42")
    r(84, 224, 48, 40, "#eb6650")
    r(98, 220, 16, 10, CREAM)
    r(102, 216, 6, 14, INK)
    r(108, 212, 18, 8, "#4e9e59")
    r(88, 231, 8, 12, "#ffaf80")
    r(60, 303, 74, 6, GOLD)
    # Two floating leaves make the bottle feel fresh, not like a potion.
    p([(149, 20), (169, 0), (211, 0), (211, 22), (191, 42), (149, 42)], INK)
    p([(159, 25), (178, 9), (200, 9), (200, 17), (184, 32), (159, 32)], MINT)
    p([(32, 69), (12, 50), (12, 28), (37, 28), (57, 49), (57, 69)], INK)
    p([(39, 58), (23, 44), (23, 38), (34, 38), (46, 51), (46, 58)], "#58ba72")


def main():
    ROOT.mkdir(parents=True, exist_ok=True)
    logo = Canvas(1200, 600)
    bottle(logo, 63, 116, 1)
    sparkle(logo, 44, 260)
    sparkle(logo, 292, 144, 10)
    sparkle(logo, 293, 445, 6)
    logo.text("JUST", 373, 113, 12, CREAM)
    logo.text("ENOUGH", 373, 222, 14, GOLD)
    logo.text("JUICES", 373, 354, 19, ORANGE)
    logo.rect(373, 508, 654, 8, INK)
    logo.rect(373, 508, 476, 5, MINT)
    sparkle(logo, 1079, 317)
    logo.save("just-enough-juices-logo", "Just Enough Juices")

    icon = Canvas(512, 512)
    icon.polygon([(48, 0), (464, 0), (512, 48), (512, 464),
                  (464, 512), (48, 512), (0, 464), (0, 48)], INK)
    icon.polygon([(48, 18), (464, 18), (494, 48), (494, 464),
                  (464, 494), (48, 494), (18, 464), (18, 48)], "#225445")
    bottle(icon, 148, 60, 1)
    icon.text("JEJ", 188, 427, 8, GOLD)
    sparkle(icon, 68, 252, 12)
    sparkle(icon, 425, 131, 12)
    sparkle(icon, 442, 385, 8)
    icon.save("just-enough-juices-icon", "Just Enough Juices bottle icon")

    bottle_icon = Canvas(384, 384)
    bottle(bottle_icon, 84, 21)
    bottle_icon.save("just-enough-juices-bottle", "Just Enough Juices bottle")
    for size in (512, 256, 128, 64, 32):
        resized = bottle_icon.image.resize((size, size), Image.Resampling.NEAREST)
        resized.save(ROOT / f"just-enough-juices-bottle-{size}.png")


if __name__ == "__main__":
    main()
