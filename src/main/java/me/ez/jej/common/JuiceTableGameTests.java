package me.ez.jej.common;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.items.CapabilityItemHandler;

@GameTestHolder(Main.MOD_ID)
@PrefixGameTestTemplate(false)
public class JuiceTableGameTests {
    private static void check(GameTestHelper helper, boolean condition, String message) {
        if (!condition) helper.fail(message);
    }
    private static JuiceTableBlockEntity table(GameTestHelper helper) {
        helper.setBlock(new BlockPos(1, 1, 1), Init.JUICE_TABLE.get());
        Init.JUICE_TABLE.get().setPlacedBy(helper.getLevel(), helper.absolutePos(new BlockPos(1, 1, 1)),
                Init.JUICE_TABLE.get().defaultBlockState(), null, ItemStack.EMPTY);
        return (JuiceTableBlockEntity) helper.getBlockEntity(new BlockPos(1, 1, 1));
    }
    private static void tick(GameTestHelper helper, JuiceTableBlockEntity table, int count) {
        for (int i = 0; i < count; i++) JuiceTableBlockEntity.tick(helper.getLevel(), table.getBlockPos(), table.getBlockState(), table);
    }
    private static void fillApple(JuiceTableBlockEntity table) {
        table.setItem(0, new ItemStack(Items.APPLE));
        table.setItem(1, new ItemStack(Items.MILK_BUCKET));
        table.setItem(2, new ItemStack(Init.GLASS_BOTTLE.get()));
    }
    private static boolean shapeContains(net.minecraft.world.phys.shapes.VoxelShape shape, Direction facing,
                                         double x, double y, double z) {
        int turns = switch (facing) {
            case EAST -> 1;
            case SOUTH -> 2;
            case WEST -> 3;
            default -> 0;
        };
        for (int i = 0; i < turns; i++) {
            double oldX = x;
            x = 16 - z;
            z = oldX;
        }
        final double px = x / 16, py = y / 16, pz = z / 16;
        return shape.toAabbs().stream().anyMatch(box -> box.contains(px, py, pz));
    }
    @GameTest(template = "empty", timeoutTicks = 20)
    public static void modelAlignedShapes(GameTestHelper helper) {
        var block = Init.JUICE_TABLE.get();
        BlockPos pos = helper.absolutePos(new BlockPos(1, 1, 1));
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (var part : net.minecraft.world.level.block.state.properties.BedPart.values()) {
                var state = block.defaultBlockState()
                        .setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING, facing)
                        .setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.BED_PART, part);
                var shape = block.getShape(state, helper.getLevel(), pos, net.minecraft.world.phys.shapes.CollisionContext.empty());
                boolean foot = part == net.minecraft.world.level.block.state.properties.BedPart.FOOT;
                check(helper, shape == block.getCollisionShape(state, helper.getLevel(), pos, net.minecraft.world.phys.shapes.CollisionContext.empty()), "Collision and outline differ");
                check(helper, shapeContains(shape, facing, 8, 15, 8), "Missing countertop for " + facing + " " + part);
                check(helper, shapeContains(shape, facing, foot ? 3 : 13, 2, 3), "Missing table leg");
                check(helper, !shapeContains(shape, facing, 8, 8, 8), "Empty space below table is solid");
                check(helper, !shapeContains(shape, facing, 1, 24, 14), "Empty space above table is solid");
                check(helper, shapeContains(shape, facing, foot ? 4 : 8, foot ? 24 : 18, foot ? 5.5 : 4), "Missing press or juice bottle");
                var bounds = shape.bounds();
                check(helper, bounds.minX >= 0 && bounds.maxX <= 1 && bounds.minZ >= 0 && bounds.maxZ <= 1, "Shape crosses partner seam");
                check(helper, Math.abs(bounds.maxY * 16 - (foot ? 29.12 : 20.85)) < 0.0001, "Wrong half height");
                check(helper, shape == block.getShape(state, helper.getLevel(), pos, net.minecraft.world.phys.shapes.CollisionContext.empty()), "Shape is not cached");
            }
        }
        helper.succeed();
    }
    @GameTest(template = "empty", timeoutTicks = 20)
    public static void allJuiceRecipes(GameTestHelper helper) {
        JuiceTableBlockEntity table = table(helper);
        int count = 0;
        for (var recipe : helper.getLevel().getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)) {
            if (!(recipe.getResultItem().getItem() instanceof JuiceClass)) continue;
            table.clearContent();
            for (var ingredient : recipe.getIngredients()) {
                if (ingredient.isEmpty()) continue;
                ItemStack stack = ingredient.getItems()[0].copy();
                int slot = stack.is(Items.MILK_BUCKET) ? 1 : stack.is(Init.GLASS_BOTTLE.get()) || stack.is(Init.JUICE_BOOSTER.get()) ? 2 : 0;
                table.setItem(slot, stack);
            }
            tick(helper, table, JuiceTableBlockEntity.DURATION);
            check(helper, ItemStack.isSameItemSameTags(table.getItem(3), recipe.getResultItem()), "Wrong output for " + recipe.getId());
            check(helper, table.getItem(4).is(Items.BUCKET) && table.getItem(4).getCount() == 1, "Milk bucket not returned");
            check(helper, table.getItem(0).isEmpty() && table.getItem(1).isEmpty() && table.getItem(2).isEmpty(), "Inputs not consumed once");
            count++;
        }
        check(helper, count == 40, "Expected 40 juice recipes, found " + count);
        helper.succeed();
    }
    @GameTest(template = "empty", timeoutTicks = 20)
    public static void savesInventoryAndProgress(GameTestHelper helper) {
        JuiceTableBlockEntity table = table(helper);
        fillApple(table);
        tick(helper, table, 45);
        var saved = table.saveWithoutMetadata();
        table.clearContent();
        table.load(saved);
        check(helper, table.data.get(0) == 45, "Progress not restored");
        tick(helper, table, 55);
        check(helper, table.getItem(3).is(Init.APPLE_JUICE.get()), "Recipe did not resume after load");
        check(helper, table.getItem(4).is(Items.BUCKET), "Bucket missing after load");
        helper.succeed();
    }
    @GameTest(template = "empty", timeoutTicks = 20)
    public static void blockedOutputDoesNotConsumeInputs(GameTestHelper helper) {
        JuiceTableBlockEntity table = table(helper);
        fillApple(table);
        table.setItem(3, new ItemStack(Init.APPLE_JUICE.get()));
        tick(helper, table, 120);
        check(helper, table.getItem(0).is(Items.APPLE) && table.getItem(1).is(Items.MILK_BUCKET), "Full output consumed ingredients");
        table.setItem(3, ItemStack.EMPTY);
        table.setItem(4, new ItemStack(Items.BUCKET, 16));
        tick(helper, table, 120);
        check(helper, table.getItem(0).is(Items.APPLE), "Full bucket output consumed ingredients");
        table.setItem(4, ItemStack.EMPTY);
        table.setItem(2, new ItemStack(Items.DIRT));
        tick(helper, table, 120);
        check(helper, table.getItem(3).isEmpty(), "Invalid input made juice");
        helper.succeed();
    }
    @GameTest(template = "empty", timeoutTicks = 20)
    public static void hopperAccessAndCapabilityLifecycle(GameTestHelper helper) {
        JuiceTableBlockEntity table = table(helper);
        var top = table.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).orElseThrow(IllegalStateException::new);
        var side = table.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.NORTH).orElseThrow(IllegalStateException::new);
        var bottom = table.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).orElseThrow(IllegalStateException::new);
        check(helper, top.insertItem(0, new ItemStack(Items.APPLE), false).isEmpty(), "Top insertion failed");
        check(helper, side.insertItem(0, new ItemStack(Items.MILK_BUCKET), false).isEmpty(), "Side milk insertion failed");
        check(helper, side.insertItem(1, new ItemStack(Init.GLASS_BOTTLE.get()), false).isEmpty(), "Side bottle insertion failed");
        check(helper, !bottom.insertItem(0, new ItemStack(Items.DIRT), false).isEmpty(), "Output allowed insertion");
        tick(helper, table, 100);
        check(helper, bottom.extractItem(0, 1, false).is(Init.APPLE_JUICE.get()), "Bottom extraction failed");
        var capability = table.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP);
        table.invalidateCaps();
        check(helper, !capability.isPresent(), "Removed entity retained capability");
        table.reviveCaps();
        check(helper, table.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).isPresent(), "Revived entity missing capability");
        helper.succeed();
    }
    @GameTest(template = "empty", timeoutTicks = 20)
    public static void menuShiftClickAndSlotRestrictions(GameTestHelper helper) {
        JuiceTableBlockEntity table = table(helper);
        var player = net.minecraftforge.common.util.FakePlayerFactory.getMinecraft(helper.getLevel());
        var inventory = player.getInventory();
        inventory.clearContent();
        inventory.setItem(9, new ItemStack(Items.APPLE));
        inventory.setItem(10, new ItemStack(Items.MILK_BUCKET));
        inventory.setItem(11, new ItemStack(Init.GLASS_BOTTLE.get()));
        JuiceTableMenu menu = new JuiceTableMenu(0, inventory, table, table.data);
        check(helper, menu.slots.size() == 41, "Menu has wrong slot count");
        check(helper, !menu.getSlot(3).mayPlace(new ItemStack(Items.APPLE)), "Juice output accepts insertion");
        check(helper, !menu.getSlot(4).mayPlace(new ItemStack(Items.BUCKET)), "Bucket output accepts insertion");
        check(helper, !menu.getSlot(1).mayPlace(new ItemStack(Items.APPLE)), "Milk slot accepts fruit");
        menu.quickMoveStack(player, 5);
        menu.quickMoveStack(player, 6);
        menu.quickMoveStack(player, 7);
        tick(helper, table, 100);
        check(helper, table.getItem(3).is(Init.APPLE_JUICE.get()), "Shift-clicked inputs did not craft");
        menu.quickMoveStack(player, 3);
        menu.quickMoveStack(player, 4);
        check(helper, inventory.contains(new ItemStack(Init.APPLE_JUICE.get())), "Shift-click juice did not reach inventory");
        check(helper, inventory.contains(new ItemStack(Items.BUCKET)), "Shift-click bucket did not reach inventory");
        menu.removed(player);
        helper.succeed();
    }
    @GameTest(template = "empty", timeoutTicks = 20)
    public static void twoBlockPlacementAndBreaking(GameTestHelper helper) {
        BlockPos master = helper.absolutePos(new BlockPos(1, 1, 1));
        var level = helper.getLevel();
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (boolean breakHead : new boolean[]{false, true}) {
            var state = Init.JUICE_TABLE.get().defaultBlockState().setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING, facing);
            level.setBlock(master, state, 3);
            Init.JUICE_TABLE.get().setPlacedBy(level, master, state, null, ItemStack.EMPTY);
            BlockPos other = JuiceTableBlock.otherPos(master, state);
            check(helper, level.getBlockState(other).is(Init.JUICE_TABLE.get()), "Second block missing for " + facing);
            var head = level.getBlockState(other);
            check(helper, head.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.BED_PART) == net.minecraft.world.level.block.state.properties.BedPart.HEAD, "Wrong partner part");
            JuiceTableBlockEntity table = (JuiceTableBlockEntity) level.getBlockEntity(master);
            check(helper, JuiceTableBlock.getTable(level, other, head) == table, "Two halves do not share inventory");
            check(helper, level.getBlockEntity(other) == null, "Partner created duplicate inventory");
            table.setItem(0, new ItemStack(Items.APPLE, 3));
            level.destroyBlock(breakHead ? other : master, true);
            check(helper, !level.getBlockState(master).is(Init.JUICE_TABLE.get()) && !level.getBlockState(other).is(Init.JUICE_TABLE.get()), "Breaking partner left an orphan");
            var area = new net.minecraft.world.phys.AABB(master).minmax(new net.minecraft.world.phys.AABB(other)).inflate(1);
            var drops = level.getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, area);
            int tables = drops.stream().filter(e -> e.getItem().is(Init.JUICE_TABLE_ITEM.get())).mapToInt(e -> e.getItem().getCount()).sum();
            int apples = drops.stream().filter(e -> e.getItem().is(Items.APPLE)).mapToInt(e -> e.getItem().getCount()).sum();
            check(helper, tables == 1 && apples == 3, "Incorrect two-block drops: " + tables + " tables, " + apples + " apples");
            drops.forEach(net.minecraft.world.entity.Entity::discard);
            }
        }
        helper.succeed();
    }
    @GameTest(template = "empty", timeoutTicks = 20)
    public static void placementRejectsBlockedPartner(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos target = helper.absolutePos(new BlockPos(1, 1, 1));
        level.setBlock(target.below(), net.minecraft.world.level.block.Blocks.STONE.defaultBlockState(), 3);
        var player = net.minecraftforge.common.util.FakePlayerFactory.getMinecraft(level);
        var context = new net.minecraft.world.item.context.BlockPlaceContext(level, player, net.minecraft.world.InteractionHand.MAIN_HAND,
                new ItemStack(Init.JUICE_TABLE_ITEM.get()), new net.minecraft.world.phys.BlockHitResult(net.minecraft.world.phys.Vec3.atCenterOf(target.below()), Direction.UP, target.below(), false));
        var state = Init.JUICE_TABLE.get().getStateForPlacement(context);
        check(helper, state != null, "Placement rejected empty space");
        level.setBlock(JuiceTableBlock.otherPos(target, state), net.minecraft.world.level.block.Blocks.STONE.defaultBlockState(), 3);
        check(helper, Init.JUICE_TABLE.get().getStateForPlacement(context) == null, "Placement would overwrite occupied second block");
        helper.succeed();
    }
    @GameTest(template = "empty", timeoutTicks = 20)
    public static void creativeBreakDoesNotDropTable(GameTestHelper helper) {
        JuiceTableBlockEntity table = table(helper);
        table.setItem(0, new ItemStack(Items.APPLE, 3));
        var level = helper.getLevel();
        BlockPos master = table.getBlockPos();
        BlockPos other = JuiceTableBlock.otherPos(master, table.getBlockState());
        var player = net.minecraftforge.common.util.FakePlayerFactory.getMinecraft(level);
        player.gameMode.changeGameModeForPlayer(net.minecraft.world.level.GameType.CREATIVE);
        try {
            Init.JUICE_TABLE.get().playerWillDestroy(level, other, level.getBlockState(other), player);
            check(helper, !level.getBlockState(master).is(Init.JUICE_TABLE.get()) && !level.getBlockState(other).is(Init.JUICE_TABLE.get()), "Creative break left an orphan");
            var drops = level.getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class,
                    new net.minecraft.world.phys.AABB(master).minmax(new net.minecraft.world.phys.AABB(other)).inflate(1));
            check(helper, drops.stream().noneMatch(e -> e.getItem().is(Init.JUICE_TABLE_ITEM.get())), "Creative break dropped a table");
            check(helper, drops.stream().filter(e -> e.getItem().is(Items.APPLE)).mapToInt(e -> e.getItem().getCount()).sum() == 3, "Creative break lost stored items");
            helper.succeed();
        } finally {
            player.gameMode.changeGameModeForPlayer(net.minecraft.world.level.GameType.SURVIVAL);
        }
    }
}
