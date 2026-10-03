# Just Enough Juices

![Just Enough Juices](art/logo/just-enough-juices-logo.png)

*Juice it. Sip it. Feel fine.*

A Forge mod for **Minecraft 1.18.2** that turns fruit, vegetables and a few stranger
ingredients into a full line of **juices** — each one handing you a set of custom
powers, with **boosted** versions that push them further.

[![Bottle icon](art/logo/just-enough-juices-bottle-128.png)](art/logo/just-enough-juices-bottle-512.png)

---

## What's in the glass?

- **20 juices**, each with a normal and a **boosted** version (40 items).
- **Custom power system** — juices grant the mod's own effects, not vanilla potion
  effects. Hover any bottle in-game to see what it does.
- **Juice Press** — a machine that juices ingredients for you.
- **Four wild berry bushes** that grow around the world.
- **Fruit powers** — Orchard Guard, Frostbite, Solar Charge and Forager's Luck.
- **Emerald Dust**, villager trades, advancements, composting and more.

---

## Recipes

Every recipe in the mod is shown in-game with **JEI (Just Enough Items)**. Install
JEI and open it to see how any juice, boosted bottle, **Juice Booster**, or the
**Juice Press** is made.

The **Juice Press** takes a fruit (or a base juice), a milk bucket, and either
a glass bottle (normal) or a **Juice Booster** (boosted), then outputs the finished
bottle. It takes **5 seconds** per bottle and works with hoppers.

---

## The Juices

Durations and amplifier levels are shown in-game on each bottle's tooltip. Effects
marked with a roman numeral are stronger on the boosted bottle.

### Fruit & vegetable juices

| Juice (base ingredient) | Powers |
|-------------------------|--------|
| **Apple** *(Apple)* | Night Vision · boosted adds Regeneration |
| **Potato** *(Baked Potato)* | Night Vision + Haste · boosted adds stronger Haste |
| **Carrot** *(Carrot)* | Strength · boosted adds Resistance |
| **Melon** *(Melon Slice)* | Jump Boost · boosted adds stronger Jump Boost |
| **Pumpkin** *(Pumpkin)* | Invisibility · boosted lasts longer |
| **Sweet Berry** *(Sweet Berries)* | Speed · boosted adds stronger Speed |
| **Wild Berry** *(Wild Berry)* | Absorption · boosted adds Strength |
| **Ice Berry** *(Ice Berry)* | Icy Foot · boosted adds Water Breathing |
| **Sun Berry** *(Sun Berry)* | Caffeinated + Speed · boosted adds Jump Boost |
| **Beetroot** *(Beetroot)* | Saturation · boosted adds Resistance |
| **Cocoa** *(Cocoa Beans)* | Haste · boosted adds Night Vision |
| **Nether Wart** *(Nether Wart)* | Fire Resistance · boosted adds Strength |
| **Spicy** *(Cactus)* | Spicy aura + Fire Resistance · boosted adds Strength |
| **Dried Kelp** *(Dried Kelp)* | Dolphin's Grace · boosted adds Water Breathing |
| **Chorus** *(Chorus Fruit)* | Levitation + Slow Falling · boosted adds stronger Slow Falling |
| **Glow Berry** *(Glow Berry)* | Glowing + Night Vision · boosted adds Float |
| **Golem** *(Iron Ingot)* | Absorption + Resistance + Slowness · boosted is stronger |

### Treasure juices

| Juice (base ingredient) | Powers |
|-------------------------|--------|
| **Glistering Melon** *(Glistering Melon Slice)* | Regeneration + Slow Falling + Speed · boosted adds Saturation |
| **Golden Apple** *(Golden Apple)* | Absorption + Fire Resistance + Night Vision · boosted adds Regeneration + Resistance |
| **Golden Carrot** *(Golden Carrot)* | Night Vision + Water Breathing + Dolphin's Grace · boosted adds Luck |

> The **Golem** line is intentionally heavy — it grants a lot of Absorption and
> Resistance at the cost of Slowness.

---

## Fruit Powers

These four powers are central to the mod and support the berry bushes. They scale
with the boosted bottles.

| Power | Found on | What it does |
|-------|----------|--------------|
| **Orchard Guard** | Apple | Reduces incoming damage by 15–40% (void damage is not reduced). |
| **Frostbite** | Ice Berry | When hit, chills the attacker — Slowness plus freezing. |
| **Solar Charge** | Sun Berry | In daylight under open sky, regenerates 1–1.5 HP every 2 seconds. |
| **Forager's Luck** | Wild Berry | Harvest 1–2 extra berries from the mod's bushes. |

## Custom effects

The mod's own effects, applied by its juices:

| Effect | What it does |
|--------|--------------|
| **Icy Foot** | Freezes the water under your feet into frosted ice, like Frost Walker. |
| **Caffeinated** | Speed and Haste while active; when the juice ends it leaves a short **Caffeine Crash** (Slowness + Mining Fatigue). |
| **Spicy** | Sets nearby mobs on fire (15% chance per second) and constantly extinguishes you. |
| **Float** | Lifts you gently into the air and slows your fall. |
| **Magnet** | (Unused) pulls nearby items and XP toward you. |
| **Caffeine Crash** | The crash after Caffeinated: slower movement and mining. |
| **Chilled** | The slow applied to mobs that hit a Frostbite user. |

---

## Berry Bushes

Four bushes generate naturally in the world. Each grows in three stages (bonemeal
works) and can be harvested once ripe; harvesting resets them to regrow.

| Bush | Drops |
|------|-------|
| **Ice Berry Bush** | Ice Berry |
| **Wild Berry Bush** | Wild Berry |
| **Sun Berry Bush** | Sun Berry |
| **Glow Berry Bush** | Glow Berry |

Berries can be eaten, juiced, composted, and pressed into their juice. **Forager's
Luck** increases the harvest.

---

## Items

- **Glass Bottle** — the container for every juice.
- **Emerald Dust** — crafting material for the Juice Booster.
- **Juice Booster** — the catalyst for every boosted bottle.
- **Juice Press** — the juicing machine.

---

## Trades, compost & advancements

- Farmer, cleric and wandering traders can sell juices (can be disabled).
- Berries can be composted (chance and toggle are configurable).
- Three advancements: **First Juice**, **Juice Connoisseur**, **Golden Mastery**.

---

## Overdrink

Drinking too many juices in a short window gives you a penalty so juice isn't a
free spam. By default, after **4 drinks within 10 seconds** you get Nausea and
Hunger. Everything (toggle, drink count, window, duration) is configurable.

---

## Configuration

Options live in `config/jej-common.toml`:

| Key | Default | Description |
|-----|---------|-------------|
| `general.enableVillagerTrades` | `true` | Add juices to villager trades. |
| `general.enableComposting` | `true` | Allow berries in a composter. |
| `general.compostChance` | `0.3` | Composter fill chance for berries. |
| `general.returnGlassBottle` | `true` | Return an empty bottle after drinking. |
| `overdrink.enabled` | `true` | Enable the overdrink penalty. |
| `overdrink.maxDrinks` | `4` | Drinks allowed within the window. |
| `overdrink.windowTicks` | `200` | Time window (ticks) for counting drinks. |
| `overdrink.nauseaTicks` | `200` | Nausea duration. |
| `overdrink.hungerTicks` | `300` | Hunger duration. |
| `effects.durationMultiplier` | `1.0` | Scales every juice effect's duration. |

---

## Building from source

```bash
./gradlew build             # compile + package the mod
./gradlew runClient         # launch a dev client
./gradlew runData           # regenerate assets/data (lang, models, recipes)
./gradlew runGameTestServer # run the mod's game tests
```

The project targets Java 17 and uses ForgeGradle with official Mojang mappings.

---

## Branding

The logo, icon and bottle art live in [`art/logo/`](art/logo/) with editable SVGs.
The mod's `logo.png` is generated from the bottle icon. See
[`art/logo/README.md`](art/logo/README.md) for details and regeneration
instructions.

---

## Credits

Made by **Cheese Ez**. Thanks for downloading Just Enough Juices! :D
