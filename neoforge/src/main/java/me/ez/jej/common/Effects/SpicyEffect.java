package me.ez.jej.common.Effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class SpicyEffect extends MobEffect {

    public SpicyEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    //? if >=1.21.2 {
    /*@Override
    public boolean applyEffectTick(net.minecraft.server.level.ServerLevel serverLevel, LivingEntity entity, int amp) {
    *///?} else {
    @Override
    public boolean applyEffectTick(LivingEntity entity, int amp) {
    //?}
        entity.clearFire();
        if (!entity.level().isClientSide() && entity.level() instanceof ServerLevel level) {
            int radius = 2 + amp;
            for (Mob mob : level.getEntitiesOfClass(Mob.class,
                    entity.getBoundingBox().inflate(radius),
                    m -> m != entity && m.isAlive())) {
                if (mob.getRandom().nextFloat() < 0.15f) {
                    mob.igniteForSeconds(3);
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amp) {
        return duration % 20 == 0;
    }
}
