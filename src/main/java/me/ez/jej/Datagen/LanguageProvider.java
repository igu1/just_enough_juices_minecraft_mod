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