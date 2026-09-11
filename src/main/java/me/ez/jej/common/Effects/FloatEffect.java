package me.ez.jej.common.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class FloatEffect extends MobEffect {

    public FloatEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amp) {
        if (entity.isEffectiveAi()) {
            if (!entity.hasEffect(MobEffects.LEVITATION)) {
                entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.LEVITATION, 1, 1, false, false));
            }
            if (!entity.hasEffect(MobEffects.SLOW_FALLING)) {
                entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.SLOW_FALLING, 1, 0, false, false));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amp) {
        return true;
    }
}