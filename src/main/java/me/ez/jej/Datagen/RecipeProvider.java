package me.ez.jej.Datagen;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder {

    public RecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    private void addJuiceRecipe(Consumer<FinishedRecipe> recipeConsumer, 
                                net.minecraft.world.item.Item fruit, 
                                net.minecraft.world.item.Item juice) {
        ShapedRecipeBuilder.shaped(juice)
                .define('M', Items.MILK_BUCKET)
                .define('G', Init.GLASS_BOTTLE.get())
                .define('F', fruit)
                .pattern(" F ")
                .pattern(" M ")
                .pattern(" G ")
                .unlockedBy("has_" + fruit.getRegistryName().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                        .of(fruit).build()))
                .save(recipeConsumer);
    }

    private void addBoostedJuiceRecipe(Consumer<FinishedRecipe> recipeConsumer,
                                       net.minecraft.world.item.Item baseJuice,
                                       net.minecraft.world.item.Item boostedJuice) {
        ShapedRecipeBuilder.shaped(boostedJuice)
                .define('J', baseJuice)
                .define('M', Items.MILK_BUCKET)
                .define('E', Init.EMERALD_DUST.get())
                .pattern(" M ")
                .pattern(" E ")
                .pattern(" J ")
                .unlockedBy("has_" + baseJuice.getRegistryName().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                        .of(baseJuice).build()))
                .save(recipeConsumer);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer) {

        //Base recipes
        ShapelessRecipeBuilder.shapeless(Init.EMERALD_DUST.get(), 9)
                .requires(Items.EMERALD)
                .unlockedBy("has_emerald", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.EMERALD).build()))
                .save(recipeConsumer);

        ShapedRecipeBuilder.shaped(Init.GLASS_BOTTLE.get(), 3)
                .define('G', Items.GLASS)
                .define('N', Items.GOLD_NUGGET)
                .pattern(" N ")
                .pattern("G G")
                .pattern(" G ")
                .unlockedBy("has_glass", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.GLASS).build()))
                .save(recipeConsumer);

        ShapedRecipeBuilder.shaped(Items.EMERALD)
                .define('M', Init.EMERALD_DUST.get())
                .pattern("MMM")
                .pattern("MMM")
                .pattern("MMM")
                .unlockedBy("has_emerald_dust", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Init.EMERALD_DUST.get()).build()))
                .save(recipeConsumer);

        //Juice recipes
        addJuiceRecipe(recipeConsumer, Items.APPLE, Init.APPLE_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.APPLE_JUICE.get(), Init.APPLE_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.BAKED_POTATO, Init.BAKEDPOTATO_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.BAKEDPOTATO_JUICE.get(), Init.BAKEDPOTATO_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.CARROT, Init.CARROT_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.CARROT_JUICE.get(), Init.CARROT_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.DRIED_KELP, Init.DRIEDKELP_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.DRIEDKELP_JUICE.get(), Init.DRIEDKELP_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Init.ICE_BERRY.get(), Init.ICEBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.ICEBERRY_JUICE.get(), Init.ICEBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.MELON_SLICE, Init.MELON_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.MELON_JUICE.get(), Init.MELON_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.PUMPKIN, Init.PUMPKIN_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.PUMPKIN_JUICE.get(), Init.PUMPKIN_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.SWEET_BERRIES, Init.SWEETBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.SWEETBERRY_JUICE.get(), Init.SWEETBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Init.WILD_BERRY.get(), Init.WILDBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.WILDBERRY_JUICE.get(), Init.WILDBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.CHORUS_FRUIT, Init.CHORUS_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.CHORUS_JUICE.get(), Init.CHORUS_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Init.GLOW_BERRY.get(), Init.GLOWBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.GLOWBERRY_JUICE.get(), Init.GLOWBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.CACTUS, Init.SPICY_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.SPICY_JUICE.get(), Init.SPICY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Init.SUN_BERRY.get(), Init.SUNBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.SUNBERRY_JUICE.get(), Init.SUNBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.BEETROOT, Init.BEETROOT_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.BEETROOT_JUICE.get(), Init.BEETROOT_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.NETHER_WART, Init.NETHERWART_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.NETHERWART_JUICE.get(), Init.NETHERWART_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.COCOA_BEANS, Init.COCOA_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.COCOA_JUICE.get(), Init.COCOA_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeConsumer, Items.IRON_INGOT, Init.GOLEM_JUICE.get());
        addBoostedJuiceRecipe(recipeConsumer, Init.GOLEM_JUICE.get(), Init.GOLEM_JUICE_BOOSTED.get());

        //Special recipes
        ShapedRecipeBuilder.shaped(Init.GLISTERING_MELON_JUICE.get())
                .define('M', Items.MILK_BUCKET)
                .define('G', Init.GLASS_BOTTLE.get())
                .define('F', Items.GLISTERING_MELON_SLICE)
                .pattern(" F ")
                .pattern(" M ")
                .pattern(" G ")
                .unlockedBy("has_glistering_melon", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.GLISTERING_MELON_SLICE).build()))
                .save(recipeConsumer);

        ShapedRecipeBuilder.shaped(Init.GOLDENAPPLE_JUICE.get())
                .define('M', Items.MILK_BUCKET)
                .define('G', Init.GLASS_BOTTLE.get())
                .define('F', Items.GOLDEN_APPLE)
                .pattern(" F ")
                .pattern(" M ")
                .pattern(" G ")
                .unlockedBy("has_golden_apple", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.GOLDEN_APPLE).build()))
                .save(recipeConsumer);

        addBoostedJuiceRecipe(recipeConsumer, Init.GOLDENAPPLE_JUICE.get(), Init.GOLDENAPPLE_JUICE_BOOSTED.get());

        ShapedRecipeBuilder.shaped(Init.GOLDENCARROT_JUICE.get())
                .define('M', Items.MILK_BUCKET)
                .define('G', Init.GLASS_BOTTLE.get())
                .define('F', Items.GOLDEN_CARROT)
                .pattern(" F ")
                .pattern(" M ")
                .pattern(" G ")
                .unlockedBy("has_golden_carrot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.GOLDEN_CARROT).build()))
                .save(recipeConsumer);

        addBoostedJuiceRecipe(recipeConsumer, Init.GOLDENCARROT_JUICE.get(), Init.GOLDENCARROT_JUICE_BOOSTED.get());

        addBoostedJuiceRecipe(recipeConsumer, Init.GLISTERING_MELON_JUICE.get(), Init.GLISTERING_MELON_JUICE_BOOSTED.get());
    }
}