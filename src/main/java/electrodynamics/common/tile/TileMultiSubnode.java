package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileMultiSubnode extends GenericTile {
	public Location nodePos;
	public VoxelShape shapeCache;

	public TileMultiSubnode(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_MULTI.get(), worldPosition, blockState);
		addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket));
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		if (nodePos != null) {
			nodePos.writeToNBT(compound, "node");
		}
		super.saveAdditional(compound);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		nodePos = Location.readFromNBT(compound, "node");
		Scheduler.schedule(20, this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler)::sendCustomPacket);
	}

	protected void readCustomPacket(CompoundTag tag) {
		load(tag);
	}

	protected void writeCustomPacket(CompoundTag nbt) {
		save(nbt);
	}

	public VoxelShape getShape() {
		if (shapeCache != null) {
			return shapeCache;
		}
		if (nodePos != null) {
			BlockEntity tile = nodePos.getTile(level);
			if (tile instanceof IMultiblockTileNode node) {
				BlockPos tp = tile.getBlockPos();
				BlockPos offset = new BlockPos(worldPosition.getX() - tp.getX(), worldPosition.getY() - tp.getY(), worldPosition.getZ() - tp.getZ());
				for (Subnode sub : node.getSubNodes()) {
					if (offset.equals(sub.pos)) {
						shapeCache = sub.shape;
						return shapeCache;
					}
				}
			}
		}
		return Shapes.block();
	}

}
