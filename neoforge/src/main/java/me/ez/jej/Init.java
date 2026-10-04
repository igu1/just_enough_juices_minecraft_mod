package me.ez.jej;

import me.ez.jej.common.Effects.CaffeinatedEffect;
import me.ez.jej.common.Effects.FloatEffect;
import me.ez.jej.common.Effects.IcyFootEffect;
import me.ez.jej.common.Effects.JuiceEffect;
import me.ez.jej.common.Effects.JuicePower;
import me.ez.jej.common.Effects.MagnetEffect;
import me.ez.jej.common.Effects.SpicyEffect;
import me.ez.jej.common.JuiceClass;
import me.ez.jej.common.Bushes.GlowBerryBush;
import me.ez.jej.common.Bushes.IcyBush;
import me.ez.jej.common.Bushes.SunBerryBush;
import me.ez.jej.common.Bushes.WildBerryBush;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Init {
    public static final DeferredRegister<net.minecraft.world.level.block.entity.BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Main.MOD_ID);
    public static final DeferredRegister<net.minecraft.world.inventory.MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Main.MOD_ID);

    public static void registerTable(net.neoforged.bus.api.IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
        MENUS.register(bus);
    }

    //? if >=1.21.2 {
    /*public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, net.minecraft.world.level.block.entity.BlockEntityType<me.ez.jej.common.JuiceTableBlockEntity>> JUICE_TABLE_ENTITY = BLOCK_ENTITIES.register("juice_table", () -> new net.minecraft.world.level.block.entity.BlockEntityType<>(me.ez.jej.common.JuiceTableBlockEntity::new, Init.JUICE_TABLE.get()));
    *///?} else {
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, net.minecraft.world.level.block.entity.BlockEntityType<me.ez.jej.common.JuiceTableBlockEntity>> JUICE_TABLE_ENTITY = BLOCK_ENTITIES.register("juice_table", () -> net.minecraft.world.level.block.entity.BlockEntityType.Builder.of(me.ez.jej.common.JuiceTableBlockEntity::new, Init.JUICE_TABLE.get()).build(null));
    //?}
    public static final DeferredHolder<net.minecraft.world.inventory.MenuType<?>, net.minecraft.world.inventory.MenuType<me.ez.jej.common.JuiceTableMenu>> JUICE_TABLE_MENU = MENUS.register("juice_table", () -> net.neoforged.neoforge.common.extensions.IMenuTypeExtension.create(me.ez.jej.common.JuiceTableMenu::new));
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MOD_ID);
    public static final DeferredHolder<Item, ? extends Item> JUICE_TABLE_ITEM = ITEMS.registerSimpleBlockItem("juice_table", () -> Init.JUICE_TABLE.get(), new Item.Properties());

    //Items
    public static final DeferredHolder<Item, ? extends Item> APPLE_JUICE = ITEMS.registerItem("apple_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> APPLE_JUICE_BOOSTED = ITEMS.registerItem("apple_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> SWEETBERRY_JUICE = ITEMS.registerItem("sweetberry_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> SWEETBERRY_JUICE_BOOSTED = ITEMS.registerItem("sweetberry_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> CARROT_JUICE = ITEMS.registerItem("carrot_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> CARROT_JUICE_BOOSTED = ITEMS.registerItem("carrot_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> BAKEDPOTATO_JUICE = ITEMS.registerItem("bakedpotato_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> BAKEDPOTATO_JUICE_BOOSTED = ITEMS.registerItem("bakedpotato_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> MELON_JUICE = ITEMS.registerItem("melon_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> MELON_JUICE_BOOSTED = ITEMS.registerItem("melon_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> PUMPKIN_JUICE = ITEMS.registerItem("pumpkin_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> PUMPKIN_JUICE_BOOSTED = ITEMS.registerItem("pumpkin_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> ICEBERRY_JUICE = ITEMS.registerItem("iceberry_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> ICEBERRY_JUICE_BOOSTED = ITEMS.registerItem("iceberry_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> WILDBERRY_JUICE = ITEMS.registerItem("wildberry_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> WILDBERRY_JUICE_BOOSTED = ITEMS.registerItem("wildberry_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> DRIEDKELP_JUICE = ITEMS.registerItem("driedkelp_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> DRIEDKELP_JUICE_BOOSTED = ITEMS.registerItem("driedkelp_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> GOLDENAPPLE_JUICE = ITEMS.registerItem("goldenapple_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> GOLDENAPPLE_JUICE_BOOSTED = ITEMS.registerItem("goldenapple_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> GOLDENCARROT_JUICE = ITEMS.registerItem("goldencarrot_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> GOLDENCARROT_JUICE_BOOSTED = ITEMS.registerItem("goldencarrot_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> GLISTERING_MELON_JUICE = ITEMS.registerItem("glistering_melon_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> GLISTERING_MELON_JUICE_BOOSTED = ITEMS.registerItem("glistering_melon_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    //New Juices
    public static final DeferredHolder<Item, ? extends Item> CHORUS_JUICE = ITEMS.registerItem("chorus_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> CHORUS_JUICE_BOOSTED = ITEMS.registerItem("chorus_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> GLOWBERRY_JUICE = ITEMS.registerItem("glowberry_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> GLOWBERRY_JUICE_BOOSTED = ITEMS.registerItem("glowberry_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> SPICY_JUICE = ITEMS.registerItem("spicy_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> SPICY_JUICE_BOOSTED = ITEMS.registerItem("spicy_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> GOLEM_JUICE = ITEMS.registerItem("golem_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> GOLEM_JUICE_BOOSTED = ITEMS.registerItem("golem_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> SUNBERRY_JUICE = ITEMS.registerItem("sunberry_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> SUNBERRY_JUICE_BOOSTED = ITEMS.registerItem("sunberry_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> BEETROOT_JUICE = ITEMS.registerItem("beetroot_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> BEETROOT_JUICE_BOOSTED = ITEMS.registerItem("beetroot_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> NETHERWART_JUICE = ITEMS.registerItem("netherwart_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> NETHERWART_JUICE_BOOSTED = ITEMS.registerItem("netherwart_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> COCOA_JUICE = ITEMS.registerItem("cocoa_juice", JuiceClass::new, new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, ? extends Item> COCOA_JUICE_BOOSTED = ITEMS.registerItem("cocoa_juice_boosted", JuiceClass::new, new Item.Properties().rarity(Rarity.RARE).stacksTo(1));

    public static final DeferredHolder<Item, ? extends Item> EMERALD_DUST = ITEMS.registerSimpleItem("emerald_dust", new Item.Properties());
    public static final DeferredHolder<Item, ? extends Item> JUICE_BOOSTER = ITEMS.registerItem("juice_booster", me.ez.jej.common.JuiceBoosterItem::new, new Item.Properties());
    public static final DeferredHolder<Item, ? extends Item> GLASS_BOTTLE = ITEMS.registerSimpleItem("glass_bottle", new Item.Properties().stacksTo(16));

    public static final DeferredHolder<Item, ? extends BlockItem> ICE_BERRY = ITEMS.registerSimpleBlockItem("ice_berry", () -> Init.ICE_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build()));
    public static final DeferredHolder<Item, ? extends BlockItem> WILD_BERRY = ITEMS.registerSimpleBlockItem("wild_berry", () -> Init.WILD_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build()));
    public static final DeferredHolder<Item, ? extends BlockItem> SUN_BERRY = ITEMS.registerSimpleBlockItem("sun_berry", () -> Init.SUN_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build()));
    public static final DeferredHolder<Item, ? extends BlockItem> GLOW_BERRY = ITEMS.registerSimpleBlockItem("glow_berry", () -> Init.GLOW_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build()));

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Main.MOD_ID);
    public static final DeferredHolder<Block, ? extends me.ez.jej.common.JuiceTableBlock> JUICE_TABLE = BLOCKS.register("juice_table", me.ez.jej.common.JuiceTableBlock::new);
    //Blocks
    public static final DeferredHolder<Block, ? extends IcyBush> ICE_BERRY_BUSH = BLOCKS.register("iceberry_bush", IcyBush::new);
    public static final DeferredHolder<Block, ? extends WildBerryBush> WILD_BERRY_BUSH = BLOCKS.register("wildberry_bush", WildBerryBush::new);
    public static final DeferredHolder<Block, ? extends SunBerryBush> SUN_BERRY_BUSH = BLOCKS.register("sunberry_bush", SunBerryBush::new);
    public static final DeferredHolder<Block, ? extends GlowBerryBush> GLOW_BERRY_BUSH = BLOCKS.register("glowberry_bush", GlowBerryBush::new);

    //Effects
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(Registries.MOB_EFFECT, Main.MOD_ID);
    public static final DeferredHolder<MobEffect, ? extends MobEffect> ICYFOOTEFFECT = EFFECT.register("icyfooteffect", () -> new IcyFootEffect(1));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> CAFFEINATED = EFFECT.register("caffeinated", () -> new CaffeinatedEffect(0x8B4513));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> SPICY = EFFECT.register("spicy", () -> new SpicyEffect(0xFF4500));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> MAGNET = EFFECT.register("magnet", () -> new MagnetEffect(0x9400D3));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> FLOAT = EFFECT.register("float", () -> new FloatEffect(0x87CEEB));

    //Per-Juice Custom Effects
    public static final DeferredHolder<MobEffect, ? extends MobEffect> APPLE_EFFECT = EFFECT.register("apple_effect", () -> new JuiceEffect(0xDC143C,
            app(MobEffects.NIGHT_VISION, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> APPLE_BOOSTED_EFFECT = EFFECT.register("apple_boosted_effect", () -> new JuiceEffect(0xDC143C,
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.REGENERATION, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> SWEETBERRY_EFFECT = EFFECT.register("sweetberry_effect", () -> new JuiceEffect(0xFF1493,
            app(MobEffects.MOVEMENT_SPEED, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> SWEETBERRY_BOOSTED_EFFECT = EFFECT.register("sweetberry_boosted_effect", () -> new JuiceEffect(0xFF1493,
            app(MobEffects.MOVEMENT_SPEED, 1)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> CARROT_EFFECT = EFFECT.register("carrot_effect", () -> new JuiceEffect(0xFF8C00,
            app(MobEffects.DAMAGE_BOOST, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> CARROT_BOOSTED_EFFECT = EFFECT.register("carrot_boosted_effect", () -> new JuiceEffect(0xFF8C00,
            app(MobEffects.DAMAGE_BOOST, 0),
            app(MobEffects.DAMAGE_RESISTANCE, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> BAKEDPOTATO_EFFECT = EFFECT.register("bakedpotato_effect", () -> new JuiceEffect(0xD2B48C,
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.DIG_SPEED, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> BAKEDPOTATO_BOOSTED_EFFECT = EFFECT.register("bakedpotato_boosted_effect", () -> new JuiceEffect(0xD2B48C,
            app(MobEffects.DIG_SPEED, 1)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> MELON_EFFECT = EFFECT.register("melon_effect", () -> new JuiceEffect(0x7CFC00,
            app(MobEffects.JUMP, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> MELON_BOOSTED_EFFECT = EFFECT.register("melon_boosted_effect", () -> new JuiceEffect(0x7CFC00,
            app(MobEffects.JUMP, 1)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> PUMPKIN_EFFECT = EFFECT.register("pumpkin_effect", () -> new JuiceEffect(0xFFA500,
            app(MobEffects.INVISIBILITY, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> PUMPKIN_BOOSTED_EFFECT = EFFECT.register("pumpkin_boosted_effect", () -> new JuiceEffect(0xFFA500,
            app(MobEffects.INVISIBILITY, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> ICEBERRY_EFFECT = EFFECT.register("iceberry_effect", () -> new JuiceEffect(0xAFEEEE,
            app(ICYFOOTEFFECT, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> ICEBERRY_BOOSTED_EFFECT = EFFECT.register("iceberry_boosted_effect", () -> new JuiceEffect(0xAFEEEE,
            app(ICYFOOTEFFECT, 0),
            app(MobEffects.WATER_BREATHING, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> WILDBERRY_EFFECT = EFFECT.register("wildberry_effect", () -> new JuiceEffect(0x8B008B,
            app(MobEffects.ABSORPTION, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> WILDBERRY_BOOSTED_EFFECT = EFFECT.register("wildberry_boosted_effect", () -> new JuiceEffect(0x8B008B,
            app(MobEffects.ABSORPTION, 1),
            app(MobEffects.DAMAGE_BOOST, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> DRIEDKELP_EFFECT = EFFECT.register("driedkelp_effect", () -> new JuiceEffect(0x006400,
            app(MobEffects.DOLPHINS_GRACE, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> DRIEDKELP_BOOSTED_EFFECT = EFFECT.register("driedkelp_boosted_effect", () -> new JuiceEffect(0x006400,
            app(MobEffects.DOLPHINS_GRACE, 0),
            app(MobEffects.WATER_BREATHING, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> GLISTERING_MELON_EFFECT = EFFECT.register("glistering_melon_effect", () -> new JuiceEffect(0xFFE4B5,
            app(MobEffects.REGENERATION, 0),
            app(MobEffects.SLOW_FALLING, 0),
            app(MobEffects.MOVEMENT_SPEED, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> GLISTERING_MELON_BOOSTED_EFFECT = EFFECT.register("glistering_melon_boosted_effect", () -> new JuiceEffect(0xFFE4B5,
            app(MobEffects.REGENERATION, 1),
            app(MobEffects.SLOW_FALLING, 0),
            app(MobEffects.MOVEMENT_SPEED, 0),
            app(MobEffects.SATURATION, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> GOLDENAPPLE_EFFECT = EFFECT.register("goldenapple_effect", () -> new JuiceEffect(0xFFD700,
            app(MobEffects.ABSORPTION, 0),
            app(MobEffects.FIRE_RESISTANCE, 0),
            app(MobEffects.NIGHT_VISION, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> GOLDENAPPLE_BOOSTED_EFFECT = EFFECT.register("goldenapple_boosted_effect", () -> new JuiceEffect(0xFFD700,
            app(MobEffects.ABSORPTION, 0),
            app(MobEffects.FIRE_RESISTANCE, 0),
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.REGENERATION, 1),
            app(MobEffects.DAMAGE_RESISTANCE, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> GOLDENCARROT_EFFECT = EFFECT.register("goldencarrot_effect", () -> new JuiceEffect(0xDAA520,
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.WATER_BREATHING, 0),
            app(MobEffects.DOLPHINS_GRACE, 1)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> GOLDENCARROT_BOOSTED_EFFECT = EFFECT.register("goldencarrot_boosted_effect", () -> new JuiceEffect(0xDAA520,
            app(MobEffects.NIGHT_VISION, 0),
            app(MobEffects.WATER_BREATHING, 0),
            app(MobEffects.DOLPHINS_GRACE, 1),
            app(MobEffects.LUCK, 1)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> CHORUS_EFFECT = EFFECT.register("chorus_effect", () -> new JuiceEffect(0xBA55D3,
            app(MobEffects.LEVITATION, 0),
            app(MobEffects.SLOW_FALLING, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> CHORUS_BOOSTED_EFFECT = EFFECT.register("chorus_boosted_effect", () -> new JuiceEffect(0xBA55D3,
            app(MobEffects.LEVITATION, 0),
            app(MobEffects.SLOW_FALLING, 1)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> GLOWBERRY_EFFECT = EFFECT.register("glowberry_effect", () -> new JuiceEffect(0xFFFF00,
            app(MobEffects.GLOWING, 0),
            app(MobEffects.NIGHT_VISION, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> GLOWBERRY_BOOSTED_EFFECT = EFFECT.register("glowberry_boosted_effect", () -> new JuiceEffect(0xFFFF00,
            app(MobEffects.GLOWING, 0),
            app(MobEffects.NIGHT_VISION, 0),
            app(FLOAT, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> SPICY_EFFECT = EFFECT.register("spicy_effect", () -> new JuiceEffect(0xFF4500,
            app(SPICY, 0),
            app(MobEffects.FIRE_RESISTANCE, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> SPICY_BOOSTED_EFFECT = EFFECT.register("spicy_boosted_effect", () -> new JuiceEffect(0xFF4500,
            app(SPICY, 1),
            app(MobEffects.FIRE_RESISTANCE, 0),
            app(MobEffects.DAMAGE_BOOST, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> GOLEM_EFFECT = EFFECT.register("golem_effect", () -> new JuiceEffect(0xA9A9A9,
            app(MobEffects.ABSORPTION, 2),
            app(MobEffects.DAMAGE_RESISTANCE, 0),
            app(MobEffects.MOVEMENT_SLOWDOWN, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> GOLEM_BOOSTED_EFFECT = EFFECT.register("golem_boosted_effect", () -> new JuiceEffect(0xA9A9A9,
            app(MobEffects.ABSORPTION, 4),
            app(MobEffects.DAMAGE_RESISTANCE, 1),
            app(MobEffects.MOVEMENT_SLOWDOWN, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> SUNBERRY_EFFECT = EFFECT.register("sunberry_effect", () -> new JuiceEffect(0xFFFF66,
            app(CAFFEINATED, 0),
            app(MobEffects.MOVEMENT_SPEED, 1)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> SUNBERRY_BOOSTED_EFFECT = EFFECT.register("sunberry_boosted_effect", () -> new JuiceEffect(0xFFFF66,
            app(CAFFEINATED, 1),
            app(MobEffects.MOVEMENT_SPEED, 1),
            app(MobEffects.JUMP, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> BEETROOT_EFFECT = EFFECT.register("beetroot_effect", () -> new JuiceEffect(0x8B0000,
            app(MobEffects.SATURATION, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> BEETROOT_BOOSTED_EFFECT = EFFECT.register("beetroot_boosted_effect", () -> new JuiceEffect(0x8B0000,
            app(MobEffects.SATURATION, 0),
            app(MobEffects.DAMAGE_RESISTANCE, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> NETHERWART_EFFECT = EFFECT.register("netherwart_effect", () -> new JuiceEffect(0x800000,
            app(MobEffects.FIRE_RESISTANCE, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> NETHERWART_BOOSTED_EFFECT = EFFECT.register("netherwart_boosted_effect", () -> new JuiceEffect(0x800000,
            app(MobEffects.FIRE_RESISTANCE, 0),
            app(MobEffects.DAMAGE_BOOST, 0)));

    public static final DeferredHolder<MobEffect, ? extends MobEffect> COCOA_EFFECT = EFFECT.register("cocoa_effect", () -> new JuiceEffect(0x8B4513,
            app(MobEffects.DIG_SPEED, 0)));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> COCOA_BOOSTED_EFFECT = EFFECT.register("cocoa_boosted_effect", () -> new JuiceEffect(0x8B4513,
            app(MobEffects.DIG_SPEED, 1),
            app(MobEffects.NIGHT_VISION, 0)));

    // Append new powers after the original effects to preserve their registration order.
    public static final DeferredHolder<MobEffect, ? extends MobEffect> ORCHARD_GUARD = EFFECT.register("orchard_guard", () -> new me.ez.jej.common.Effects.FruitPowerEffect(0x82B39B, false));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> FROSTBITE = EFFECT.register("frostbite", () -> new me.ez.jej.common.Effects.FruitPowerEffect(0xBDF6FF, false));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> SOLAR_CHARGE = EFFECT.register("solar_charge", () -> new me.ez.jej.common.Effects.FruitPowerEffect(0xF3D75D, true));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> FORAGERS_LUCK = EFFECT.register("foragers_luck", () -> new me.ez.jej.common.Effects.FruitPowerEffect(0xA778CC, false));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> CAFFEINE_CRASH = EFFECT.register("caffeine_crash", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x725044) {}
            .addAttributeModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("jej", "caffeine_crash_speed"), -.3, net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, ? extends MobEffect> CHILLED = EFFECT.register("chilled", () -> new MobEffect(MobEffectCategory.HARMFUL, 0xBDF6FF) {}
            .addAttributeModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("jej", "chilled_speed"), -.15, net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    private static JuiceEffect.JuiceApplication app(net.minecraft.core.Holder<MobEffect> effect, int amplifier) {
        return new JuiceEffect.JuiceApplication(effect, amplifier);
    }

    private static JuiceEffect.JuiceApplication app(JuicePower power, int amplifier) {
        return new JuiceEffect.JuiceApplication(power, amplifier);
    }

}
