package me.ez.jej.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.ez.jej.common.JuiceTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class JuiceTableRenderer implements BlockEntityRenderer<JuiceTableBlockEntity> {
    public static final ResourceLocation SCREW = ResourceLocation.fromNamespaceAndPath("jej", "block/juice_table_screw");
    public static final ResourceLocation RAM = ResourceLocation.fromNamespaceAndPath("jej", "block/juice_table_ram");
    //? if >=1.21.2 {
    /*private static final ModelResourceLocation SCREW_MODEL = new ModelResourceLocation(SCREW, "standalone");
    private static final ModelResourceLocation RAM_MODEL = new ModelResourceLocation(RAM, "standalone");
    *///?} else {
    private static final ModelResourceLocation SCREW_MODEL = ModelResourceLocation.standalone(SCREW);
    private static final ModelResourceLocation RAM_MODEL = ModelResourceLocation.standalone(RAM);
    //?}
    public JuiceTableRenderer(BlockEntityRendererProvider.Context context) {}

    @Override public void render(JuiceTableBlockEntity table, float partialTick, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        var state = table.getBlockState();
        float rotation = switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case EAST -> 90; case SOUTH -> 180; case WEST -> 270; default -> 0;
        };
        pose.pushPose();
        pose.translate(.5, 0, .5);
        pose.mulPose(Axis.YP.rotationDegrees(-rotation));
        pose.translate(-.5, 0, -.5);
        float time = table.getAnimationTime(partialTick);
        piece(table, SCREW_MODEL, JuiceTableAnimation.SCREW_PIVOT, JuiceTableAnimation.sample(JuiceTableAnimation.ROTATION, time),
                JuiceTableAnimation.sample(JuiceTableAnimation.SCREW_POSITION, time) / 16, pose, buffers, light, overlay);
        piece(table, RAM_MODEL, JuiceTableAnimation.RAM_PIVOT, 0, JuiceTableAnimation.sample(JuiceTableAnimation.RAM_POSITION, time) / 16,
                pose, buffers, light, overlay);
        pose.popPose();
    }
    private void piece(JuiceTableBlockEntity table, ModelResourceLocation model, float[] pivot, float rotation, float y,
                       PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        pose.pushPose();
        pose.translate(pivot[0], pivot[1] + y, pivot[2]);
        pose.mulPose(Axis.YP.rotationDegrees(-rotation));
        pose.translate(-pivot[0], -pivot[1], -pivot[2]);
        var minecraft = Minecraft.getInstance();
        minecraft.getBlockRenderer().getModelRenderer().renderModel(pose.last(), buffers.getBuffer(RenderType.cutout()),
                table.getBlockState(), minecraft.getModelManager().getModel(model), 1, 1, 1, light, overlay);
        pose.popPose();
    }
    @Override public boolean shouldRenderOffScreen(JuiceTableBlockEntity table) { return true; }
}
