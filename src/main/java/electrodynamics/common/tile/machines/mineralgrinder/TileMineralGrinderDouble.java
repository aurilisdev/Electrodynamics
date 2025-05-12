package electrodynamics.common.tile.machines.mineralgrinder;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.common.inventory.container.ContainerO2OProcessorDouble;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileMineralGrinderDouble extends TileMineralGrinder {

	public TileMineralGrinderDouble() {
		super(ElectrodynamicsTiles.TILE_MINERALGRINDERDOUBLE.get(), 2);

		addComponent(new ComponentContainerProvider(SubtypeMachine.mineralgrinderdouble.tag(), this).createMenu((id, player) -> new ContainerO2OProcessorDouble(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}
}
