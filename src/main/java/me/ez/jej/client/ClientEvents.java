package me.ez.jej.client;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent public static void models(net.minecraftforge.client.event.ModelRegistryEvent event) {
        net.minecraftforge.client.model.ForgeModelBakery.addSpecialModel(JuiceTableRenderer.SCREW);
        net.minecraftforge.client.model.ForgeModelBakery.addSpecialModel(JuiceTableRenderer.RAM);
    }
    @SubscribeEvent public static void renderers(net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(Init.JUICE_TABLE_ENTITY.get(), JuiceTableRenderer::new);
    }
    @SubscribeEvent public static void setup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(Init.JUICE_TABLE_MENU.get(), JuiceTableScreen::new);
            ItemBlockRenderTypes.setRenderLayer(Init.JUICE_TABLE.get(), RenderType.cutout());
        });
    }
}
