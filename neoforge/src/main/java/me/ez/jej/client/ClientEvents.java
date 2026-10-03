package me.ez.jej.client;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void models(ModelEvent.RegisterAdditional event) {
        //? if >=1.21.2 {
        /*event.register(JuiceTableRenderer.SCREW);
        event.register(JuiceTableRenderer.RAM);
        *///?} else {
        event.register(ModelResourceLocation.standalone(JuiceTableRenderer.SCREW));
        event.register(ModelResourceLocation.standalone(JuiceTableRenderer.RAM));
        //?}
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(Init.JUICE_TABLE_ENTITY.get(), JuiceTableRenderer::new);
    }

    @SubscribeEvent
    public static void screens(RegisterMenuScreensEvent event) {
        event.register(Init.JUICE_TABLE_MENU.get(), JuiceTableScreen::new);
    }
}
