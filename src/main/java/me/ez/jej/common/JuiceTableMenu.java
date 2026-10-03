package me.ez.jej.common;

import me.ez.jej.Init;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class JuiceTableMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;
    // Slot coordinates also drive the generated GUI artwork.
    public static final int[][] MACHINE_SLOTS = {{26, 35}, {62, 35}, {98, 35}, {152, 35}, {152, 65}};
    public JuiceTableMenu(int id, Inventory inventory, FriendlyByteBuf buffer) {
        this(id, inventory, clientContainer(inventory, buffer), new SimpleContainerData(2));
    }
    private static Container clientContainer(Inventory inventory, FriendlyByteBuf buffer) {
        var entity = inventory.player.level.getBlockEntity(buffer.readBlockPos());
        return entity instanceof JuiceTableBlockEntity table ? table : new SimpleContainer(5);
    }
    public JuiceTableMenu(int id, Inventory inventory, Container container, ContainerData data) {
        super(Init.JUICE_TABLE_MENU.get(), id);
        checkContainerSize(container, 5);
        checkContainerDataCount(data, 2);
        this.container = container;
        this.data = data;
        container.startOpen(inventory.player);
        for (int i = 0; i < 5; i++) {
            final int slot = i;
            addSlot(new Slot(container, i, MACHINE_SLOTS[i][0], MACHINE_SLOTS[i][1]) {
                @Override public boolean mayPlace(ItemStack stack) { return JuiceTableBlockEntity.isInput(slot, stack); }
            });
        }
        for (int row = 0; row < 3; row++) for (int col = 0; col < 9; col++) addSlot(new Slot(inventory, col + row * 9 + 9, 17 + col * 18, 108 + row * 18));
        for (int col = 0; col < 9; col++) addSlot(new Slot(inventory, col, 17 + col * 18, 166));
        addDataSlots(data);
    }
    public int getProgressWidth() { return data.get(1) <= 0 ? 0 : data.get(0) * 24 / data.get(1); }
    @Override public boolean stillValid(Player player) { return container.stillValid(player); }
    @Override public void removed(Player player) { super.removed(player); container.stopOpen(player); }
    @Override public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        if (index < 5) {
            if (!moveItemStackTo(stack, 5, slots.size(), true)) return ItemStack.EMPTY;
        } else {
            int target = JuiceTableBlockEntity.isInput(1, stack) ? 1 : JuiceTableBlockEntity.isInput(2, stack) ? 2 : 0;
            if (!JuiceTableBlockEntity.isInput(target, stack) || !moveItemStackTo(stack, target, target + 1, false)) {
                if (index < 32 ? !moveItemStackTo(stack, 32, 41, false) : !moveItemStackTo(stack, 5, 32, false)) return ItemStack.EMPTY;
            }
        }
        if (stack.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        slot.onTake(player, stack);
        return original;
    }
}
