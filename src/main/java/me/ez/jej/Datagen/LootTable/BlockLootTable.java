package me.ez.jej.Datagen.LootTable;

import me.ez.jej.Init;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class BlockLootTable extends BlockLoot {

    @Override
    protected void addTables() {
        BushLootTable(Init.ICE_BERRY_BUSH.get(), Init.ICE_BERRY.get());
        BushLootTable(Init.WILD_BERRY_BUSH.get(), Init.WILD_BERRY.get());
        BushLootTable(Init.SUN_BERRY_BUSH.get(), Init.SUN_BERRY.get());
        BushLootTable(Init.GLOW_BERRY_BUSH.get(), Init.GLOW_BERRY.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return Init.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    private void BushLootTable(BushBlock bushBlock, ItemLike itemLike){
        this.add(bushBlock, (block ->
                applyExplosionDecay(block,
                        LootTable.lootTable()

            .withPool(LootPool.lootPool()
                    .when(LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(bushBlock)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(BlockStateProperties.AGE_3, 3)))
                    .add(LootItem.lootTableItem(itemLike))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                    .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))

            .withPool(LootPool.lootPool()
                    .when(LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(bushBlock)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(BlockStateProperties.AGE_3, 3)))
                    .add(LootItem.lootTableItem(itemLike))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                    .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))));
    }
}
