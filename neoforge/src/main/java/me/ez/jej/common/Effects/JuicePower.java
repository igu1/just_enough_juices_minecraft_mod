package me.ez.jej.common.Effects;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

/** Mechanics owned by juice effects, not vanilla status-effect instances. */
public enum JuicePower {
    NIGHT_VISION, REGENERATION, MOVEMENT_SPEED, DAMAGE_BOOST, DAMAGE_RESISTANCE,
    DIG_SPEED, JUMP, INVISIBILITY, WATER_BREATHING, ABSORPTION, DOLPHINS_GRACE,
    SLOW_FALLING, SATURATION, FIRE_RESISTANCE, LUCK, LEVITATION, GLOWING, MOVEMENT_SLOWDOWN;

    public static JuicePower fromEffect(Holder<MobEffect> effect) {
        return effect.unwrapKey().map(key -> {
            ResourceLocation id = key.location();
            if (!id.getNamespace().equals("minecraft")) return null;
            return switch (id.getPath()) {
                case "speed" -> MOVEMENT_SPEED;
                case "slowness" -> MOVEMENT_SLOWDOWN;
                case "haste" -> DIG_SPEED;
                case "strength" -> DAMAGE_BOOST;
                case "resistance" -> DAMAGE_RESISTANCE;
                case "jump_boost" -> JUMP;
                default -> valueOf(id.getPath().toUpperCase(java.util.Locale.ROOT));
            };
        }).orElse(null);
    }
}
