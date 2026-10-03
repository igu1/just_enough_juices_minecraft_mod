package me.ez.jej.common.Effects;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.Random;

public class IcyFootEffect extends MobEffect {

    public IcyFootEffect(int amp) {
        super(MobEffectCategory.BENEFICIAL, amp);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amp) {
        if (livingEntity.isOnGround()) {
            Random r = new Random();
            BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
            float f = (float)Math.min(16, 2 + amp);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(BlockPos blockpos : BlockPos.betweenClosed(livingEntity.getOnPos().above().offset((double)(-f), -1.0D, (double)(-f)), livingEntity.getOnPos().offset((double)f, -1.0D, (double)f))) {
                if (blockpos.closerToCenterThan(livingEntity.position(), (double)f)) {
                    blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = livingEntity.getLevel().getBlockState(blockpos$mutableblockpos);
                    if (blockstate1.isAir()) {
                        BlockState blockstate2 = livingEntity.level.getBlockState(blockpos);
                        boolean isFull = blockstate2.getBlock() == Blocks.WATER && blockstate2.getValue(LiquidBlock.LEVEL) == 0;
                        if (blockstate2.getMaterial() == Material.WATER && isFull && blockstate.canSurvive(livingEntity.level, blockpos) && livingEntity.level.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(livingEntity, net.minecraftforge.common.util.BlockSnapshot.create(livingEntity.level.dimension(), livingEntity.level, blockpos), net.minecraft.core.Direction.UP)) {
                            livingEntity.level.setBlockAndUpdate(blockpos, blockstate);
                            livingEntity.level.scheduleTick(blockpos, Blocks.FROSTED_ICE, Mth.nextInt(r, 60, 120));
                        }
                    }
                }
            }

        }
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
