package me.ez.jej.common;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;

@EventBusSubscriber(modid = Main.MOD_ID)
public class JuiceCapabilities {
    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Init.JUICE_TABLE_ENTITY.get(),
                (table, side) -> side == null ? new InvWrapper(table) : new SidedInvWrapper(table, side));
    }
}
