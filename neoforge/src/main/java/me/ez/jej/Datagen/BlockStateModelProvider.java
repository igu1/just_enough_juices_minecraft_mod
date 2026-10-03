//? if <1.21.2 {
package me.ez.jej.Datagen;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStateModelProvider extends BlockStateProvider {

    public BlockStateModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeBush(Init.WILD_BERRY_BUSH.get(),
                "wildberry_bush_blockbench_stage",
                "wildberry_bush_stage");
        makeBush(Init.ICE_BERRY_BUSH.get(),
                "iceberry_bush_blockbench_stage",
                "iceberry_bush_stage");
        makeBush(Init.SUN_BERRY_BUSH.get(),
                "sunberry_bush_blockbench_stage",
                "sunberry_bush_stage");
        makeBush(Init.GLOW_BERRY_BUSH.get(),
                "glowberry_bush_stage",
                "glowberry_bush_stage");
    }

    public void makeBush(BushBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, BushBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        if (block == Init.GLOW_BERRY_BUSH.get()) {
            models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(BlockStateProperties.AGE_3),
                    ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "block/" + textureName + state.getValue(BlockStateProperties.AGE_3))));
        } else {
            models[0] = new ConfiguredModel(models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Main.MOD_ID,
                    "block/" + modelName + state.getValue(BlockStateProperties.AGE_3))));
        }
        return models;
    }
}

//?}
