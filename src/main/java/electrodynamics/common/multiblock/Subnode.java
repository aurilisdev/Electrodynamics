package electrodynamics.common.multiblock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;

public class Subnode {
    public BlockPos pos;
    public VoxelShape shape;

    public Subnode(BlockPos pos, VoxelShape shape) {
	this.pos = pos;
	this.shape = shape;
    }
}
