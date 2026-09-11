package me.ez.jej.common.Effects;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class SpicyEffect extends MobEffect {

    public SpicyEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amp) {
        entity.clearFire();
        if (!entity.getLevel().isClientSide && entity.getLevel() instanceof ServerLevel level) {
            int radius = 2 + amp;
            for (Mob mob : level.getEntitiesOfClass(Mob.class,
                    entity.getBoundingBox().inflate(radius),
                    m -> m != entity && m.isAlive())) {
                if (mob.getRandom().nextFloat() < 0.15f) {
                    mob.setSecondsOnFire(3);
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amp) {
        return duration % 20 == 0;
    }
}