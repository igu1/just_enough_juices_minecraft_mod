//? if <1.21.2 {
package me.ez.jej.Datagen;

import me.ez.jej.Datagen.LootTable.BlockLootTable;
import me.ez.jej.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Main.MOD_ID)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new BlockStateModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new LanguageProvider(output, "en_us"));
        generator.addProvider(event.includeServer(), new RecipeProvider(output, event.getLookupProvider()));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, event.getLookupProvider()));
    }
}

//?}
