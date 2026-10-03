package me.ez.jej.Datagen.LootTable;

import me.ez.jej.Init;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockLootTable extends BlockLootSubProvider {

    public BlockLootTable(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(Init.JUICE_TABLE.get(), LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Init.JUICE_TABLE.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.BED_PART,
                                BedPart.FOOT)))
                .when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(Init.JUICE_TABLE.get()))));
        BushLootTable(Init.ICE_BERRY_BUSH.get(), Init.ICE_BERRY.get());
        BushLootTable(Init.WILD_BERRY_BUSH.get(), Init.WILD_BERRY.get());
        BushLootTable(Init.SUN_BERRY_BUSH.get(), Init.SUN_BERRY.get());
        BushLootTable(Init.GLOW_BERRY_BUSH.get(), Init.GLOW_BERRY.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return Init.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get()).toList();
    }

    private void BushLootTable(BushBlock bushBlock, ItemLike itemLike) {
        Holder<Enchantment> fortune = this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
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
                    .apply(ApplyBonusCount.addUniformBonusCount(fortune)))

            .withPool(LootPool.lootPool()
                    .when(LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(bushBlock)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(BlockStateProperties.AGE_3, 3)))
                    .add(LootItem.lootTableItem(itemLike))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                    .apply(ApplyBonusCount.addUniformBonusCount(fortune))))));
    }
}
