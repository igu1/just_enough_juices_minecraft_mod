package me.ez.jej.Datagen;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.data.DataGenerator;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public LanguageProvider(DataGenerator gen, String locale) {
        super(gen, Main.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        //Item Group
        add("itemGroup." + "juices", "Juices");
        add(Init.ORCHARD_GUARD.get(), "Orchard Guard");
        add(Init.FROSTBITE.get(), "Frostbite");
        add(Init.SOLAR_CHARGE.get(), "Solar Charge");
        add(Init.FORAGERS_LUCK.get(), "Forager's Luck");
        add(Init.JUICE_TABLE.get(), "Juice Making Table");
        add("container.jej.juice_table", "Juice Making Table");
        add("container.jej.juice_table.slot0", "Fruit or base juice");
        add("container.jej.juice_table.slot1", "Milk bucket");
        add("container.jej.juice_table.slot2", "Empty juice bottle or emerald dust");
        add("container.jej.juice_table.slot3", "Finished juice");
        add("container.jej.juice_table.slot4", "Returned buckets");

        //Juice
        add(Init.APPLE_JUICE.get(), "Apple Juice");
        add(Init.APPLE_JUICE_BOOSTED.get(), "Apple Juice Boosted");
        add(Init.BAKEDPOTATO_JUICE.get(), "Potato Juice");
        add(Init.BAKEDPOTATO_JUICE_BOOSTED.get(), "Potato Juice Boosted");
        add(Init.CARROT_JUICE.get(), "Carrot Juice");
        add(Init.CARROT_JUICE_BOOSTED.get(), "Carrot Juice Boosted");
        add(Init.DRIEDKELP_JUICE.get(), "Dried Kelp Juice");
        add(Init.DRIEDKELP_JUICE_BOOSTED.get(), "Dried Kelp Juice Boosted");
        add(Init.GLISTERING_MELON_JUICE.get(), "Glistering Melon Juice");
        add(Init.GLISTERING_MELON_JUICE_BOOSTED.get(), "Glistering Melon Juice Boosted");
        add(Init.GOLDENAPPLE_JUICE.get(), "Golden Apple Juice");
        add(Init.GOLDENAPPLE_JUICE_BOOSTED.get(), "Golden Apple Juice Boosted");
        add(Init.GOLDENCARROT_JUICE.get(), "Golden Carrot Juice");
        add(Init.GOLDENCARROT_JUICE_BOOSTED.get(), "Golden Carrot Juice Boosted");
        add(Init.ICEBERRY_JUICE.get(), "Ice Berry Juice");
        add(Init.ICEBERRY_JUICE_BOOSTED.get(), "Ice Berry Juice Boosted");
        add(Init.MELON_JUICE.get(), "Melon Juice");
        add(Init.MELON_JUICE_BOOSTED.get(), "Melon Juice Boosted");
        add(Init.PUMPKIN_JUICE.get(), "Pumpkin Juice");
        add(Init.PUMPKIN_JUICE_BOOSTED.get(), "Pumpkin Juice Boosted");
        add(Init.WILDBERRY_JUICE.get(), "Wild Berry Juice");
        add(Init.WILDBERRY_JUICE_BOOSTED.get(), "Wild Berry Juice Boosted");
        add(Init.SWEETBERRY_JUICE.get(), "Sweet Berry Juice");
        add(Init.SWEETBERRY_JUICE_BOOSTED.get(), "Sweet Berry Juice Boosted");

        //New Juices
        add(Init.CHORUS_JUICE.get(), "Chorus Juice");
        add(Init.CHORUS_JUICE_BOOSTED.get(), "Chorus Juice Boosted");
        add(Init.GLOWBERRY_JUICE.get(), "Glow Berry Juice");
        add(Init.GLOWBERRY_JUICE_BOOSTED.get(), "Glow Berry Juice Boosted");
        add(Init.SPICY_JUICE.get(), "Spicy Juice");
        add(Init.SPICY_JUICE_BOOSTED.get(), "Spicy Juice Boosted");
        add(Init.GOLEM_JUICE.get(), "Golem Juice");
        add(Init.GOLEM_JUICE_BOOSTED.get(), "Golem Juice Boosted");
        add(Init.SUNBERRY_JUICE.get(), "Sun Berry Juice");
        add(Init.SUNBERRY_JUICE_BOOSTED.get(), "Sun Berry Juice Boosted");
        add(Init.BEETROOT_JUICE.get(), "Beetroot Juice");
        add(Init.BEETROOT_JUICE_BOOSTED.get(), "Beetroot Juice Boosted");
        add(Init.NETHERWART_JUICE.get(), "Nether Wart Juice");
        add(Init.NETHERWART_JUICE_BOOSTED.get(), "Nether Wart Juice Boosted");
        add(Init.COCOA_JUICE.get(), "Cocoa Juice");
        add(Init.COCOA_JUICE_BOOSTED.get(), "Cocoa Juice Boosted");

        //Bushes (berry items are BlockItems of the bushes, so they share the block translation key)
        add(Init.ICE_BERRY_BUSH.get(), "Ice Berry Bush");

        add(Init.WILD_BERRY_BUSH.get(), "Wild Berry Bush");

        add(Init.SUN_BERRY_BUSH.get(), "Sun Berry Bush");

        add(Init.GLOW_BERRY_BUSH.get(), "Glow Berry Bush");

        //Effect
        add(Init.ICYFOOTEFFECT.get(), "Icy Foot");
        add(Init.CAFFEINATED.get(), "Caffeinated");
        add(Init.SPICY.get(), "Spicy");
        add(Init.MAGNET.get(), "Magnet");
        add(Init.FLOAT.get(), "Float");
        add(Init.CAFFEINE_CRASH.get(), "Caffeine Crash");
        add(Init.CHILLED.get(), "Chilled");

        //Power descriptions shown in juice tooltips
        add("jej.power.night_vision", "Night Vision");
        add("jej.power.night_vision.desc", "See clearly in the dark.");
        add("jej.power.regeneration", "Regeneration");
        add("jej.power.regeneration.desc", "Slowly restores health.");
        add("jej.power.movement_speed", "Speed");
        add("jej.power.movement_speed.desc", "Move faster.");
        add("jej.power.damage_boost", "Strength");
        add("jej.power.damage_boost.desc", "Deal more melee damage.");
        add("jej.power.damage_resistance", "Resistance");
        add("jej.power.damage_resistance.desc", "Take less damage from most sources.");
        add("jej.power.dig_speed", "Haste");
        add("jej.power.dig_speed.desc", "Mine and break blocks faster.");
        add("jej.power.jump", "Jump Boost");
        add("jej.power.jump.desc", "Jump higher.");
        add("jej.power.invisibility", "Invisibility");
        add("jej.power.invisibility.desc", "Hide from mobs.");
        add("jej.power.water_breathing", "Water Breathing");
        add("jej.power.water_breathing.desc", "Breathe underwater without drowning.");
        add("jej.power.absorption", "Absorption");
        add("jej.power.absorption.desc", "Gain extra temporary hearts.");
        add("jej.power.dolphins_grace", "Dolphin's Grace");
        add("jej.power.dolphins_grace.desc", "Swim faster in water.");
        add("jej.power.slow_falling", "Slow Falling");
        add("jej.power.slow_falling.desc", "Fall gently and avoid fall damage.");
        add("jej.power.saturation", "Saturation");
        add("jej.power.saturation.desc", "Instantly restores hunger.");
        add("jej.power.fire_resistance", "Fire Resistance");
        add("jej.power.fire_resistance.desc", "Immune to fire and lava damage.");
        add("jej.power.luck", "Luck");
        add("jej.power.luck.desc", "Improves loot and fishing luck.");
        add("jej.power.levitation", "Levitation");
        add("jej.power.levitation.desc", "Drift upward into the air.");
        add("jej.power.glowing", "Glowing");
        add("jej.power.glowing.desc", "Your outline is visible through blocks.");
        add("jej.power.movement_slowdown", "Slowness");
        add("jej.power.movement_slowdown.desc", "Move slower.");

        //Descriptions for the mod's own mechanical effects
        add(Init.ICYFOOTEFFECT.get().getDescriptionId() + ".desc", "Turns the water under your feet to frosted ice.");
        add(Init.CAFFEINATED.get().getDescriptionId() + ".desc", "Speed and Haste while active, then a 20s crash (Slowness + Mining Fatigue).");
        add(Init.SPICY.get().getDescriptionId() + ".desc", "Sets nearby mobs on fire (15% per second) and extinguishes you.");
        add(Init.FLOAT.get().getDescriptionId() + ".desc", "Lift gently into the air and descend slowly.");
        add(Init.MAGNET.get().getDescriptionId() + ".desc", "Pulls nearby items and XP toward you.");
        add(Init.ORCHARD_GUARD.get().getDescriptionId() + ".desc", "Reduces incoming damage by 15-40% (except the void).");
        add(Init.FROSTBITE.get().getDescriptionId() + ".desc", "Chills attackers, slowing and freezing them.");
        add(Init.SOLAR_CHARGE.get().getDescriptionId() + ".desc", "Heals you in daylight under open sky (1-1.5 HP every 2s).");
        add(Init.FORAGERS_LUCK.get().getDescriptionId() + ".desc", "Harvest 1-2 extra berries from the mod's bushes.");

        //Per-Juice Custom Effects
        add(Init.APPLE_EFFECT.get(), "Apple Sight");
        add(Init.APPLE_BOOSTED_EFFECT.get(), "Empowered Apple Sight");
        add(Init.SWEETBERRY_EFFECT.get(), "Berry Rush");
        add(Init.SWEETBERRY_BOOSTED_EFFECT.get(), "Empowered Berry Rush");
        add(Init.CARROT_EFFECT.get(), "Keen Carrot");
        add(Init.CARROT_BOOSTED_EFFECT.get(), "Empowered Keen Carrot");
        add(Init.BAKEDPOTATO_EFFECT.get(), "Starch Stamina");
        add(Init.BAKEDPOTATO_BOOSTED_EFFECT.get(), "Empowered Starch Stamina");
        add(Init.MELON_EFFECT.get(), "Melon Bounce");
        add(Init.MELON_BOOSTED_EFFECT.get(), "Empowered Melon Bounce");
        add(Init.PUMPKIN_EFFECT.get(), "Pumpkin Shroud");
        add(Init.PUMPKIN_BOOSTED_EFFECT.get(), "Empowered Pumpkin Shroud");
        add(Init.ICEBERRY_EFFECT.get(), "Frozen Footing");
        add(Init.ICEBERRY_BOOSTED_EFFECT.get(), "Empowered Frozen Footing");
        add(Init.WILDBERRY_EFFECT.get(), "Wild Vitality");
        add(Init.WILDBERRY_BOOSTED_EFFECT.get(), "Empowered Wild Vitality");
        add(Init.DRIEDKELP_EFFECT.get(), "Kelp Current");
        add(Init.DRIEDKELP_BOOSTED_EFFECT.get(), "Empowered Kelp Current");
        add(Init.GLISTERING_MELON_EFFECT.get(), "Glistering Vitality");
        add(Init.GLISTERING_MELON_BOOSTED_EFFECT.get(), "Empowered Glistering Vitality");
        add(Init.GOLDENAPPLE_EFFECT.get(), "Golden Blessing");
        add(Init.GOLDENAPPLE_BOOSTED_EFFECT.get(), "Empowered Golden Blessing");
        add(Init.GOLDENCARROT_EFFECT.get(), "Golden Clarity");
        add(Init.GOLDENCARROT_BOOSTED_EFFECT.get(), "Empowered Golden Clarity");
        add(Init.CHORUS_EFFECT.get(), "Chorus Drift");
        add(Init.CHORUS_BOOSTED_EFFECT.get(), "Empowered Chorus Drift");
        add(Init.GLOWBERRY_EFFECT.get(), "Glow Sight");
        add(Init.GLOWBERRY_BOOSTED_EFFECT.get(), "Empowered Glow Sight");
        add(Init.SPICY_EFFECT.get(), "Spicy Aura");
        add(Init.SPICY_BOOSTED_EFFECT.get(), "Empowered Spicy Aura");
        add(Init.GOLEM_EFFECT.get(), "Golem Strength");
        add(Init.GOLEM_BOOSTED_EFFECT.get(), "Empowered Golem Strength");
        add(Init.SUNBERRY_EFFECT.get(), "Solar Energy");
        add(Init.SUNBERRY_BOOSTED_EFFECT.get(), "Empowered Solar Energy");
        add(Init.BEETROOT_EFFECT.get(), "Beetroot Feast");
        add(Init.BEETROOT_BOOSTED_EFFECT.get(), "Empowered Beetroot Feast");
        add(Init.NETHERWART_EFFECT.get(), "Nether Ward");
        add(Init.NETHERWART_BOOSTED_EFFECT.get(), "Empowered Nether Ward");
        add(Init.COCOA_EFFECT.get(), "Cocoa Focus");
        add(Init.COCOA_BOOSTED_EFFECT.get(), "Empowered Cocoa Focus");

        //item
        add(Init.EMERALD_DUST.get(), "Emerald Dust");
        add(Init.GLASS_BOTTLE.get(), "Glass Bottle");

        //Advancements
        add("advancements.jej.first_juice.title", "First Juice");
        add("advancements.jej.first_juice.description", "Craft your first juice");
        add("advancements.jej.juice_connoisseur.title", "Juice Connoisseur");
        add("advancements.jej.juice_connoisseur.description", "Craft a boosted juice");
        add("advancements.jej.golden_mastery.title", "Golden Mastery");
        add("advancements.jej.golden_mastery.description", "Craft golden apple juice");
    }
}
