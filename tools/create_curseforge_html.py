"""Generate a self-contained CurseForge description HTML page.

Images are embedded as base64 data URIs so the file renders on its own.
Run with: python3 tools/create_curseforge_html.py
"""
import base64
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / "curseforge-description.html"


def data_uri(path: Path) -> str:
    return "data:image/png;base64," + base64.b64encode(path.read_bytes()).decode("ascii")


LOGO = data_uri(ROOT / "art/logo/just-enough-juices-logo.png")
BOTTLE = data_uri(ROOT / "art/logo/just-enough-juices-bottle-256.png")

JUICES = [
    ("Apple Juice", "Apple", "Night Vision &middot; boosted adds Regeneration"),
    ("Potato Juice", "Baked Potato", "Night Vision + Haste &middot; boosted adds stronger Haste"),
    ("Carrot Juice", "Carrot", "Strength &middot; boosted adds Resistance"),
    ("Melon Juice", "Melon Slice", "Jump Boost &middot; boosted adds stronger Jump Boost"),
    ("Pumpkin Juice", "Pumpkin", "Invisibility &middot; boosted lasts longer"),
    ("Sweet Berry Juice", "Sweet Berries", "Speed &middot; boosted adds stronger Speed"),
    ("Wild Berry Juice", "Wild Berry", "Absorption &middot; boosted adds Strength"),
    ("Ice Berry Juice", "Ice Berry", "Icy Foot &middot; boosted adds Water Breathing"),
    ("Sun Berry Juice", "Sun Berry", "Caffeinated + Speed &middot; boosted adds Jump Boost"),
    ("Beetroot Juice", "Beetroot", "Saturation &middot; boosted adds Resistance"),
    ("Cocoa Juice", "Cocoa Beans", "Haste &middot; boosted adds Night Vision"),
    ("Nether Wart Juice", "Nether Wart", "Fire Resistance &middot; boosted adds Strength"),
    ("Spicy Juice", "Cactus", "Spicy aura + Fire Resistance &middot; boosted adds Strength"),
    ("Dried Kelp Juice", "Dried Kelp", "Dolphin's Grace &middot; boosted adds Water Breathing"),
    ("Chorus Juice", "Chorus Fruit", "Levitation + Slow Falling &middot; boosted adds stronger Slow Falling"),
    ("Glow Berry Juice", "Glow Berry", "Glowing + Night Vision &middot; boosted adds Float"),
    ("Golem Juice", "Iron Ingot", "Absorption + Resistance + Slowness &middot; boosted is stronger"),
]

TREASURE = [
    ("Glistering Melon Juice", "Glistering Melon Slice", "Regeneration + Slow Falling + Speed &middot; boosted adds Saturation"),
    ("Golden Apple Juice", "Golden Apple", "Absorption + Fire Resistance + Night Vision &middot; boosted adds Regeneration + Resistance"),
    ("Golden Carrot Juice", "Golden Carrot", "Night Vision + Water Breathing + Dolphin's Grace &middot; boosted adds Luck"),
]

POWERS = [
    ("Orchard Guard", "Apple", "Reduces incoming damage by 15&ndash;40% (void damage is not reduced)."),
    ("Frostbite", "Ice Berry", "When hit, chills the attacker &mdash; Slowness plus freezing."),
    ("Solar Charge", "Sun Berry", "In daylight under open sky, regenerates 1&ndash;1.5 HP every 2 seconds."),
    ("Forager's Luck", "Wild Berry", "Harvest 1&ndash;2 extra berries from the mod's bushes."),
]

EFFECTS = [
    ("Icy Foot", "Freezes the water under your feet into frosted ice, like Frost Walker."),
    ("Caffeinated", "Speed and Haste while active; the juice ends with a short Caffeine Crash."),
    ("Spicy", "Sets nearby mobs on fire (15% chance per second) and constantly extinguishes you."),
    ("Float", "Lifts you gently into the air and slows your fall."),
    ("Caffeine Crash", "The crash after Caffeinated: slower movement and mining."),
    ("Chilled", "The slow applied to mobs that hit a Frostbite user."),
    ("Magnet", "Reserved for future use: pulls nearby items and XP toward you."),
]

BUSHES = [
    ("Ice Berry Bush", "Ice Berry"),
    ("Wild Berry Bush", "Wild Berry"),
    ("Sun Berry Bush", "Sun Berry"),
    ("Glow Berry Bush", "Glow Berry"),
]

CONFIG = [
    ("enableVillagerTrades", "true", "Add juices to villager trades."),
    ("enableComposting", "true", "Allow berries in a composter."),
    ("compostChance", "0.3", "Composter fill chance for berries."),
    ("returnGlassBottle", "true", "Return an empty bottle after drinking."),
    ("overdrink.enabled", "true", "Enable the overdrink penalty."),
    ("overdrink.maxDrinks", "4", "Drinks allowed within the window."),
    ("overdrink.windowTicks", "200", "Time window for counting drinks."),
    ("effects.durationMultiplier", "1.0", "Scales every juice effect's duration."),
]


def juice_rows(rows):
    return "\n".join(
        f'      <tr><td><strong>{name}</strong></td><td>{base}</td><td>{powers}</td></tr>'
        for name, base, powers in rows
    )


def two_col(rows):
    return "\n".join(
        f'      <tr><td><strong>{a}</strong></td><td>{b}</td></tr>' for a, b in rows
    )


def three_col(rows):
    return "\n".join(
        f'      <tr><td><strong>{a}</strong></td><td>{b}</td><td>{c}</td></tr>' for a, b, c in rows
    )


HTML = f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Just Enough Juices</title>
</head>
<body style="font-family:-apple-system,Segoe UI,Roboto,Helvetica,Arial,sans-serif;color:#22332c;line-height:1.55;max-width:900px;margin:0 auto;">

  <p style="text-align:center;margin:8px 0;">
    <img src="{LOGO}" alt="Just Enough Juices" style="max-width:100%;width:640px;height:auto;">
  </p>

  <h1 style="text-align:center;margin:0 0 4px;">Just Enough Juices</h1>
  <p style="text-align:center;font-style:italic;font-size:1.15em;margin:0 0 16px;color:#2e7d4f;">
    Juice it. Sip it. Feel fine.
  </p>

  <p style="text-align:center;">
    <img src="{BOTTLE}" alt="Juice Booster" style="width:96px;height:96px;">
  </p>

  <p>A Forge mod for <strong>Minecraft 1.18.2</strong> that turns fruit, vegetables and a few
  stranger ingredients into a full lineup of <strong>juices</strong>. Every bottle hands you a set of
  custom powers, and <strong>boosted</strong> bottles push them further. No brewing stand required &mdash;
  just juice.</p>

  <h2 style="color:#1d6b46;border-bottom:2px solid #d8ead9;padding-bottom:4px;">What's inside</h2>
  <ul>
    <li><strong>20 juices</strong>, each with a normal and a <strong>boosted</strong> version (40 items).</li>
    <li><strong>Custom power system</strong> &mdash; juices grant the mod's own effects, not vanilla potion effects.</li>
    <li><strong>Juice Press</strong> &mdash; a machine that juices ingredients for you.</li>
    <li><strong>Four wild berry bushes</strong> that grow around the world.</li>
    <li><strong>Fruit powers</strong> &mdash; Orchard Guard, Frostbite, Solar Charge and Forager's Luck.</li>
    <li><strong>Juice Booster</strong>, villager trades, advancements, composting and more.</li>
  </ul>

  <h2 style="color:#1d6b46;border-bottom:2px solid #d8ead9;padding-bottom:4px;">Recipes</h2>
  <p>Every recipe in the mod is shown in-game with <strong>JEI (Just Enough Items)</strong>.
  Install JEI and open it to see how any juice, boosted bottle, <strong>Juice Booster</strong>,
  or the <strong>Juice Press</strong> is made.</p>
  <p>The <strong>Juice Press</strong> takes a fruit (or a base juice), a milk bucket, and
  either a glass bottle (normal) or a <strong>Juice Booster</strong> (boosted), then outputs the
  finished bottle. It takes <strong>5 seconds</strong> per bottle and works with hoppers.</p>

  <h2 style="color:#1d6b46;border-bottom:2px solid #d8ead9;padding-bottom:4px;">The Juices</h2>
  <h3>Fruit &amp; vegetable juices</h3>
  <table style="border-collapse:collapse;width:100%;">
    <thead>
      <tr>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Juice</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Ingredient</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Powers</th>
      </tr>
    </thead>
    <tbody>
{juice_rows(JUICES)}
    </tbody>
  </table>

  <h3>Treasure juices</h3>
  <table style="border-collapse:collapse;width:100%;">
    <thead>
      <tr>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Juice</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Ingredient</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Powers</th>
      </tr>
    </thead>
    <tbody>
{juice_rows(TREASURE)}
    </tbody>
  </table>

  <h2 style="color:#1d6b46;border-bottom:2px solid #d8ead9;padding-bottom:4px;">Fruit Powers</h2>
  <table style="border-collapse:collapse;width:100%;">
    <thead>
      <tr>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Power</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Found on</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">What it does</th>
      </tr>
    </thead>
    <tbody>
{three_col(POWERS)}
    </tbody>
  </table>

  <h2 style="color:#1d6b46;border-bottom:2px solid #d8ead9;padding-bottom:4px;">Custom effects</h2>
  <table style="border-collapse:collapse;width:100%;">
    <thead>
      <tr>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Effect</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">What it does</th>
      </tr>
    </thead>
    <tbody>
{two_col(EFFECTS)}
    </tbody>
  </table>

  <h2 style="color:#1d6b46;border-bottom:2px solid #d8ead9;padding-bottom:4px;">Berry Bushes</h2>
  <p>Four bushes generate naturally. Each grows in three stages (bonemeal works) and
  regrows after harvesting. Berries can be eaten, juiced, composted and pressed.</p>
  <table style="border-collapse:collapse;width:100%;">
    <thead>
      <tr>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Bush</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Drops</th>
      </tr>
    </thead>
    <tbody>
{two_col(BUSHES)}
    </tbody>
  </table>

  <h2 style="color:#1d6b46;border-bottom:2px solid #d8ead9;padding-bottom:4px;">Overdrink &amp; configuration</h2>
  <p>Drink too many juices too fast and you'll get a penalty. By default, after
  <strong>4 drinks within 10 seconds</strong> you get Nausea and Hunger. Everything is
  configurable in <code>config/jej-common.toml</code>.</p>
  <table style="border-collapse:collapse;width:100%;">
    <thead>
      <tr>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Option</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Default</th>
        <th style="text-align:left;padding:6px 10px;border:1px solid #d8ead9;background:#eef7ee;">Description</th>
      </tr>
    </thead>
    <tbody>
{three_col(CONFIG)}
    </tbody>
  </table>

  <h2 style="color:#1d6b46;border-bottom:2px solid #d8ead9;padding-bottom:4px;">Credits</h2>
  <p>Made by <strong>Cheese Ez</strong>. Thanks for downloading Just Enough Juices! :D</p>

</body>
</html>
"""

OUT.write_text(HTML, encoding="utf-8")
print(f"Wrote {OUT} ({len(HTML):,} chars, images embedded as base64)")
