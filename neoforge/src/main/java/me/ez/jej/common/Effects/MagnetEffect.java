package me.ez.jej.common.Effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;

public class MagnetEffect extends MobEffect {

    public MagnetEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amp) {
        if (!entity.getLevel().isClientSide && entity.getLevel() instanceof ServerLevel level) {
            double radius = 6.0D + amp * 3.0D;
            for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class,
                    entity.getBoundingBox().inflate(radius),
                    item -> item.isAlive() && !item.hasPickUpDelay())) {
                item.setPos(net.minecraft.util.Mth.lerp(0.1D, item.getX(), entity.getX()),
                        net.minecraft.util.Mth.lerp(0.1D, item.getY(), entity.getY() + entity.getEyeHeight() * 0.5D),
                        net.minecraft.util.Mth.lerp(0.1D, item.getZ(), entity.getZ()));
                item.setPickUpDelay(0);
            }
            for (ExperienceOrb orb : level.getEntitiesOfClass(ExperienceOrb.class,
                    entity.getBoundingBox().inflate(radius),
                    orb -> orb.isAlive())) {
                orb.setPos(net.minecraft.util.Mth.lerp(0.1D, orb.getX(), entity.getX()),
                        net.minecraft.util.Mth.lerp(0.1D, orb.getY(), entity.getY() + entity.getEyeHeight() * 0.5D),
                        net.minecraft.util.Mth.lerp(0.1D, orb.getZ(), entity.getZ()));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amp) {
        return true;
    }
}