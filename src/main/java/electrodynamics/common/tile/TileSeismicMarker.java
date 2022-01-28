package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.client.ClientEvents;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileSeismicMarker extends GenericTile {

	public static final int MAX_RADIUS = 64;
	
	//the only reason this is a tile is because of the damn onRemove() method
	public TileSeismicMarker(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_SEISMICMARKER.get(), pos, state);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
	}
	
	private void tickClient(ComponentTickable tick) {
		Level world = getLevel();
		BlockPos pos = getBlockPos();
		if(world.hasNeighborSignal(pos)) {
			//do not combine!
			if(!ClientEvents.markerLines.containsKey(pos)) {
				List<AABB> boxes = new ArrayList<>();
				boxes.add(new AABB(pos.getX() + 0.25, pos.getY() + 0.625, pos.getZ() + 0.4375, pos.getX() + MAX_RADIUS + 1.75, pos.getY() + 0.75, pos.getZ() + 0.5625));
				boxes.add(new AABB(pos.getX() + 0.25, pos.getY() + 0.625, pos.getZ() + 0.4375, pos.getX() - MAX_RADIUS - 0.75, pos.getY() + 0.75, pos.getZ() + 0.5625));
				boxes.add(new AABB(pos.getX() + 0.4375, pos.getY() + 0.625, pos.getZ() + 0.25, pos.getX() + 0.5625, pos.getY() + 0.75, pos.getZ() + MAX_RADIUS + 1.75));
				boxes.add(new AABB(pos.getX() + 0.4375, pos.getY() + 0.625, pos.getZ() + 0.25, pos.getX() + 0.5625, pos.getY() + 0.75, pos.getZ() - MAX_RADIUS - 0.75));
				ClientEvents.markerLines.put(pos, boxes);
			}
		} else {
			ClientEvents.markerLines.remove(pos);
		}
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		if(getLevel().isClientSide) {
			ClientEvents.markerLines.remove(getBlockPos());
		}
	}

}
