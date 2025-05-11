package electrodynamics.common.tile.electricitygrid;

import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;

public class TileLogisticalWire extends TileWire {

	public boolean isPowered = false;

	public TileLogisticalWire() {
		super(ElectrodynamicsTiles.TILE_LOGISTICALWIRE.get());
		forceComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
	}

	private void tickClient(ComponentTickable componentTickable) {
	}

	protected void tickServer(ComponentTickable component) {
		if (component.getTicks() % 10 == 0 && getNetwork() != null) {
			boolean shouldPower = getNetwork().getActiveTransmitted() > 0;
			if (shouldPower != isPowered) {
				isPowered = shouldPower;
				level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
				BlockEntityUtils.updateLit(this, isPowered);
			}
		}
	}
}
