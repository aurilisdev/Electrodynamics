package electrodynamics.common.tile.machines.furnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceDouble;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileElectricFurnaceDouble extends TileElectricFurnace {

	public TileElectricFurnaceDouble() {
		super(ElectrodynamicsTiles.TILE_ELECTRICFURNACEDOUBLE.get(), 2);

		addComponent(new ComponentContainerProvider(SubtypeMachine.electricfurnacedouble.tag(), this).createMenu((id, player) -> new ContainerElectricFurnaceDouble(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

	}

}
