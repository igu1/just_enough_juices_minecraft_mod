package me.ez.jej;

import me.ez.jej.common.Effects.CaffeinatedEffect;
import me.ez.jej.common.Effects.FloatEffect;
import me.ez.jej.common.Effects.IcyFootEffect;
import me.ez.jej.common.Effects.JuiceEffect;
import me.ez.jej.common.Effects.MagnetEffect;
import me.ez.jej.common.Effects.SpicyEffect;
import me.ez.jej.common.JuiceClass;
import me.ez.jej.common.Bushes.GlowBerryBush;
import me.ez.jej.common.Bushes.IcyBush;
import me.ez.jej.common.Bushes.SunBerryBush;
import me.ez.jej.common.Bushes.WildBerryBush;
import me.ez.jej.common.TomatoBlock;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Init {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    //Items
    public static final RegistryObject<Item> APPLE_JUICE = ITEMS.register("apple_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> APPLE_JUICE_BOOSTED = ITEMS.register("apple_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> SWEETBERRY_JUICE = ITEMS.register("sweetberry_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> SWEETBERRY_JUICE_BOOSTED = ITEMS.register("sweetberry_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));;

    public static final RegistryObject<Item> CARROT_JUICE = ITEMS.register("carrot_juice", () -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> CARROT_JUICE_BOOSTED = ITEMS.register("carrot_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> BAKEDPOTATO_JUICE = ITEMS.register("bakedpotato_juice", () -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> BAKEDPOTATO_JUICE_BOOSTED = ITEMS.register("bakedpotato_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> MELON_JUICE = ITEMS.register("melon_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> MELON_JUICE_BOOSTED = ITEMS.register("melon_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> PUMPKIN_JUICE = ITEMS.register("pumpkin_juice", () -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> PUMPKIN_JUICE_BOOSTED = ITEMS.register("pumpkin_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> ICEBERRY_JUICE = ITEMS.register("iceberry_juice", () -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> ICEBERRY_JUICE_BOOSTED = ITEMS.register("iceberry_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> WILDBERRY_JUICE = ITEMS.register("wildberry_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> WILDBERRY_JUICE_BOOSTED = ITEMS.register("wildberry_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> DRIEDKELP_JUICE = ITEMS.register("driedkelp_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> DRIEDKELP_JUICE_BOOSTED = ITEMS.register("driedkelp_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> GOLDENAPPLE_JUICE = ITEMS.register("goldenapple_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> GOLDENAPPLE_JUICE_BOOSTED = ITEMS.register("goldenapple_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> GOLDENCARROT_JUICE = ITEMS.register("goldencarrot_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> GOLDENCARROT_JUICE_BOOSTED = ITEMS.register("goldencarrot_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> GLISTERING_MELON_JUICE = ITEMS.register("glistering_melon_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> GLISTERING_MELON_JUICE_BOOSTED = ITEMS.register("glistering_melon_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    //New Juices
    public static final RegistryObject<Item> CHORUS_JUICE = ITEMS.register("chorus_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> CHORUS_JUICE_BOOSTED = ITEMS.register("chorus_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> GLOWBERRY_JUICE = ITEMS.register("glowberry_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> GLOWBERRY_JUICE_BOOSTED = ITEMS.register("glowberry_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> SPICY_JUICE = ITEMS.register("spicy_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> SPICY_JUICE_BOOSTED = ITEMS.register("spicy_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> GOLEM_JUICE = ITEMS.register("golem_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> GOLEM_JUICE_BOOSTED = ITEMS.register("golem_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> SUNBERRY_JUICE = ITEMS.register("sunberry_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> SUNBERRY_JUICE_BOOSTED = ITEMS.register("sunberry_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> BEETROOT_JUICE = ITEMS.register("beetroot_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> BEETROOT_JUICE_BOOSTED = ITEMS.register("beetroot_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> NETHERWART_JUICE = ITEMS.register("netherwart_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> NETHERWART_JUICE_BOOSTED = ITEMS.register("netherwart_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> COCOA_JUICE = ITEMS.register("cocoa_juice",() -> new JuiceClass(new Item.Properties().stacksTo(1).tab(Main.TAB)));
    public static final RegistryObject<Item> COCOA_JUICE_BOOSTED = ITEMS.register("cocoa_juice_boosted", () -> new JuiceClass(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(Main.TAB)));

    public static final RegistryObject<Item> EMERALD_DUST = ITEMS.register("emerald_dust",() -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> GLASS_BOTTLE = ITEMS.register("glass_bottle",() -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(16)));

    public static final RegistryObject<BlockItem> ICE_BERRY = ITEMS.register("ice_berry",() -> new BlockItem(Init.ICE_BERRY_BUSH.get(), new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).build())));
    public static final RegistryObject<BlockItem> WILD_BERRY = ITEMS.register("wild_berry",() -> new BlockItem(Init.WILD_BERRY_BUSH.get(), new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
    public static final RegistryObject<BlockItem> SUN_BERRY = ITEMS.register("sun_berry",() -> new BlockItem(Init.SUN_BERRY_BUSH.get(), new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build())));
    public static final RegistryObject<BlockItem> GLOW_BERRY = ITEMS.register("glow_berry",() -> new BlockItem(Init.GLOW_BERRY_BUSH.get(), new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
//    public static final RegistryObject<BlockItem> TOMATO_ITEM = ITEMS.register("tomato",() -> new BlockItem(Init.TOMATO.get(), new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    //Blocks
    public static final RegistryObject<IcyBush> ICE_BERRY_BUSH = BLOCKS.register("iceberry_bush", IcyBush::new);
    public static final RegistryObject<WildBerryBush> WILD_BERRY_BUSH = BLOCKS.register("wildberry_bush", WildBerryBush::new);
    public static final RegistryObject<SunBerryBush> SUN_BERRY_BUSH = BLOCKS.register("sunberry_bush", SunBerryBush::new);
    public static final RegistryObject<GlowBerryBush> GLOW_BERRY_BUSH = BLOCKS.register("glowberry_bush", GlowBerryBush::new);
//    public static final RegistryObject<Block> TOMATO = BLOCKS.register("tomato", () -> new TomatoBlock(BlockBehaviour.Properties.copy(Blocks.PUMPKIN)));

    //Effects
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MOD_ID);
    public static final RegistryObject<MobEffect> ICYFOOTEFFECT = EFFECT.register("icyfooteffect", () -> new IcyFootEffect(1));
    public static final RegistryObject<MobEffect> CAFFEINATED = EFFECT.register("caffeinated", () -> new CaffeinatedEffect(0x8B4513));
    public static final RegistryObject<MobEffect> SPICY = EFFECT.register("spicy", () -> new SpicyEffect(0xFF4500));
    public static final RegistryObject<MobEffect> MAGNET = EFFECT.register("magnet", () -> new MagnetEffect(0x9400D3));
    public static final RegistryObject<MobEffect> FLOAT = EFFECT.register("float", () -> new FloatEffect(0x87CEEB));

    //Per-Juice Custom Effects
    public static final RegistryObject<MobEffect> APPLE_EFFECT = EFFECT.register("apple_effect", () -> new JuiceEffect(0xDC143C,
            app(MobEffects.NIGHT_VISION, 0)));
    public static final RegistryObject<MobEffect> APPLE_BOOSTED_EFFECT = EFFECT.register("apple_boosted_effect", () -> new JuiceEffect(0xDC143C,
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.REGENERATION, 0)));

    public static final RegistryObject<MobEffect> SWEETBERRY_EFFECT = EFFECT.register("sweetberry_effect", () -> new JuiceEffect(0xFF1493,
            app(MobEffects.MOVEMENT_SPEED, 0)));
    public static final RegistryObject<MobEffect> SWEETBERRY_BOOSTED_EFFECT = EFFECT.register("sweetberry_boosted_effect", () -> new JuiceEffect(0xFF1493,
            app(MobEffects.MOVEMENT_SPEED, 1)));

    public static final RegistryObject<MobEffect> CARROT_EFFECT = EFFECT.register("carrot_effect", () -> new JuiceEffect(0xFF8C00,
            app(MobEffects.DAMAGE_BOOST, 0)));
    public static final RegistryObject<MobEffect> CARROT_BOOSTED_EFFECT = EFFECT.register("carrot_boosted_effect", () -> new JuiceEffect(0xFF8C00,
            app(MobEffects.DAMAGE_BOOST, 0),
            app(MobEffects.DAMAGE_RESISTANCE, 0)));

    public static final RegistryObject<MobEffect> BAKEDPOTATO_EFFECT = EFFECT.register("bakedpotato_effect", () -> new JuiceEffect(0xD2B48C,
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.DIG_SPEED, 0)));
    public static final RegistryObject<MobEffect> BAKEDPOTATO_BOOSTED_EFFECT = EFFECT.register("bakedpotato_boosted_effect", () -> new JuiceEffect(0xD2B48C,
            app(MobEffects.DIG_SPEED, 1)));

    public static final RegistryObject<MobEffect> MELON_EFFECT = EFFECT.register("melon_effect", () -> new JuiceEffect(0x7CFC00,
            app(MobEffects.JUMP, 0)));
    public static final RegistryObject<MobEffect> MELON_BOOSTED_EFFECT = EFFECT.register("melon_boosted_effect", () -> new JuiceEffect(0x7CFC00,
            app(MobEffects.JUMP, 1)));

    public static final RegistryObject<MobEffect> PUMPKIN_EFFECT = EFFECT.register("pumpkin_effect", () -> new JuiceEffect(0xFFA500,
            app(MobEffects.INVISIBILITY, 0)));
    public static final RegistryObject<MobEffect> PUMPKIN_BOOSTED_EFFECT = EFFECT.register("pumpkin_boosted_effect", () -> new JuiceEffect(0xFFA500,
            app(MobEffects.INVISIBILITY, 0)));

    public static final RegistryObject<MobEffect> ICEBERRY_EFFECT = EFFECT.register("iceberry_effect", () -> new JuiceEffect(0xAFEEEE,
            app(ICYFOOTEFFECT.get(), 0)));
    public static final RegistryObject<MobEffect> ICEBERRY_BOOSTED_EFFECT = EFFECT.register("iceberry_boosted_effect", () -> new JuiceEffect(0xAFEEEE,
            app(ICYFOOTEFFECT.get(), 0),
            app(MobEffects.WATER_BREATHING, 0)));

    public static final RegistryObject<MobEffect> WILDBERRY_EFFECT = EFFECT.register("wildberry_effect", () -> new JuiceEffect(0x8B008B,
            app(MobEffects.ABSORPTION, 0)));
    public static final RegistryObject<MobEffect> WILDBERRY_BOOSTED_EFFECT = EFFECT.register("wildberry_boosted_effect", () -> new JuiceEffect(0x8B008B,
            app(MobEffects.ABSORPTION, 1),
            app(MobEffects.DAMAGE_BOOST, 0)));

    public static final RegistryObject<MobEffect> DRIEDKELP_EFFECT = EFFECT.register("driedkelp_effect", () -> new JuiceEffect(0x006400,
            app(MobEffects.DOLPHINS_GRACE, 0)));
    public static final RegistryObject<MobEffect> DRIEDKELP_BOOSTED_EFFECT = EFFECT.register("driedkelp_boosted_effect", () -> new JuiceEffect(0x006400,
            app(MobEffects.DOLPHINS_GRACE, 0),
            app(MobEffects.WATER_BREATHING, 0)));

    public static final RegistryObject<MobEffect> GLISTERING_MELON_EFFECT = EFFECT.register("glistering_melon_effect", () -> new JuiceEffect(0xFFE4B5,
            app(MobEffects.REGENERATION, 0),
            app(MobEffects.SLOW_FALLING, 0),
            app(MobEffects.MOVEMENT_SPEED, 0)));
    public static final RegistryObject<MobEffect> GLISTERING_MELON_BOOSTED_EFFECT = EFFECT.register("glistering_melon_boosted_effect", () -> new JuiceEffect(0xFFE4B5,
            app(MobEffects.REGENERATION, 1),
            app(MobEffects.SLOW_FALLING, 0),
            app(MobEffects.MOVEMENT_SPEED, 0),
            app(MobEffects.SATURATION, 0)));

    public static final RegistryObject<MobEffect> GOLDENAPPLE_EFFECT = EFFECT.register("goldenapple_effect", () -> new JuiceEffect(0xFFD700,
            app(MobEffects.ABSORPTION, 0),
            app(MobEffects.FIRE_RESISTANCE, 0),
            app(MobEffects.NIGHT_VISION, 0)));
    public static final RegistryObject<MobEffect> GOLDENAPPLE_BOOSTED_EFFECT = EFFECT.register("goldenapple_boosted_effect", () -> new JuiceEffect(0xFFD700,
            app(MobEffects.ABSORPTION, 0),
            app(MobEffects.FIRE_RESISTANCE, 0),
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.REGENERATION, 1),
            app(MobEffects.DAMAGE_RESISTANCE, 0)));

    public static final RegistryObject<MobEffect> GOLDENCARROT_EFFECT = EFFECT.register("goldencarrot_effect", () -> new JuiceEffect(0xDAA520,
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.WATER_BREATHING, 0),
            app(MobEffects.DOLPHINS_GRACE, 1)));
    public static final RegistryObject<MobEffect> GOLDENCARROT_BOOSTED_EFFECT = EFFECT.register("goldencarrot_boosted_effect", () -> new JuiceEffect(0xDAA520,
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.WATER_BREATHING, 0),
            app(MobEffects.DOLPHINS_GRACE, 1),
            app(MobEffects.LUCK, 1)));

    public static final RegistryObject<MobEffect> CHORUS_EFFECT = EFFECT.register("chorus_effect", () -> new JuiceEffect(0xBA55D3,
            app(MobEffects.LEVITATION, 0),
            app(MobEffects.SLOW_FALLING, 0)));
    public static final RegistryObject<MobEffect> CHORUS_BOOSTED_EFFECT = EFFECT.register("chorus_boosted_effect", () -> new JuiceEffect(0xBA55D3,
            app(MobEffects.LEVITATION, 0),
            app(MobEffects.SLOW_FALLING, 1)));

    public static final RegistryObject<MobEffect> GLOWBERRY_EFFECT = EFFECT.register("glowberry_effect", () -> new JuiceEffect(0xFFFF00,
            app(MobEffects.GLOWING, 0),
            app(MobEffects.NIGHT_VISION, 0)));
    public static final RegistryObject<MobEffect> GLOWBERRY_BOOSTED_EFFECT = EFFECT.register("glowberry_boosted_effect", () -> new JuiceEffect(0xFFFF00,
            app(MobEffects.GLOWING, 0),
            app(MobEffects.NIGHT_VISION, 0),
            app(FLOAT.get(), 0)));

    public static final RegistryObject<MobEffect> SPICY_EFFECT = EFFECT.register("spicy_effect", () -> new JuiceEffect(0xFF4500,
            app(SPICY.get(), 0),
            app(MobEffects.FIRE_RESISTANCE, 0)));
    public static final RegistryObject<MobEffect> SPICY_BOOSTED_EFFECT = EFFECT.register("spicy_boosted_effect", () -> new JuiceEffect(0xFF4500,
            app(SPICY.get(), 1),
            app(MobEffects.FIRE_RESISTANCE, 0),
            app(MobEffects.DAMAGE_BOOST, 0)));

    public static final RegistryObject<MobEffect> GOLEM_EFFECT = EFFECT.register("golem_effect", () -> new JuiceEffect(0xA9A9A9,
            app(MobEffects.ABSORPTION, 2),
            app(MobEffects.DAMAGE_RESISTANCE, 0),
            app(MobEffects.MOVEMENT_SLOWDOWN, 0)));
    public static final RegistryObject<MobEffect> GOLEM_BOOSTED_EFFECT = EFFECT.register("golem_boosted_effect", () -> new JuiceEffect(0xA9A9A9,
            app(MobEffects.ABSORPTION, 4),
            app(MobEffects.DAMAGE_RESISTANCE, 1),
            app(MobEffects.MOVEMENT_SLOWDOWN, 0)));

    public static final RegistryObject<MobEffect> SUNBERRY_EFFECT = EFFECT.register("sunberry_effect", () -> new JuiceEffect(0xFFFF66,
            app(CAFFEINATED.get(), 0),
            app(MobEffects.MOVEMENT_SPEED, 1)));
    public static final RegistryObject<MobEffect> SUNBERRY_BOOSTED_EFFECT = EFFECT.register("sunberry_boosted_effect", () -> new JuiceEffect(0xFFFF66,
            app(CAFFEINATED.get(), 1),
            app(MobEffects.MOVEMENT_SPEED, 1),
            app(MobEffects.JUMP, 0)));

    public static final RegistryObject<MobEffect> BEETROOT_EFFECT = EFFECT.register("beetroot_effect", () -> new JuiceEffect(0x8B0000,
            app(MobEffects.SATURATION, 0)));
    public static final RegistryObject<MobEffect> BEETROOT_BOOSTED_EFFECT = EFFECT.register("beetroot_boosted_effect", () -> new JuiceEffect(0x8B0000,
            app(MobEffects.SATURATION, 0),
            app(MobEffects.DAMAGE_RESISTANCE, 0)));

    public static final RegistryObject<MobEffect> NETHERWART_EFFECT = EFFECT.register("netherwart_effect", () -> new JuiceEffect(0x800000,
            app(MobEffects.FIRE_RESISTANCE, 0)));
    public static final RegistryObject<MobEffect> NETHERWART_BOOSTED_EFFECT = EFFECT.register("netherwart_boosted_effect", () -> new JuiceEffect(0x800000,
            app(MobEffects.FIRE_RESISTANCE, 0),
            app(MobEffects.DAMAGE_BOOST, 0)));

    public static final RegistryObject<MobEffect> COCOA_EFFECT = EFFECT.register("cocoa_effect", () -> new JuiceEffect(0x8B4513,
            app(MobEffects.DIG_SPEED, 0)));
    public static final RegistryObject<MobEffect> COCOA_BOOSTED_EFFECT = EFFECT.register("cocoa_boosted_effect", () -> new JuiceEffect(0x8B4513,
            app(MobEffects.DIG_SPEED, 1),
            app(MobEffects.NIGHT_VISION, 0)));

    private static JuiceEffect.JuiceApplication app(MobEffect effect, int amplifier) {
        return new JuiceEffect.JuiceApplication(effect, amplifier);
    }

}