package me.ez.jej.client;

import me.ez.jej.Main;
import me.ez.jej.common.Effects.JuiceEffect;
import me.ez.jej.common.Effects.JuicePower;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-only visuals for juice powers. Night Vision is approximated by raising an internal
 * gamma value, so no vanilla status effect is ever created.
 */
@Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
public class JuicePowerClient {
    private static Double savedGamma;

    @SubscribeEvent public static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.options == null) return;
        if (mc.player == null) {
            if (savedGamma != null) { mc.options.gamma = savedGamma; savedGamma = null; }
            return;
        }
        if (JuiceEffect.level(mc.player, JuicePower.NIGHT_VISION) > 0) {
            if (savedGamma == null) savedGamma = mc.options.gamma;
            mc.options.gamma = Math.max(mc.options.gamma, 1.0D);
        } else if (savedGamma != null) {
            mc.options.gamma = savedGamma;
            savedGamma = null;
        }
    }
}
