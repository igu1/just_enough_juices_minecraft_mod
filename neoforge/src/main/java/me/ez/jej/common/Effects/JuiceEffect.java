package me.ez.jej.common.Effects;

import me.ez.jej.Init;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class JuiceEffect extends MobEffect {
    public static class JuiceApplication {
        public final Holder<MobEffect> effect;
        public final JuicePower power;
        public final int amplifier;

        public JuiceApplication(Holder<MobEffect> effect, int amplifier) {
            this.power = JuicePower.fromEffect(effect);
            this.effect = power == null ? effect : null;
            this.amplifier = amplifier;
        }

        public JuiceApplication(JuicePower power, int amplifier) {
            this.effect = null;
            this.power = power;
            this.amplifier = amplifier;
        }
    }

    public final List<JuiceApplication> applications;

    public JuiceEffect(int color, JuiceApplication... applications) {
        super(MobEffectCategory.BENEFICIAL, color);
        this.applications = Arrays.asList(applications);
        for (JuiceApplication app : applications) {
            if (app.power == JuicePower.MOVEMENT_SPEED || app.power == JuicePower.MOVEMENT_SLOWDOWN)
                addAttributeModifier(Attributes.MOVEMENT_SPEED, id(),
                        (app.power == JuicePower.MOVEMENT_SPEED ? .2 : -.15) * (app.amplifier + 1), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            if (app.power == JuicePower.DAMAGE_BOOST)
                addAttributeModifier(Attributes.ATTACK_DAMAGE, id(), 3 * (app.amplifier + 1), AttributeModifier.Operation.ADD_VALUE);
            if (app.power == JuicePower.LUCK)
                addAttributeModifier(Attributes.LUCK, id(), app.amplifier + 1, AttributeModifier.Operation.ADD_VALUE);
        }
    }

    private static ResourceLocation id() {
        return ResourceLocation.fromNamespaceAndPath("jej", UUID.randomUUID().toString());
    }

    public int powerLevel(JuicePower power) {
        return applications.stream().filter(a -> a.power == power).mapToInt(a -> a.amplifier + 1).max().orElse(0);
    }

    public boolean hasCaffeine() {
        return applications.stream().anyMatch(a -> a.effect != null
                && a.effect.unwrapKey().map(key -> key.equals(Init.CAFFEINATED.getKey())).orElse(false));
    }

    public static int level(LivingEntity entity, JuicePower power) {
        return entity.getActiveEffects().stream()
                .filter(i -> i.getEffect().value() instanceof JuiceEffect)
                .mapToInt(i -> ((JuiceEffect) i.getEffect().value()).powerLevel(power))
                .max().orElse(0);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amp) {
        if (entity.level().isClientSide) return true;
        for (JuiceApplication app : applications) {
            if (app.effect != null && !entity.hasEffect(app.effect))
                entity.addEffect(new MobEffectInstance(app.effect, 60, app.amplifier, false, false, false));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amp) {
        return true;
    }

    /** Adds a readable breakdown of this juice's powers to an item tooltip. */
    public void appendTooltip(List<Component> out) {
        for (JuiceApplication app : applications) {
            if (app.power != null) {
                String key = "jej.power." + app.power.name().toLowerCase(Locale.ROOT);
                MutableComponent name = Component.translatable(key);
                if (app.amplifier > 0) name = Component.translatable("potion.withAmplifier", name,
                        Component.translatable("potion.potency." + app.amplifier));
                out.add(name.withStyle(ChatFormatting.BLUE));
                out.add(Component.translatable(key + ".desc").withStyle(ChatFormatting.GRAY));
            } else if (app.effect != null) {
                String id = descriptionId(app.effect);
                MutableComponent name = Component.translatable(id);
                if (app.amplifier > 0) name = Component.translatable("potion.withAmplifier", name,
                        Component.translatable("potion.potency." + app.amplifier));
                out.add(name.withStyle(ChatFormatting.BLUE));
                out.add(Component.translatable(id + ".desc").withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public static String descriptionId(Holder<MobEffect> effect) {
        return effect.unwrapKey()
                .map(key -> {
                    ResourceLocation id = key.location();
                    return "effect." + id.getNamespace() + "." + id.getPath();
                })
                .orElse("effect.unknown");
    }
}
