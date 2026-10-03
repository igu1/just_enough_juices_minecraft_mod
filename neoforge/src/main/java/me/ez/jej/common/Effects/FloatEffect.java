package me.ez.jej.common.Effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/** Motion is handled by JuicePowerEvents so no vanilla effects are added. */
public class FloatEffect extends MobEffect {
    public FloatEffect(int color) { super(MobEffectCategory.BENEFICIAL, color); }
}
