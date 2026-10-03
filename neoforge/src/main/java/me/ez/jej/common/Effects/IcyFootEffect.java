package me.ez.jej.common.Effects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class IcyFootEffect extends MobEffect {

    public IcyFootEffect(int amp) {
        super(MobEffectCategory.BENEFICIAL, amp);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amp) {
        if (livingEntity.onGround()) {
            RandomSource r = livingEntity.getRandom();
            BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
            int f = Math.min(16, 2 + amp);
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for (BlockPos blockpos : BlockPos.betweenClosed(
                    livingEntity.getOnPos().above().offset(-f, -1, -f),
                    livingEntity.getOnPos().offset(f, -1, f))) {
                if (blockpos.closerToCenterThan(livingEntity.position(), (double) f)) {
                    mutable.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState above = livingEntity.level().getBlockState(mutable);
                    if (above.isAir()) {
                        BlockState state = livingEntity.level().getBlockState(blockpos);
                        boolean isFull = state.is(Blocks.WATER) && state.getFluidState().isSource();
                        if (state.getFluidState().is(FluidTags.WATER) && isFull
                                && blockstate.canSurvive(livingEntity.level(), blockpos)
                                && livingEntity.level().isUnobstructed(blockstate, blockpos, CollisionContext.empty())
                                && !net.neoforged.neoforge.event.EventHooks.onBlockPlace(livingEntity,
                                        net.neoforged.neoforge.common.util.BlockSnapshot.create(
                                                livingEntity.level().dimension(), livingEntity.level(), blockpos),
                                        Direction.UP)) {
                            livingEntity.level().setBlockAndUpdate(blockpos, blockstate);
                            livingEntity.level().scheduleTick(blockpos, Blocks.FROSTED_ICE, Mth.nextInt(r, 60, 120));
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amp) {
        return true;
    }
}
