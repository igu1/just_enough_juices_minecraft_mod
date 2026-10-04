package me.ez.jej.common.Bushes;

import com.mojang.serialization.MapCodec;
import me.ez.jej.common.ModBushBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SunBerryBush extends ModBushBlock {

    public SunBerryBush() {
        //? if >=1.21.2 {
        /*super(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).setId(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BLOCK, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("jej", "sunberry_bush"))));
        *///?} else {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH));
        //?}
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(props -> new SunBerryBush());
    }
}
