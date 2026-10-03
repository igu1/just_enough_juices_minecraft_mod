package me.ez.jej.common.Effects;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;

/** While active it boosts movement speed. The crash is applied by JuicePowerEvents when the juice expires. */
public class CaffeinatedEffect extends MobEffect {
    public CaffeinatedEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "4936f44b-0f47-4871-93ac-797da0f8ad21", .2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
