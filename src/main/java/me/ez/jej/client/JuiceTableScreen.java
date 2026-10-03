package me.ez.jej.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.ez.jej.common.JuiceTableMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class JuiceTableScreen extends AbstractContainerScreen<JuiceTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("jej", "textures/gui/juice_table.png");
    public JuiceTableScreen(JuiceTableMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 194;
        imageHeight = 190;
        inventoryLabelX = 17;
        inventoryLabelY = 96;
    }
    @Override protected void renderLabels(PoseStack pose, int mouseX, int mouseY) {
        // Keep the inventory label, but leave the citrus header free of title text.
        font.draw(pose, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 4210752);
    }
    @Override protected void renderBg(PoseStack pose, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(net.minecraft.client.renderer.GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(pose, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        blit(pose, leftPos + 121, topPos + 36, 0, 192, menu.getProgressWidth(), 16);
    }
    @Override public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) {
        renderBackground(pose);
        super.render(pose, mouseX, mouseY, partialTick);
        renderTooltip(pose, mouseX, mouseY);
        for (int i = 0; i < JuiceTableMenu.MACHINE_SLOTS.length; i++) {
            int[] slot = JuiceTableMenu.MACHINE_SLOTS[i];
            if (isHovering(slot[0], slot[1], 16, 16, mouseX, mouseY) && !menu.getSlot(i).hasItem()) {
                renderTooltip(pose, new TranslatableComponent("container.jej.juice_table.slot" + i), mouseX, mouseY);
            }
        }
    }
}
