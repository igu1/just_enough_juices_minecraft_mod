package me.ez.jej.client;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Opt-in real-client model/atlas test; inactive in normal play. */
@Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
public class ClientAssetSmokeTest {
    private static boolean completed;
    private static void check(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
    private static void model(ResourceLocation location) {
        checkModel(Minecraft.getInstance().getModelManager().getModel(location), location.toString());
    }
    private static void checkModel(net.minecraft.client.resources.model.BakedModel model, String location) {
        check(model != Minecraft.getInstance().getModelManager().getMissingModel(), "Missing baked model: " + location);
        var quads = model.getQuads(null, null, new java.util.Random(42));
        check(!quads.isEmpty(), "Empty baked model: " + location);
        for (var quad : quads) check(!quad.getSprite().getName().equals(MissingTextureAtlasSprite.getLocation()), "Missing model texture: " + location);
    }
    @SubscribeEvent public static void tick(TickEvent.ClientTickEvent event) {
        if (!Boolean.getBoolean("jej.assetSmokeTest") || completed || event.phase != TickEvent.Phase.END) return;
        var minecraft = Minecraft.getInstance();
        if (!(minecraft.screen instanceof TitleScreen) || minecraft.getOverlay() != null) return;
        completed = true;
        boolean success = false;
        String error = "";
        try {
            for (String part : new String[]{"screw", "ram"}) model(new ResourceLocation("jej", "block/juice_table_" + part));
            for (var part : net.minecraft.world.level.block.state.properties.BedPart.values()) {
                for (var facing : net.minecraft.core.Direction.Plane.HORIZONTAL) {
                    var state = Init.JUICE_TABLE.get().defaultBlockState().setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.BED_PART, part)
                            .setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING, facing);
                    checkModel(minecraft.getBlockRenderer().getBlockModel(state), state.toString());
                }
            }
            for (var bush : new me.ez.jej.common.ModBushBlock[]{Init.WILD_BERRY_BUSH.get(), Init.ICE_BERRY_BUSH.get(), Init.SUN_BERRY_BUSH.get()}) {
                for (int age = 0; age < 4; age++) {
                    var state = bush.defaultBlockState().setValue(me.ez.jej.common.ModBushBlock.AGE, age);
                    checkModel(minecraft.getBlockRenderer().getBlockModel(state), state.toString());
                }
            }
            for (var berry : new net.minecraft.world.item.Item[]{Init.WILD_BERRY.get(), Init.ICE_BERRY.get(), Init.SUN_BERRY.get()}) {
                checkModel(minecraft.getItemRenderer().getModel(new ItemStack(berry), null, null, 0), berry.getRegistryName().toString());
            }
            for (var item : Init.ITEMS.getEntries()) {
                if (item.getId().getPath().contains("_juice")) {
                    var bottle = minecraft.getItemRenderer().getModel(new ItemStack(item.get()), null, null, 0);
                    checkModel(bottle, item.getId().toString());
                }
            }
            for (var effect : Init.EFFECT.getEntries()) {
                var sprite = minecraft.getMobEffectTextures().get(effect.get());
                check(!sprite.getName().equals(MissingTextureAtlasSprite.getLocation()) && sprite.getWidth() == 18 && sprite.getHeight() == 18,
                        "Missing/invalid effect icon: " + effect.getId());
            }
            check(JuiceTableAnimation.sample(JuiceTableAnimation.ROTATION, 1.75F) == 540F, "Wrong imported crank keyframe");
            check(JuiceTableAnimation.sample(JuiceTableAnimation.RAM_POSITION, 1.75F) == -.6F, "Wrong imported ram keyframe");
            success = true;
            org.apache.logging.log4j.LogManager.getLogger().info("JEJ CLIENT ASSET SMOKE TEST PASSED: table animation, 12 bush stages, 3 berries, 40 bottles, 49 effect icons");
        } catch (Exception exception) {
            error = exception.toString();
            org.apache.logging.log4j.LogManager.getLogger().error("JEJ CLIENT ASSET SMOKE TEST FAILED", exception);
        } finally {
            try {
                var report = java.nio.file.Path.of(System.getProperty("jej.assetSmokeReport"));
                java.nio.file.Files.createDirectories(report.getParent());
                var json = new com.google.gson.JsonObject();
                json.addProperty("success", success);
                json.addProperty("error", error);
                java.nio.file.Files.writeString(report, json.toString());
            } catch (Exception exception) { org.apache.logging.log4j.LogManager.getLogger().error("Could not save asset smoke report", exception); }
            minecraft.stop();
        }
    }
}
