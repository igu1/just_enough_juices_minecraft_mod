package me.ez.jej.common.Bushes;

import me.ez.jej.common.ModBushBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GlowBerryBush extends ModBushBlock {

    public GlowBerryBush() {
        super(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).lightLevel(state -> state.getValue(AGE) >= 3 ? 8 : 0));
    }
}