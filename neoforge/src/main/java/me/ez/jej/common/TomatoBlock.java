package me.ez.jej.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class TomatoBlock extends Block {

    public TomatoBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }


    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (level.isThundering()){
            if (random.nextInt(5) == 0){
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt.setPos(pos.getX() ,pos.getY(), pos.getZ());
                level.addFreshEntity(lightningBolt);
                level.setBlock(pos, Blocks.EMERALD_BLOCK.defaultBlockState(), 1);
            }
        }
    }
}
