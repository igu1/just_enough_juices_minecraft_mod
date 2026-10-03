package me.ez.jej.common;

import me.ez.jej.Init;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

// Generated from the imported Blockbench stages.
public final class BushShapes {
    private static final VoxelShape[][] SHAPES = {
        {Block.box(5.43961, 0.07437, 5.43961, 10.56039, 5.28, 10.56039), Block.box(2.72404, 0.15324, 2.72404, 13.27596, 10.88, 13.27596), Block.box(0.24124, 0.22535, 0.24124, 15.75876, 16, 15.75876), Block.box(0.24124, 0.22535, 0.24124, 16.24124, 16, 15.75876)},
        {Block.box(5.563031, 0.05087, 5.32969, 10.507267, 5.18844, 10.609695), Block.box(2.978359, 0.10482, 2.497547, 13.166479, 10.88, 13.377541), Block.box(0.615236, 0.15414, -0.091851, 15.59776, 16, 15.908145), Block.box(0.615236, 0.15414, -0.091851, 15.59776, 16, 15.908145)},
        {Block.box(5.359999, 0.76154, 5.577321, 10.640005, 3.764165, 10.422679), Block.box(2.559996, 0, 3.007802, 13.44, 10.88, 12.992198), Block.box(0.0, 0, 0.658538, 15.999996, 16, 15.341462), Block.box(0.0, 0, 0.658538, 15.999996, 16, 15.341462)},
    };
    public static VoxelShape get(Block block, int age) {
        int row = block == Init.WILD_BERRY_BUSH.get() ? 0 : block == Init.ICE_BERRY_BUSH.get() ? 1 : block == Init.SUN_BERRY_BUSH.get() ? 2 : -1;
        return row < 0 ? null : SHAPES[row][age];
    }
}
