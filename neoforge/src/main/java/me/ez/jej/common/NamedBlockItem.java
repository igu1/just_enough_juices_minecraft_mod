package me.ez.jej.common;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

/**
 * A BlockItem that displays its own item name ("Ice Berry") instead of the block's
 * name ("Ice Berry Bush"), which is the default for BlockItems.
 */
public class NamedBlockItem extends BlockItem {
    public NamedBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(this);
        return Component.translatable("item." + id.getNamespace() + "." + id.getPath());
    }
}
