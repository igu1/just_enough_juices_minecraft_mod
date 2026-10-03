package me.ez.jej.common.Effects;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Runs every juice power without ever creating a vanilla status-effect instance. */
@EventBusSubscriber(modid = Main.MOD_ID)
public class JuicePowerEvents {
    @SubscribeEvent
    public static void tick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        int floatLevel = entity.hasEffect(Init.FLOAT) ? 2 : 0;
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
        if (entity.level().isClientSide) return;
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

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (JuiceEffect.level(player, JuicePower.INVISIBILITY) > 0) {
            player.setInvisible(true);
            player.getPersistentData().putBoolean("jejInvisible", true);
        } else if (player.getPersistentData().getBoolean("jejInvisible")) {
            player.setInvisible(player.hasEffect(MobEffects.INVISIBILITY));
            player.getPersistentData().remove("jejInvisible");
        }
    }

    @SubscribeEvent
    public static void effectAdded(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        if (!(event.getEffectInstance().getEffect().value() instanceof JuiceEffect juice)) return;
        int absorption = juice.powerLevel(JuicePower.ABSORPTION);
        if (absorption > 0) entity.setAbsorptionAmount(entity.getAbsorptionAmount() + 4 * absorption);
        if (juice.powerLevel(JuicePower.SATURATION) > 0 && entity instanceof Player player)
            player.getFoodData().eat(1, 1);
    }

    private static void removeAbsorption(MobEffectEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(event.getEffectInstance().getEffect().value() instanceof JuiceEffect juice)) return;
        int absorption = juice.powerLevel(JuicePower.ABSORPTION);
        if (absorption > 0) entity.setAbsorptionAmount(Math.max(0, entity.getAbsorptionAmount() - 4 * absorption));
    }

    @SubscribeEvent
    public static void effectRemoved(MobEffectEvent.Remove event) {
        removeAbsorption(event);
    }

    @SubscribeEvent
    public static void expiry(MobEffectEvent.Expired event) {
        removeAbsorption(event);
        MobEffectInstance instance = event.getEffectInstance();
        if (instance != null && instance.getEffect().value() instanceof JuiceEffect juice && juice.hasCaffeine()) {
            event.getEntity().addEffect(new MobEffectInstance(Init.CAFFEINE_CRASH, 400));
        }
    }

    @SubscribeEvent
    public static void jump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        int level = JuiceEffect.level(entity, JuicePower.JUMP);
        if (level > 0) entity.setDeltaMovement(entity.getDeltaMovement().add(0, .1 * level, 0));
    }

    @SubscribeEvent
    public static void hurt(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;
        if (event.getSource().is(DamageTypeTags.IS_FIRE) && JuiceEffect.level(entity, JuicePower.FIRE_RESISTANCE) > 0) {
            event.setCanceled(true);
            return;
        }
        if (!event.getSource().is(DamageTypeTags.BYPASSES_RESISTANCE)) {
            int resistance = JuiceEffect.level(entity, JuicePower.DAMAGE_RESISTANCE);
            if (resistance > 0) event.setAmount(event.getAmount() * Math.max(0F, 1 - .2F * resistance));
        }
    }

    @SubscribeEvent
    public static void mining(PlayerEvent.BreakSpeed event) {
        int level = JuiceEffect.level(event.getEntity(), JuicePower.DIG_SPEED);
        var caffeine = event.getEntity().getEffect(Init.CAFFEINATED);
        if (caffeine != null) level = Math.max(level, Math.min(3, caffeine.getAmplifier() + 1));
        float speed = event.getNewSpeed();
        if (level > 0) speed *= 1 + .2F * level;
        if (event.getEntity().hasEffect(Init.CAFFEINE_CRASH)) speed *= .6F;
        event.setNewSpeed(speed);
    }
}
