package me.ez.jej.common.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class CaffeinatedEffect extends MobEffect {

    public CaffeinatedEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amp) {
        if (entity.isEffectiveAi()) {
            if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED)) {
                entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, Math.min(amp, 2)));
            }
            if (!entity.hasEffect(MobEffects.DIG_SPEED)) {
                entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.DIG_SPEED, 1, Math.min(amp, 2)));
            }
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amp) {
        super.removeAttributeModifiers(entity, attributeMap, amp);
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 1));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.DIG_SLOWDOWN, 400, 1));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amp) {
        return true;
    }
}