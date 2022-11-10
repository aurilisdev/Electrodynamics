package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.ClientEvents;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileSeismicMarker extends GenericTile {

	public static final int MAX_RADIUS = Math.max(Math.min(Constants.MARKER_RADIUS, 128), 2);

	public TileSeismicMarker(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_SEISMICMARKER.get(), pos, state);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
	}

	private void tickClient(ComponentTickable tick) {
		Level world = getLevel();
		BlockPos pos = getBlockPos();
		if (world.hasNeighborSignal(pos)) {
			// do not combine!
			if (!ClientEvents.markerLines.containsKey(pos)) {
				List<AABB> boxes = new ArrayList<>();
				boxes.add(new AABB(pos.getX() + 0.25, pos.getY() + 0.5625, pos.getZ() + 0.4375, pos.getX() + MAX_RADIUS + 1.5625, pos.getY() + 0.6875, pos.getZ() + 0.5625));
				boxes.add(new AABB(pos.getX() + 0.25, pos.getY() + 0.5625, pos.getZ() + 0.4375, pos.getX() - MAX_RADIUS - 0.5625, pos.getY() + 0.6875, pos.getZ() + 0.5625));
				boxes.add(new AABB(pos.getX() + 0.4375, pos.getY() + 0.5625, pos.getZ() + 0.25, pos.getX() + 0.5625, pos.getY() + 0.6875, pos.getZ() + MAX_RADIUS + 1.5625));
				boxes.add(new AABB(pos.getX() + 0.4375, pos.getY() + 0.5625, pos.getZ() + 0.25, pos.getX() + 0.5625, pos.getY() + 0.6875, pos.getZ() - MAX_RADIUS - 0.5625));
				ClientEvents.markerLines.put(pos, boxes);
			}
		} else {
			ClientEvents.markerLines.remove(pos);
		}
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		if (getLevel().isClientSide) {
			ClientEvents.markerLines.remove(getBlockPos());
		}
	}

}
