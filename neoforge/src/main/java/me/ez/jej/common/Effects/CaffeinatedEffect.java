package me.ez.jej.common.Effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;

/** While active it boosts movement speed. The crash is applied by JuicePowerEvents when the juice expires. */
public class CaffeinatedEffect extends MobEffect {
    public CaffeinatedEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("jej", "caffeinated_speed"), .2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
