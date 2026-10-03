package me.ez.jej.common.Effects;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class JuiceEffect extends MobEffect {
    public static class JuiceApplication {
        public final MobEffect effect;
        public final JuicePower power;
        public final int amplifier;
        public JuiceApplication(MobEffect effect, int amplifier) {
            this.power = JuicePower.fromEffect(effect);
            this.effect = power == null ? effect : null;
            this.amplifier = amplifier;
        }
        public JuiceApplication(JuicePower power, int amplifier) {
            this.effect = null; this.power = power; this.amplifier = amplifier;
        }
    }
    public final List<JuiceApplication> applications;
    public JuiceEffect(int color, JuiceApplication... applications) {
        super(MobEffectCategory.BENEFICIAL, color);
        this.applications = Arrays.asList(applications);
        for (JuiceApplication app : applications) {
            if (app.power == JuicePower.MOVEMENT_SPEED || app.power == JuicePower.MOVEMENT_SLOWDOWN)
                addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID.randomUUID().toString(),
                        (app.power == JuicePower.MOVEMENT_SPEED ? .2 : -.15) * (app.amplifier + 1), AttributeModifier.Operation.MULTIPLY_TOTAL);
            if (app.power == JuicePower.DAMAGE_BOOST)
                addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID.randomUUID().toString(), 3 * (app.amplifier + 1), AttributeModifier.Operation.ADDITION);
            if (app.power == JuicePower.LUCK)
                addAttributeModifier(Attributes.LUCK, UUID.randomUUID().toString(), app.amplifier + 1, AttributeModifier.Operation.ADDITION);
        }
    }
    public int powerLevel(JuicePower power) {
        return applications.stream().filter(a -> a.power == power).mapToInt(a -> a.amplifier + 1).max().orElse(0);
    }
    public boolean hasCaffeine() {
        return applications.stream().anyMatch(a -> a.effect == me.ez.jej.Init.CAFFEINATED.get());
    }
    public static int level(LivingEntity entity, JuicePower power) {
        return entity.getActiveEffects().stream().filter(i -> i.getEffect() instanceof JuiceEffect)
                .mapToInt(i -> ((JuiceEffect)i.getEffect()).powerLevel(power)).max().orElse(0);
    }
    @Override public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amp) {
        super.addAttributeModifiers(entity, attributes, amp);
        int absorption = powerLevel(JuicePower.ABSORPTION);
        if (absorption > 0) entity.setAbsorptionAmount(entity.getAbsorptionAmount() + 4 * absorption);
        if (powerLevel(JuicePower.SATURATION) > 0 && entity instanceof Player player)
            player.getFoodData().eat(1, 1);
    }
    @Override public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amp) {
        super.removeAttributeModifiers(entity, attributes, amp);
        int absorption = powerLevel(JuicePower.ABSORPTION);
        if (absorption > 0) entity.setAbsorptionAmount(Math.max(0, entity.getAbsorptionAmount() - 4 * absorption));
    }
    @Override public void applyEffectTick(LivingEntity entity, int amp) {
        if (entity.level.isClientSide) return;
        for (JuiceApplication app : applications) {
            if (app.effect != null && !entity.hasEffect(app.effect))
                entity.addEffect(new MobEffectInstance(app.effect, 60, app.amplifier, false, false));
        }
    }
    @Override public boolean isDurationEffectTick(int duration, int amp) { return true; }

    /** Adds a readable breakdown of this juice's powers to an item tooltip. */
    public void appendTooltip(List<Component> out) {
        for (JuiceApplication app : applications) {
            if (app.power != null) {
                String key = "jej.power." + app.power.name().toLowerCase(Locale.ROOT);
                MutableComponent name = new TranslatableComponent(key);
                if (app.amplifier > 0) name = new TranslatableComponent("potion.withAmplifier", name,
                        new TranslatableComponent("potion.potency." + app.amplifier));
                out.add(name.withStyle(ChatFormatting.BLUE));
                out.add(new TranslatableComponent(key + ".desc").withStyle(ChatFormatting.GRAY));
            } else if (app.effect != null) {
                MutableComponent name = new TranslatableComponent(app.effect.getDescriptionId());
                if (app.amplifier > 0) name = new TranslatableComponent("potion.withAmplifier", name,
                        new TranslatableComponent("potion.potency." + app.amplifier));
                out.add(name.withStyle(ChatFormatting.BLUE));
                out.add(new TranslatableComponent(app.effect.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
