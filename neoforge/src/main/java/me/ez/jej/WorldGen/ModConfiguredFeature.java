package me.ez.jej.WorldGen;

import me.ez.jej.Init;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class ModConfiguredFeature<E, I> {

    private static final List<Block> blocks = List.of(
            Blocks.GRASS_BLOCK,
            Blocks.DIRT
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_ICE_BERRY_BUSH = PatchBush(Init.ICE_BERRY_BUSH.get(), blocks, "ice");

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_WILD_BERRY_BUSH = PatchBush(Init.WILD_BERRY_BUSH.get(),blocks, "wild");

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_SUN_BERRY_BUSH = PatchBush(Init.SUN_BERRY_BUSH.get(), List.of(Blocks.SAND, Blocks.RED_SAND), "sun");

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_GLOW_BERRY_BUSH = PatchBush(Init.GLOW_BERRY_BUSH.get(), List.of(Blocks.STONE, Blocks.DEEPSLATE), "glow");


    private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PatchBush(BushBlock block, List<Block> PlaceableBlockList, String registerName){
        return FeatureUtils.register("patch_" + registerName + "_bush",
                Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(
                                block.defaultBlockState()
                                        .setValue(BlockStateProperties.AGE_3, Integer.valueOf(3)))), PlaceableBlockList));
    }
}
