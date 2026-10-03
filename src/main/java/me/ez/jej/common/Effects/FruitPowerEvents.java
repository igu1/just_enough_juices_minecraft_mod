package me.ez.jej.common.Effects;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class FruitPowerEvents {
    @SubscribeEvent public static void hurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.level.isClientSide || event.getAmount() <= 0) return;
        var guard = entity.getEffect(Init.ORCHARD_GUARD.get());
        if (guard != null && event.getSource() != DamageSource.OUT_OF_WORLD) {
            event.setAmount(event.getAmount() * (1 - Math.min(.4F, .15F * (guard.getAmplifier() + 1))));
        }
        var frost = entity.getEffect(Init.FROSTBITE.get());
        if (frost != null && event.getSource().getEntity() instanceof LivingEntity attacker && attacker != entity) {
            attacker.addEffect(new MobEffectInstance(Init.CHILLED.get(), 80 + 40 * Math.min(2, frost.getAmplifier()), Math.min(2, frost.getAmplifier())));
            attacker.setTicksFrozen(Math.max(attacker.getTicksFrozen(), 100));
        }
    }
}
