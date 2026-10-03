package me.ez.jej.common;

import me.ez.jej.Init;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JuiceTableBlockEntity extends BlockEntity implements WorldlyContainer, MenuProvider {
    public static final int DURATION = 100;
    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private int progress;
    private long clientSyncTime;
    private ItemStack workingResult = ItemStack.EMPTY;

    public final ContainerData data = new ContainerData() {
        public int get(int index) { return index == 0 ? progress : DURATION; }
        public void set(int index, int value) { if (index == 0) progress = value; }
        public int getCount() { return 2; }
    };

    public JuiceTableBlockEntity(BlockPos pos, BlockState state) {
        super(Init.JUICE_TABLE_ENTITY.get(), pos, state);
    }

    public static boolean isInput(int slot, ItemStack stack) {
        if (slot == 1) return stack.is(Items.MILK_BUCKET);
        if (slot == 2) return stack.is(Init.GLASS_BOTTLE.get()) || stack.is(Init.JUICE_BOOSTER.get());
        return slot == 0 && !stack.isEmpty() && !stack.is(Items.MILK_BUCKET) && !stack.is(Items.BUCKET)
                && !stack.is(Init.GLASS_BOTTLE.get()) && !stack.is(Init.EMERALD_DUST.get())
                && !stack.is(Init.JUICE_BOOSTER.get());
    }

    private ItemStack findResult() {
        // Match the mod's existing recipes, so datapack recipe changes are respected.
        boolean boosted = getItem(2).is(Init.JUICE_BOOSTER.get());
        List<ItemStack> grid = new ArrayList<>(Collections.nCopies(9, ItemStack.EMPTY));
        grid.set(1, getItem(boosted ? 1 : 0).copy());
        grid.set(4, getItem(boosted ? 2 : 1).copy());
        grid.set(7, getItem(boosted ? 0 : 2).copy());
        CraftingInput input = CraftingInput.of(3, 3, grid);
        //? if >=1.21.2 {
        /*return level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level)
                .filter(holder -> holder.value().getResultItem(level.registryAccess()).getItem() instanceof JuiceClass)
                .map(holder -> holder.value().assemble(input, level.registryAccess())).orElse(ItemStack.EMPTY);
        *///?} else {
        return level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level)
                .filter(holder -> holder.value().getResultItem(level.registryAccess()).getItem() instanceof JuiceClass)
                .map(holder -> holder.value().assemble(input, level.registryAccess())).orElse(ItemStack.EMPTY);
        //?}
    }

    private boolean canFit(int slot, ItemStack stack) {
        ItemStack present = getItem(slot);
        return present.isEmpty() || (ItemStack.isSameItemSameComponents(present, stack)
                && present.getCount() + stack.getCount() <= Math.min(getMaxStackSize(), present.getMaxStackSize()));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, JuiceTableBlockEntity table) {
        ItemStack result = table.findResult();
        if (result.isEmpty() || !table.canFit(3, result) || !table.canFit(4, new ItemStack(Items.BUCKET))) {
            if (table.progress != 0) { table.progress = 0; table.workingResult = ItemStack.EMPTY; table.setChanged(); level.sendBlockUpdated(pos, state, state, 2); }
            return;
        }
        if (!ItemStack.isSameItemSameComponents(result, table.workingResult)) {
            table.progress = 0;
            table.workingResult = result.copy();
        }
        if (++table.progress >= DURATION) {
            for (int i = 0; i < 3; i++) table.items.get(i).shrink(1);
            if (table.getItem(3).isEmpty()) table.items.set(3, result.copy()); else table.getItem(3).grow(result.getCount());
            if (table.getItem(4).isEmpty()) table.items.set(4, new ItemStack(Items.BUCKET)); else table.getItem(4).grow(1);
            table.progress = 0;
        }
        table.setChanged();
        if (table.progress % 4 == 0 || table.progress == 1) level.sendBlockUpdated(pos, state, state, 2);
    }

    public float getAnimationTime(float partialTick) {
        if (progress <= 0 || level == null) return 0;
        return Math.min(DURATION, progress + Math.max(0, level.getGameTime() - clientSyncTime) + partialTick) / 20F;
    }

    @Override public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Progress", progress);
        return tag;
    }

    @Override public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        progress = Math.max(0, Math.min(DURATION - 1, tag.getInt("Progress")));
        clientSyncTime = level == null ? 0 : level.getGameTime();
    }

    @Override public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("Progress", progress);
        if (!workingResult.isEmpty()) tag.put("WorkingResult", workingResult.save(registries));
    }

    @Override protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(5, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
        progress = Math.max(0, Math.min(DURATION - 1, tag.getInt("Progress")));
        workingResult = ItemStack.parseOptional(registries, tag.getCompound("WorkingResult"));
    }

    @Override public int getContainerSize() { return items.size(); }
    @Override public boolean isEmpty() { return items.stream().allMatch(ItemStack::isEmpty); }
    @Override public ItemStack getItem(int slot) { return items.get(slot); }
    @Override public ItemStack removeItem(int slot, int count) { ItemStack stack = ContainerHelper.removeItem(items, slot, count); setChanged(); return stack; }
    @Override public ItemStack removeItemNoUpdate(int slot) { return ContainerHelper.takeItem(items, slot); }
    @Override public void setItem(int slot, ItemStack stack) { items.set(slot, stack); stack.setCount(Math.min(stack.getCount(), Math.min(getMaxStackSize(), stack.getMaxStackSize()))); setChanged(); }
    @Override public boolean stillValid(Player player) { return level.getBlockEntity(worldPosition) == this && player.distanceToSqr(worldPosition.getX() + .5, worldPosition.getY() + .5, worldPosition.getZ() + .5) <= 64; }
    @Override public void clearContent() { items.clear(); setChanged(); }
    @Override public boolean canPlaceItem(int slot, ItemStack stack) { return isInput(slot, stack); }
    @Override public int[] getSlotsForFace(Direction side) { return side == Direction.DOWN ? new int[]{3, 4} : side == Direction.UP ? new int[]{0} : new int[]{1, 2}; }
    @Override public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) { return canPlaceItem(slot, stack); }
    @Override public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) { return slot >= 3; }
    @Override public Component getDisplayName() { return Component.translatable("container.jej.juice_table"); }
    @Override public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) { return new JuiceTableMenu(id, inventory, this, data); }
}
