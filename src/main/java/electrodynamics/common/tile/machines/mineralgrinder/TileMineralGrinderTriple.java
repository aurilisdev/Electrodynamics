package electrodynamics.common.tile.machines.mineralgrinder;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.common.inventory.container.ContainerO2OProcessorTriple;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileMineralGrinderTriple extends TileMineralGrinder {

	public TileMineralGrinderTriple() {
		super(ElectrodynamicsTiles.TILE_MINERALGRINDERTRIPLE.get(), 3);

		addComponent(new ComponentContainerProvider(SubtypeMachine.mineralgrindertriple.tag(), this).createMenu((id, player) -> new ContainerO2OProcessorTriple(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

}
