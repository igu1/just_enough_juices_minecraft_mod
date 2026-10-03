package me.ez.jej.common.Effects;

/** Mechanics owned by juice effects, not vanilla status-effect instances. */
public enum JuicePower {
    NIGHT_VISION, REGENERATION, MOVEMENT_SPEED, DAMAGE_BOOST, DAMAGE_RESISTANCE,
    DIG_SPEED, JUMP, INVISIBILITY, WATER_BREATHING, ABSORPTION, DOLPHINS_GRACE,
    SLOW_FALLING, SATURATION, FIRE_RESISTANCE, LUCK, LEVITATION, GLOWING, MOVEMENT_SLOWDOWN;

    public static JuicePower fromEffect(net.minecraft.world.effect.MobEffect effect) {
        var name = effect.getRegistryName();
        if (name == null || !name.getNamespace().equals("minecraft")) return null;
        return switch (name.getPath()) {
            case "speed" -> MOVEMENT_SPEED;
            case "slowness" -> MOVEMENT_SLOWDOWN;
            case "haste" -> DIG_SPEED;
            case "strength" -> DAMAGE_BOOST;
            case "resistance" -> DAMAGE_RESISTANCE;
            case "jump_boost" -> JUMP;
            default -> valueOf(effect.getRegistryName().getPath().toUpperCase(java.util.Locale.ROOT));
        };
    }
}
