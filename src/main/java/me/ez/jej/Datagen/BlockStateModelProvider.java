package me.ez.jej.Datagen;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStateModelProvider extends BlockStateProvider {

    public BlockStateModelProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeBush(Init.WILD_BERRY_BUSH.get(),
                "wildberry_bush_stage",
                "wildberry_bush_stage");
        makeBush(Init.ICE_BERRY_BUSH.get(),
                "iceberry_bush_stage",
                "iceberry_bush_stage");
        makeBush(Init.SUN_BERRY_BUSH.get(),
                "sunberry_bush_stage",
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
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(BlockStateProperties.AGE_3),
                new ResourceLocation(Main.MOD_ID, "block/" + textureName + state.getValue(BlockStateProperties.AGE_3))));
        return models;
    }
}
