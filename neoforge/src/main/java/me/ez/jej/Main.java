package me.ez.jej;

import me.ez.jej.Events.VillagerTradeHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ComposterBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Main.MOD_ID)
public class Main {

    public static final String MOD_ID = "jej";

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        Init.EFFECT.register(modEventBus);
        Init.ITEMS.register(modEventBus);
        Init.BLOCKS.register(modEventBus);
        Init.registerTable(modEventBus);

        TABS.register("juices", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.juices"))
                .icon(() -> new ItemStack(Init.GOLDENAPPLE_JUICE.get()))
                .displayItems((params, output) -> Init.ITEMS.getEntries().forEach(holder -> output.accept(holder.get())))
                .build());
        TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        NeoForge.EVENT_BUS.register(VillagerTradeHandler.class);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
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
}
