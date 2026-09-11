package me.ez.jej;

import me.ez.jej.Events.VillagerTradeHandler;
import me.ez.jej.WorldGen.generation.ModBushGeneration;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MOD_ID)
public class Main{

    public static final String MOD_ID = "jej";
    public static CreativeModeTab TAB = new JuiceTab();

    public Main()
    {
        Init.EFFECT.register(FMLJavaModLoadingContext.get().getModEventBus());
        Init.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        Init.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventPriority.HIGH, this::ClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::CommonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(VillagerTradeHandler.class);
    }

    public void CommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            if (!Config.ENABLE_COMPOSTING.get()) {
                return;
            }
            float chance = Config.COMPOST_CHANCE.get().floatValue();
            ComposterBlock.COMPOSTABLES.put(Init.ICE_BERRY.get(), chance);
            ComposterBlock.COMPOSTABLES.put(Init.WILD_BERRY.get(), chance);
            ComposterBlock.COMPOSTABLES.put(Init.SUN_BERRY.get(), chance);
            ComposterBlock.COMPOSTABLES.put(Init.GLOW_BERRY.get(), chance);
        });
    }

    public void ClientSetup(FMLClientSetupEvent event)
    {
        ItemBlockRenderTypes.setRenderLayer(Init.ICE_BERRY_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(Init.WILD_BERRY_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(Init.SUN_BERRY_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(Init.GLOW_BERRY_BUSH.get(), RenderType.cutout());
    }

    public static class JuiceTab extends CreativeModeTab{

        public JuiceTab() {
            super("juices");
        }

        @Override
        public ItemStack makeIcon() {
            return Init.GOLDENAPPLE_JUICE.get().getDefaultInstance();
        }
    }
}
