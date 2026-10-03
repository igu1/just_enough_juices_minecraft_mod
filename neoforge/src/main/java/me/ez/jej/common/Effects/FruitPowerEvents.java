package me.ez.jej.common.Effects;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Main.MOD_ID)
public class FruitPowerEvents {
    @SubscribeEvent
    public static void hurt(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide || event.getAmount() <= 0) return;
        var guard = entity.getEffect(Init.ORCHARD_GUARD);
        if (guard != null && !event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            event.setAmount(event.getAmount() * (1 - Math.min(.4F, .15F * (guard.getAmplifier() + 1))));
        }
        var frost = entity.getEffect(Init.FROSTBITE);
        if (frost != null && event.getSource().getEntity() instanceof LivingEntity attacker && attacker != entity) {
            attacker.addEffect(new MobEffectInstance(Init.CHILLED,
                    80 + 40 * Math.min(2, frost.getAmplifier()), Math.min(2, frost.getAmplifier())));
            attacker.setTicksFrozen(Math.max(attacker.getTicksFrozen(), 100));
        }
    }
}
