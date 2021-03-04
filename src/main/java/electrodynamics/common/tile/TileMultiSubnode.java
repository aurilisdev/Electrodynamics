package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.scheduler.Scheduler;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class TileMultiSubnode extends GenericTileBase implements ITickableTileBase {
    public BlockPos nodePos;
    public VoxelShape shapeCache;

    public TileMultiSubnode() {
	super(DeferredRegisters.TILE_MULTI.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	if (nodePos != null) {
	    compound.putInt("nodeX", nodePos.getX());
	    compound.putInt("nodeY", nodePos.getY());
	    compound.putInt("nodeZ", nodePos.getZ());
	}
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	nodePos = new BlockPos(compound.getInt("nodeX"), compound.getInt("nodeY"), compound.getInt("nodeZ"));
	Scheduler.schedule(20, () -> sendCustomPacket());
    }

    @Override
    public void readCustomPacket(CompoundNBT tag) {
	read(getBlockState(), tag);
    }

    @Override
    public CompoundNBT writeCustomPacket() {
	CompoundNBT nbt = super.writeCustomPacket();
	write(nbt);
	return nbt;
    }

    public VoxelShape getShape() {
	if (shapeCache != null) {
	    return shapeCache;
	}
	if (nodePos != null) {
	    TileEntity tile = world.getTileEntity(nodePos);
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
