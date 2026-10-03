package me.ez.jej;

import me.ez.jej.WorldGen.generation.ModBushGeneration;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.world.BiomeLoadingEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegisterEvents {

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent e){
        ModBushGeneration.generateBush(e);
    }
}
