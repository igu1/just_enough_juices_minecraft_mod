package me.ez.jej.client;

import me.ez.jej.common.JuiceTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class JuiceTableScreen extends AbstractContainerScreen<JuiceTableMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("jej", "textures/gui/juice_table.png");
    public JuiceTableScreen(JuiceTableMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 194;
        imageHeight = 190;
        inventoryLabelX = 17;
        inventoryLabelY = 96;
    }
    @Override protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Keep the inventory label, but leave the citrus header free of title text.
        guiGraphics.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 4210752, false);
    }
    @Override protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        //? if >=1.21.2 {
        /*guiGraphics.blit(net.minecraft.client.renderer.RenderType::guiTextured, TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        guiGraphics.blit(net.minecraft.client.renderer.RenderType::guiTextured, TEXTURE, leftPos + 121, topPos + 36, 0, 192, menu.getProgressWidth(), 16, 256, 256);
        *///?} else {
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        guiGraphics.blit(TEXTURE, leftPos + 121, topPos + 36, 0, 192, menu.getProgressWidth(), 16);
        //?}
    }
    @Override public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        for (int i = 0; i < JuiceTableMenu.MACHINE_SLOTS.length; i++) {
            int[] slot = JuiceTableMenu.MACHINE_SLOTS[i];
            if (isHovering(slot[0], slot[1], 16, 16, mouseX, mouseY) && !menu.getSlot(i).hasItem()) {
                guiGraphics.renderTooltip(font, Component.translatable("container.jej.juice_table.slot" + i), mouseX, mouseY);
            }
        }
    }
}
