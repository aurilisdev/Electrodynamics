package electrodynamics.common.tile.electricitygrid;

import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.registers.ElectrodynamicsBlockTypes;

public class TileLogisticalWire extends TileWire {
	
	public boolean isPowered = false;

	public TileLogisticalWire() {
		super(ElectrodynamicsBlockTypes.TILE_LOGISTICALWIRE.get());
		forceComponent(new ComponentTickable(this).tickServer(this::tickServer));
	}

	protected void tickServer(ComponentTickable component) {
		if (component.getTicks() % 10 == 0) {
			boolean shouldPower = getNetwork().getActiveTransmitted() > 0;
			if (shouldPower != isPowered) {
				isPowered = shouldPower;
				level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
			}
		}
	}
}