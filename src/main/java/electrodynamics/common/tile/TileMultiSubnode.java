package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class TileMultiSubnode extends GenericTileBase {
    public BlockPos node;
    public VoxelShape shapeCache;

    public TileMultiSubnode() {
	super(DeferredRegisters.TILE_MULTI.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putInt("nodeX", node.getX());
	compound.putInt("nodeY", node.getY());
	compound.putInt("nodeZ", node.getZ());
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	node = new BlockPos(compound.getInt("nodeX"), compound.getInt("nodeY"), compound.getInt("nodeZ"));
	sendUpdatePacket();
    }

    @Override
    public void handleUpdatePacket(CompoundNBT tag) {
	read(getBlockState(), tag);
    }

    @Override
    public CompoundNBT createUpdateTag() {
	CompoundNBT nbt = super.createUpdateTag();
	write(nbt);
	return super.createUpdateTag();
    }

    public VoxelShape getShape() {
	if (shapeCache != null) {
	    return shapeCache;
	}
	if (node != null) {
	    TileEntity tile = world.getTileEntity(node);
	    if (tile instanceof IMultiblockTileNode) {
		IMultiblockTileNode node = (IMultiblockTileNode) tile;
		BlockPos tp = tile.getPos();
		BlockPos offset = new BlockPos(pos.getX() - tp.getX(), pos.getY() - tp.getY(), pos.getZ() - tp.getZ());
		for (Subnode sub : node.getSubNodes()) {
		    if (offset.equals(sub.pos)) {
			shapeCache = sub.shape;
			return shapeCache;
		    }
		}
	    }
	}
	return VoxelShapes.fullCube();
    }

}
