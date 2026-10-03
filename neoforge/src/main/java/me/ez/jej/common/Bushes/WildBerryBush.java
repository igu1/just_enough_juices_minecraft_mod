package me.ez.jej.common.Bushes;

import me.ez.jej.common.ModBushBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WildBerryBush extends ModBushBlock {

    public WildBerryBush() {
        super(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH));
    }
}
