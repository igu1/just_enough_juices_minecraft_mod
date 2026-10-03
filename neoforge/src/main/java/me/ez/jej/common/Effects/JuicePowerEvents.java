package me.ez.jej.common.Effects;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

/** Runs every juice power without ever creating a vanilla status-effect instance. */
@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class JuicePowerEvents {
    @SubscribeEvent public static void tick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        int floatLevel = entity.hasEffect(Init.FLOAT.get()) ? 2 : 0;
        int levitation = Math.max(floatLevel, JuiceEffect.level(entity, JuicePower.LEVITATION));
        boolean slowFall = floatLevel > 0 || JuiceEffect.level(entity, JuicePower.SLOW_FALLING) > 0;
        Vec3 motion = entity.getDeltaMovement();
        if (levitation > 0) {
            entity.setDeltaMovement(motion.x, motion.y + (.05 * levitation - motion.y) * .2, motion.z);
            entity.fallDistance = 0;
        } else if (slowFall && motion.y < 0) {
            entity.setDeltaMovement(motion.x, Math.max(motion.y, -.125), motion.z);
            entity.fallDistance = 0;
        }
        if (entity.isInWater() && JuiceEffect.level(entity, JuicePower.DOLPHINS_GRACE) > 0)
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.03, 1, 1.03));
        if (entity.level.isClientSide) return;
        if (JuiceEffect.level(entity, JuicePower.WATER_BREATHING) > 0) entity.setAirSupply(entity.getMaxAirSupply());
        if (JuiceEffect.level(entity, JuicePower.FIRE_RESISTANCE) > 0) entity.clearFire();
        int regen = JuiceEffect.level(entity, JuicePower.REGENERATION);
        if (regen > 0 && entity.tickCount % Math.max(1, 50 >> (regen - 1)) == 0 && entity.getHealth() < entity.getMaxHealth()) entity.heal(1);
        if (JuiceEffect.level(entity, JuicePower.GLOWING) > 0) {
            entity.setGlowingTag(true);
            entity.getPersistentData().putBoolean("jejGlowing", true);
        } else if (entity.getPersistentData().getBoolean("jejGlowing")) {
            entity.setGlowingTag(entity.hasEffect(MobEffects.GLOWING));
            entity.getPersistentData().remove("jejGlowing");
        }
    }

    @SubscribeEvent public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (JuiceEffect.level(player, JuicePower.INVISIBILITY) > 0) {
            player.setInvisible(true);
            player.getPersistentData().putBoolean("jejInvisible", true);
        } else if (player.getPersistentData().getBoolean("jejInvisible")) {
            player.setInvisible(player.hasEffect(MobEffects.INVISIBILITY));
            player.getPersistentData().remove("jejInvisible");
        }
    }

    @SubscribeEvent public static void expiry(PotionEvent.PotionExpiryEvent event) {
        if (event.getPotionEffect() == null || event.getPotionEffect().getEffect() == null) return;
        if (event.getPotionEffect().getEffect() instanceof JuiceEffect juice && juice.hasCaffeine()) {
            event.getEntityLiving().addEffect(new net.minecraft.world.effect.MobEffectInstance(Init.CAFFEINE_CRASH.get(), 400));
        }
    }

    @SubscribeEvent public static void jump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        int level = JuiceEffect.level(entity, JuicePower.JUMP);
        if (level > 0) entity.setDeltaMovement(entity.getDeltaMovement().add(0, .1 * level, 0));
    }

    @SubscribeEvent public static void hurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (event.getSource() == DamageSource.OUT_OF_WORLD) return;
        if (event.getSource().isFire() && JuiceEffect.level(entity, JuicePower.FIRE_RESISTANCE) > 0) {
            event.setCanceled(true);
            return;
        }
        if (!event.getSource().isBypassMagic()) {
            int resistance = JuiceEffect.level(entity, JuicePower.DAMAGE_RESISTANCE);
            if (resistance > 0) event.setAmount(event.getAmount() * Math.max(0F, 1 - .2F * resistance));
        }
    }

    @SubscribeEvent public static void mining(PlayerEvent.BreakSpeed event) {
        int level = JuiceEffect.level(event.getPlayer(), JuicePower.DIG_SPEED);
        var caffeine = event.getPlayer().getEffect(Init.CAFFEINATED.get());
        if (caffeine != null) level = Math.max(level, Math.min(3, caffeine.getAmplifier() + 1));
        float speed = event.getNewSpeed();
        if (level > 0) speed *= 1 + .2F * level;
        if (event.getPlayer().hasEffect(Init.CAFFEINE_CRASH.get())) speed *= .6F;
        event.setNewSpeed(speed);
    }
}
