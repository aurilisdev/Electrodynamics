package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.math.Location;
import electrodynamics.api.scheduler.Scheduler;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class TileMultiSubnode extends GenericTile {
    public Location nodePos;
    public VoxelShape shapeCache;

    public TileMultiSubnode() {
	super(DeferredRegisters.TILE_MULTI.get());
	addComponent(new ComponentPacketHandler().addCustomPacketConsumer(this::readCustomPacket)
		.setCustomPacketSupplier(this::writeCustomPacket));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	if (nodePos != null) {
	    nodePos.writeToNBT(compound, "node");
	}
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	nodePos = Location.readFromNBT(compound, "node");
	Scheduler.schedule(20,
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler)::sendCustomPacket);
    }

    public void readCustomPacket(CompoundNBT tag) {
	read(getBlockState(), tag);
    }

    public CompoundNBT writeCustomPacket() {
	return write(new CompoundNBT());
    }

    public VoxelShape getShape() {
	if (shapeCache != null) {
	    return shapeCache;
	}
	if (nodePos != null) {
	    TileEntity tile = nodePos.getTile(world);
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
