package electrodynamics.common.tile.machines.wiremill;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.common.inventory.container.ContainerO2OProcessorDouble;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileWireMillDouble extends TileWireMill {

	public TileWireMillDouble() {
		super(ElectrodynamicsTiles.TILE_WIREMILLDOUBLE.get(), 2);

		addComponent(new ComponentContainerProvider(SubtypeMachine.wiremilldouble.tag(), this).createMenu((id, player) -> new ContainerO2OProcessorDouble(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

}
