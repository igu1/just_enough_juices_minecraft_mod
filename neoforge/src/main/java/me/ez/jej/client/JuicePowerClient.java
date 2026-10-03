package me.ez.jej.client;

import me.ez.jej.Main;
import me.ez.jej.common.Effects.JuiceEffect;
import me.ez.jej.common.Effects.JuicePower;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/**
 * Client-only visuals for juice powers. Night Vision is approximated by raising an internal
 * gamma value, so no vanilla status effect is ever created.
 */
@EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
public class JuicePowerClient {
    private static Double savedGamma;

    @SubscribeEvent public static void tick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options == null) return;
        if (mc.player == null) {
            if (savedGamma != null) { mc.options.gamma().set(savedGamma); savedGamma = null; }
            return;
        }
        if (JuiceEffect.level(mc.player, JuicePower.NIGHT_VISION) > 0) {
            if (savedGamma == null) savedGamma = mc.options.gamma().get();
            mc.options.gamma().set(Math.max(mc.options.gamma().get(), 1.0D));
        } else if (savedGamma != null) {
            mc.options.gamma().set(savedGamma);
            savedGamma = null;
        }
    }
}
