//? if <1.21.2 {
package me.ez.jej.Datagen;

import me.ez.jej.Init;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

    public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    private void addJuiceRecipe(RecipeOutput recipeOutput,
                                Item fruit,
                                Item juice) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, juice)
                .define('M', Items.MILK_BUCKET)
                .define('G', Init.GLASS_BOTTLE.get())
                .define('F', fruit)
                .pattern(" F ")
                .pattern(" M ")
                .pattern(" G ")
                .unlockedBy(getHasName(fruit), has(fruit))
                .save(recipeOutput);
    }

    private void addBoostedJuiceRecipe(RecipeOutput recipeOutput,
                                       Item baseJuice,
                                       Item boostedJuice) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, boostedJuice)
                .define('J', baseJuice)
                .define('M', Items.MILK_BUCKET)
                .define('E', Init.JUICE_BOOSTER.get())
                .pattern(" M ")
                .pattern(" E ")
                .pattern(" J ")
                .unlockedBy(getHasName(baseJuice), has(baseJuice))
                .save(recipeOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        //Base recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Init.JUICE_TABLE_ITEM.get())
                .define('P', Items.OAK_PLANKS).define('I', Items.IRON_INGOT).define('G', Init.GLASS_BOTTLE.get())
                .pattern("IGI").pattern("PPP").pattern("P P")
                .unlockedBy(getHasName(Init.GLASS_BOTTLE.get()), has(Init.GLASS_BOTTLE.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Init.EMERALD_DUST.get(), 9)
                .requires(Items.EMERALD)
                .unlockedBy(getHasName(Items.EMERALD), has(Items.EMERALD))
                .save(recipeOutput);

        //Juice Booster: the catalyst that turns a base juice into a boosted one.
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Init.JUICE_BOOSTER.get())
                .define('E', Init.EMERALD_DUST.get())
                .define('G', Items.GOLD_INGOT)
                .pattern("EEE")
                .pattern("EGE")
                .pattern("EEE")
                .unlockedBy(getHasName(Init.EMERALD_DUST.get()), has(Init.EMERALD_DUST.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Init.GLASS_BOTTLE.get(), 3)
                .define('G', Items.GLASS)
                .define('N', Items.GOLD_NUGGET)
                .pattern(" N ")
                .pattern("G G")
                .pattern(" G ")
                .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.EMERALD)
                .define('M', Init.EMERALD_DUST.get())
                .pattern("MMM")
                .pattern("MMM")
                .pattern("MMM")
                .unlockedBy(getHasName(Init.EMERALD_DUST.get()), has(Init.EMERALD_DUST.get()))
                .save(recipeOutput);

        //Juice recipes
        addJuiceRecipe(recipeOutput, Items.APPLE, Init.APPLE_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.APPLE_JUICE.get(), Init.APPLE_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.BAKED_POTATO, Init.BAKEDPOTATO_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.BAKEDPOTATO_JUICE.get(), Init.BAKEDPOTATO_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.CARROT, Init.CARROT_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.CARROT_JUICE.get(), Init.CARROT_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.DRIED_KELP, Init.DRIEDKELP_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.DRIEDKELP_JUICE.get(), Init.DRIEDKELP_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Init.ICE_BERRY.get(), Init.ICEBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.ICEBERRY_JUICE.get(), Init.ICEBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.MELON_SLICE, Init.MELON_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.MELON_JUICE.get(), Init.MELON_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.PUMPKIN, Init.PUMPKIN_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.PUMPKIN_JUICE.get(), Init.PUMPKIN_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.SWEET_BERRIES, Init.SWEETBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.SWEETBERRY_JUICE.get(), Init.SWEETBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Init.WILD_BERRY.get(), Init.WILDBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.WILDBERRY_JUICE.get(), Init.WILDBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.CHORUS_FRUIT, Init.CHORUS_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.CHORUS_JUICE.get(), Init.CHORUS_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Init.GLOW_BERRY.get(), Init.GLOWBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.GLOWBERRY_JUICE.get(), Init.GLOWBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.CACTUS, Init.SPICY_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.SPICY_JUICE.get(), Init.SPICY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Init.SUN_BERRY.get(), Init.SUNBERRY_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.SUNBERRY_JUICE.get(), Init.SUNBERRY_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.BEETROOT, Init.BEETROOT_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.BEETROOT_JUICE.get(), Init.BEETROOT_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.NETHER_WART, Init.NETHERWART_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.NETHERWART_JUICE.get(), Init.NETHERWART_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.COCOA_BEANS, Init.COCOA_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.COCOA_JUICE.get(), Init.COCOA_JUICE_BOOSTED.get());

        addJuiceRecipe(recipeOutput, Items.IRON_INGOT, Init.GOLEM_JUICE.get());
        addBoostedJuiceRecipe(recipeOutput, Init.GOLEM_JUICE.get(), Init.GOLEM_JUICE_BOOSTED.get());

        //Special recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Init.GLISTERING_MELON_JUICE.get())
                .define('M', Items.MILK_BUCKET)
                .define('G', Init.GLASS_BOTTLE.get())
                .define('F', Items.GLISTERING_MELON_SLICE)
                .pattern(" F ")
                .pattern(" M ")
                .pattern(" G ")
                .unlockedBy(getHasName(Items.GLISTERING_MELON_SLICE), has(Items.GLISTERING_MELON_SLICE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Init.GOLDENAPPLE_JUICE.get())
                .define('M', Items.MILK_BUCKET)
                .define('G', Init.GLASS_BOTTLE.get())
                .define('F', Items.GOLDEN_APPLE)
                .pattern(" F ")
                .pattern(" M ")
                .pattern(" G ")
                .unlockedBy(getHasName(Items.GOLDEN_APPLE), has(Items.GOLDEN_APPLE))
                .save(recipeOutput);

        addBoostedJuiceRecipe(recipeOutput, Init.GOLDENAPPLE_JUICE.get(), Init.GOLDENAPPLE_JUICE_BOOSTED.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Init.GOLDENCARROT_JUICE.get())
                .define('M', Items.MILK_BUCKET)
                .define('G', Init.GLASS_BOTTLE.get())
                .define('F', Items.GOLDEN_CARROT)
                .pattern(" F ")
                .pattern(" M ")
                .pattern(" G ")
                .unlockedBy(getHasName(Items.GOLDEN_CARROT), has(Items.GOLDEN_CARROT))
                .save(recipeOutput);

        addBoostedJuiceRecipe(recipeOutput, Init.GOLDENCARROT_JUICE.get(), Init.GOLDENCARROT_JUICE_BOOSTED.get());

        addBoostedJuiceRecipe(recipeOutput, Init.GLISTERING_MELON_JUICE.get(), Init.GLISTERING_MELON_JUICE_BOOSTED.get());
    }
}

//?}
