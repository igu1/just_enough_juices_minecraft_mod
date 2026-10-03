package me.ez.jej.common;

import com.mojang.serialization.MapCodec;
import me.ez.jej.Init;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class JuiceTableBlock extends BaseEntityBlock {
    public JuiceTableBlock() {
        super(Properties.of().strength(2.5F).sound(SoundType.WOOD).noOcclusion());
        registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .setValue(BlockStateProperties.BED_PART, BedPart.FOOT));
    }

    @Override protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(props -> new JuiceTableBlock());
    }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
        builder.add(BlockStateProperties.BED_PART);
    }
    @Override public BlockState getStateForPlacement(BlockPlaceContext context) {
        var facing = context.getHorizontalDirection().getOpposite();
        BlockPos other = context.getClickedPos().relative(facing.getClockWise());
        return context.getLevel().getWorldBorder().isWithinBounds(other)
                && context.getLevel().getBlockState(other).canBeReplaced(context)
                ? defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing) : null;
    }
    public static BlockPos otherPos(BlockPos pos, BlockState state) {
        var direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise();
        return pos.relative(state.getValue(BlockStateProperties.BED_PART) == BedPart.FOOT ? direction : direction.getOpposite());
    }
    public static JuiceTableBlockEntity getTable(BlockGetter level, BlockPos pos, BlockState state) {
        BlockPos master = state.getValue(BlockStateProperties.BED_PART) == BedPart.FOOT ? pos : otherPos(pos, state);
        return level.getBlockEntity(master) instanceof JuiceTableBlockEntity table ? table : null;
    }
    @Override public void setPlacedBy(Level level, BlockPos pos, BlockState state, net.minecraft.world.entity.LivingEntity placer, net.minecraft.world.item.ItemStack stack) {
        if (!level.isClientSide()) level.setBlock(otherPos(pos, state), state.setValue(BlockStateProperties.BED_PART, BedPart.HEAD), 3);
    }
    @Override public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }
    @Override public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }
    @Override public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }
    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return JuiceTableShapes.get(state.getValue(BlockStateProperties.BED_PART), state.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }
    @Override public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }
    @Override public PushReaction getPistonPushReaction(BlockState state) { return PushReaction.BLOCK; }
    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(BlockStateProperties.BED_PART) == BedPart.FOOT ? new JuiceTableBlockEntity(pos, state) : null;
    }
    @Override public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() || state.getValue(BlockStateProperties.BED_PART) != BedPart.FOOT ? null : createTickerHelper(type, Init.JUICE_TABLE_ENTITY.get(), JuiceTableBlockEntity::tick);
    }
    @Override protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        JuiceTableBlockEntity table = getTable(level, pos, state);
        if (!level.isClientSide() && table != null) {
            ((ServerPlayer) player).openMenu(table, buf -> buf.writeBlockPos(table.getBlockPos()));
        }
        //? if >=1.21.2 {
        /*return InteractionResult.SUCCESS;
        *///?} else {
        return InteractionResult.sidedSuccess(level.isClientSide());
        //?}
    }
    @Override public void onRemove(BlockState state, Level level, BlockPos pos, BlockState next, boolean moving) {
        if (!state.is(next.getBlock())) {
            if (level.getBlockEntity(pos) instanceof JuiceTableBlockEntity table) {
                Containers.dropContents(level, pos, table);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, next, moving);
            if (!level.isClientSide()) {
                BlockPos other = otherPos(pos, state);
                BlockState partner = level.getBlockState(other);
                if (partner.is(this) && partner.getValue(BlockStateProperties.BED_PART) != state.getValue(BlockStateProperties.BED_PART)
                        && partner.getValue(BlockStateProperties.HORIZONTAL_FACING) == state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                    // Only the inventory-bearing foot has block loot. Breaking the head
                    // destroys the foot with loot; breaking the foot removes an empty head.
                    level.destroyBlock(other, true);
                }
            }
        }
    }
    @Override public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            BlockPos master = state.getValue(BlockStateProperties.BED_PART) == BedPart.FOOT ? pos : otherPos(pos, state);
            if (level.getBlockState(master).is(this)) level.setBlock(master, Blocks.AIR.defaultBlockState(), 35);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
    @Override public boolean hasAnalogOutputSignal(BlockState state) { return true; }
    @Override public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        JuiceTableBlockEntity table = getTable(level, pos, state);
        return table == null ? 0 : net.minecraft.world.inventory.AbstractContainerMenu.getRedstoneSignalFromContainer(table);
    }
}
