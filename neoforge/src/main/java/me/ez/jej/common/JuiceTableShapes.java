package me.ez.jej.common;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Model-aligned boxes in the north-facing model's 32 x 16 footprint. */
public final class JuiceTableShapes {
    private static final VoxelShape[][] SHAPES = new VoxelShape[2][4];

    static {
        VoxelShape table = Shapes.or(
                Block.box(0, 14, 0, 32, 16.12, 16),
                Block.box(2, 0, 2, 4, 14, 4),
                Block.box(2, 0, 12, 4, 14, 14),
                Block.box(28, 0, 2, 30, 14, 4),
                Block.box(28, 0, 12, 30, 12, 14),
                Block.box(1, 11.5, 1, 30, 14, 2),
                Block.box(2, 11.5, 14, 30, 14, 15),
                Block.box(1, 11.5, 2, 2, 14, 15),
                Block.box(30, 11.5, 1, 31, 14, 15),
                Block.box(3, 3, 3, 29, 4, 13),
                Block.box(3, 16, 2, 13, 17, 10),
                Block.box(3.5, 17, 5, 4.5, 26, 6.5),
                Block.box(11.5, 17, 5, 12.5, 26, 6.5),
                Block.box(3, 25.5, 4.9, 13, 26.5, 6.6),
                Block.box(7.65, 25.4, 5.7, 9.15, 27, 7.3),
                // Reserve the animated spindle/ram's travel, without filling the press frame.
                Block.box(8.15, 20.7, 6.25, 8.65, 28.8, 6.75),
                Block.box(5.3, 28.35, 6.12, 11.5, 29.12, 6.88),
                // Stepped approximation of the eight-sided barrel.
                Block.box(5.5, 17, 5.25, 11.5, 21.9, 7.75),
                Block.box(6.25, 17, 4.25, 10.75, 21.9, 8.75),
                Block.box(7.25, 17, 3.5, 9.75, 21.9, 9.5),
                Block.box(8.1, 17.1, 9.3, 8.9, 17.55, 11.3),
                Block.box(7.3, 16, 10.7, 10.35, 17.9, 13.1),
                Block.box(1.5, 16, 11, 6.5, 18.07, 14.5),
                Block.box(14, 16, 10, 21, 16.72, 14.5),
                Block.box(23.35, 16, 9.4, 29.9, 16.95, 14.3));
        for (int x : new int[]{20, 24, 28}) {
            table = Shapes.or(table,
                    Block.box(x - 1.1, 16, 3, x + 1.1, 19.3, 5.245),
                    Block.box(x - .55, 19.3, 3.55, x + .55, 20.85, 4.65));
        }
        for (BedPart part : BedPart.values()) {
            int index = part == BedPart.FOOT ? 0 : 1;
            double offset = index * 16;
            VoxelShape half = Shapes.empty();
            for (AABB box : table.toAabbs()) {
                double minX = Math.max(box.minX * 16, offset) - offset;
                double maxX = Math.min(box.maxX * 16, offset + 16) - offset;
                if (minX < maxX) {
                    half = Shapes.or(half, Block.box(minX, box.minY * 16, box.minZ * 16,
                            maxX, box.maxY * 16, box.maxZ * 16));
                }
            }
            SHAPES[index][0] = half.optimize();
            for (int turn = 1; turn < 4; turn++) {
                VoxelShape rotated = Shapes.empty();
                for (AABB box : SHAPES[index][turn - 1].toAabbs()) {
                    rotated = Shapes.or(rotated, Shapes.box(1 - box.maxZ, box.minY, box.minX,
                            1 - box.minZ, box.maxY, box.maxX));
                }
                SHAPES[index][turn] = rotated.optimize();
            }
        }
    }

    private JuiceTableShapes() {}

    public static VoxelShape get(BedPart part, Direction facing) {
        int turn = switch (facing) {
            case EAST -> 1;
            case SOUTH -> 2;
            case WEST -> 3;
            default -> 0;
        };
        return SHAPES[part == BedPart.FOOT ? 0 : 1][turn];
    }
}
