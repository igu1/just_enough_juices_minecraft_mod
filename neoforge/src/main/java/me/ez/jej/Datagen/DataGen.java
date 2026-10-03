package me.ez.jej.Datagen;

import me.ez.jej.Datagen.LootTable.BlockLootTable;
import me.ez.jej.Main;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(new ItemModelProvider(generator, existingFileHelper));
        generator.addProvider(new BlockStateModelProvider(generator, existingFileHelper));
        generator.addProvider(new RecipeProvider(generator));
        generator.addProvider(new LanguageProvider(generator, "en_us"));
        generator.addProvider(new LootTableProvider(generator));
    }
}
