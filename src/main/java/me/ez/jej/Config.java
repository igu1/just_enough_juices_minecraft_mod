package me.ez.jej;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_VILLAGER_TRADES;
    public static final ForgeConfigSpec.BooleanValue ENABLE_COMPOSTING;
    public static final ForgeConfigSpec.DoubleValue COMPOST_CHANCE;
    public static final ForgeConfigSpec.BooleanValue RETURN_GLASS_BOTTLE;

    public static final ForgeConfigSpec.BooleanValue OVERDRINK_ENABLED;
    public static final ForgeConfigSpec.IntValue OVERDRINK_MAX_DRINKS;
    public static final ForgeConfigSpec.IntValue OVERDRINK_WINDOW_TICKS;
    public static final ForgeConfigSpec.IntValue OVERDRINK_NAUSEA_TICKS;
    public static final ForgeConfigSpec.IntValue OVERDRINK_HUNGER_TICKS;

    public static final ForgeConfigSpec.DoubleValue EFFECT_DURATION_MULTIPLIER;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("General gameplay toggles").push("general");
        ENABLE_VILLAGER_TRADES = builder
                .comment("Add juices to farmer, cleric and wandering trader trades")
                .define("enableVillagerTrades", true);
        ENABLE_COMPOSTING = builder
                .comment("Allow berries to be used in a composter")
                .define("enableComposting", true);
        COMPOST_CHANCE = builder
                .comment("Composter fill chance for berries (0.0 - 1.0)")
                .defineInRange("compostChance", 0.3D, 0.0D, 1.0D);
        RETURN_GLASS_BOTTLE = builder
                .comment("Return an empty glass bottle after drinking a juice")
                .define("returnGlassBottle", true);
        builder.pop();

        builder.comment("Penalties for drinking many juices quickly").push("overdrink");
        OVERDRINK_ENABLED = builder
                .comment("Enable the overdrink penalty")
                .define("enabled", true);
        OVERDRINK_MAX_DRINKS = builder
                .comment("Number of drinks within the window before the penalty applies")
                .defineInRange("maxDrinks", 4, 1, 64);
        OVERDRINK_WINDOW_TICKS = builder
                .comment("Time window in ticks for counting drinks")
                .defineInRange("windowTicks", 200, 1, 24000);
        OVERDRINK_NAUSEA_TICKS = builder
                .comment("Duration in ticks of the nausea penalty")
                .defineInRange("nauseaTicks", 200, 1, 24000);
        OVERDRINK_HUNGER_TICKS = builder
                .comment("Duration in ticks of the hunger penalty")
                .defineInRange("hungerTicks", 300, 1, 24000);
        builder.pop();

        builder.comment("Juice effect tuning").push("effects");
        EFFECT_DURATION_MULTIPLIER = builder
                .comment("Multiplies the duration of every juice effect")
                .defineInRange("durationMultiplier", 1.0D, 0.1D, 10.0D);
        builder.pop();

        SPEC = builder.build();
    }
}
