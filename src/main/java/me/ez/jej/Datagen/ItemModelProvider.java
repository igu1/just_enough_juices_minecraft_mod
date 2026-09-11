package me.ez.jej.Datagen;


import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        //Juice (3D bottle model)
        bottleItem(Init.APPLE_JUICE.get());
        bottleItem(Init.CARROT_JUICE.get());
        bottleItem(Init.BAKEDPOTATO_JUICE.get());
        bottleItem(Init.ICEBERRY_JUICE.get());
        bottleItem(Init.PUMPKIN_JUICE.get());
        bottleItem(Init.MELON_JUICE.get());
        bottleItem(Init.DRIEDKELP_JUICE.get());
        bottleItem(Init.WILDBERRY_JUICE.get());

        bottleItem(Init.GOLDENCARROT_JUICE.get());
        bottleItem(Init.GOLDENAPPLE_JUICE.get());
        bottleItem(Init.GLISTERING_MELON_JUICE.get());

        bottleItem(Init.APPLE_JUICE_BOOSTED.get());
        bottleItem(Init.CARROT_JUICE_BOOSTED.get());
        bottleItem(Init.BAKEDPOTATO_JUICE_BOOSTED.get());
        bottleItem(Init.ICEBERRY_JUICE_BOOSTED.get());
        bottleItem(Init.PUMPKIN_JUICE_BOOSTED.get());
        bottleItem(Init.MELON_JUICE_BOOSTED.get());
        bottleItem(Init.DRIEDKELP_JUICE_BOOSTED.get());
        bottleItem(Init.WILDBERRY_JUICE_BOOSTED.get());

        bottleItem(Init.GOLDENAPPLE_JUICE_BOOSTED.get());
        bottleItem(Init.GOLDENCARROT_JUICE_BOOSTED.get());
        bottleItem(Init.GLISTERING_MELON_JUICE_BOOSTED.get());

        //New Juices (3D bottle model)
        bottleItem(Init.CHORUS_JUICE.get());
        bottleItem(Init.CHORUS_JUICE_BOOSTED.get());
        bottleItem(Init.GLOWBERRY_JUICE.get());
        bottleItem(Init.GLOWBERRY_JUICE_BOOSTED.get());
        bottleItem(Init.SPICY_JUICE.get());
        bottleItem(Init.SPICY_JUICE_BOOSTED.get());
        bottleItem(Init.GOLEM_JUICE.get());
        bottleItem(Init.GOLEM_JUICE_BOOSTED.get());
        bottleItem(Init.SUNBERRY_JUICE.get());
        bottleItem(Init.SUNBERRY_JUICE_BOOSTED.get());
        bottleItem(Init.BEETROOT_JUICE.get());
        bottleItem(Init.BEETROOT_JUICE_BOOSTED.get());
        bottleItem(Init.NETHERWART_JUICE.get());
        bottleItem(Init.NETHERWART_JUICE_BOOSTED.get());
        bottleItem(Init.COCOA_JUICE.get());
        bottleItem(Init.COCOA_JUICE_BOOSTED.get());

        //Sweetberry (no liquid texture yet, keep flat for now)
        simpleItem(Init.SWEETBERRY_JUICE.get());
        simpleItem(Init.SWEETBERRY_JUICE_BOOSTED.get());

        //Item
        simpleItem(Init.EMERALD_DUST.get());
        simpleItem(Init.GLASS_BOTTLE.get());

        //BlockItem
        simpleItem(Init.WILD_BERRY.get());
        simpleItem(Init.ICE_BERRY.get());
        simpleItem(Init.SUN_BERRY.get());
        simpleItem(Init.GLOW_BERRY.get());
    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Main.MOD_ID,"item/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder bottleItem(Item item) {
        String path = item.getRegistryName().getPath();
        String liquid;
        if (path.endsWith("_juice_boosted")) {
            liquid = path.substring(0, path.length() - "_juice_boosted".length()) + "_liquid_boosted";
        } else {
            liquid = path.substring(0, path.length() - "_juice".length()) + "_liquid";
        }
        return withExistingParent(path, new ResourceLocation(Main.MOD_ID, "item/juice_bottle"))
                .texture("liquid", new ResourceLocation(Main.MOD_ID, "item/" + liquid));
    }
}
