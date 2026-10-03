package me.ez.jej.common.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FruitPowerEffect extends MobEffect {
    private final boolean solar;

    public FruitPowerEffect(int color, boolean solar) {
        super(MobEffectCategory.BENEFICIAL, color);
        this.solar = solar;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return solar && duration % 40 == 0;
    }

    //? if >=1.21.2 {
    /*@Override
    public boolean applyEffectTick(net.minecraft.server.level.ServerLevel serverLevel, LivingEntity entity, int amplifier) {
    *///?} else {
    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
    //?}
        if (!entity.level().isClientSide() && entity.level().isDay() && entity.level().canSeeSky(entity.blockPosition())
                && !entity.level().isRainingAt(entity.blockPosition()) && entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(.5F * (Math.min(2, amplifier) + 1));
        }
        return true;
    }
}
