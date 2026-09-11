package me.ez.jej.common.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JuiceEffect extends MobEffect {

    public static class JuiceApplication {
        public final MobEffect effect;
        public final int amplifier;

        public JuiceApplication(MobEffect effect, int amplifier) {
            this.effect = effect;
            this.amplifier = amplifier;
        }
    }

    private final List<JuiceApplication> applications;
    private final Map<UUID, Boolean> instantApplied = new HashMap<>();

    public JuiceEffect(int color, JuiceApplication... applications) {
        super(MobEffectCategory.BENEFICIAL, color);
        this.applications = Arrays.asList(applications);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amp) {
        if (entity.isEffectiveAi()) {
            for (JuiceApplication app : applications) {
                if (app.effect.isInstantenous()) {
                    if (entity instanceof Player && instantApplied.putIfAbsent(entity.getUUID(), Boolean.TRUE) == null) {
                        app.effect.applyInstantenousEffect(entity, entity, entity, app.amplifier, 1.0D);
                    }
                } else if (!entity.hasEffect(app.effect)) {
                    entity.addEffect(new MobEffectInstance(app.effect, 60, app.amplifier, false, false));
                }
            }
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amp) {
        super.removeAttributeModifiers(entity, attributeMap, amp);
        instantApplied.remove(entity.getUUID());
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amp) {
        return true;
    }
}