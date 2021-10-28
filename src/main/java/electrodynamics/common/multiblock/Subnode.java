package electrodynamics.common.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Subnode {
    public BlockPos pos;
    public VoxelShape shape;

    public Subnode(BlockPos pos, VoxelShape shape) {
	this.pos = pos;
	this.shape = shape;
    }
}
