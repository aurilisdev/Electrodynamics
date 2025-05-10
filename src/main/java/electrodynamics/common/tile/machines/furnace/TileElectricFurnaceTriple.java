package electrodynamics.common.tile.machines.furnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceTriple;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileElectricFurnaceTriple extends TileElectricFurnace {

	public TileElectricFurnaceTriple() {
		super(ElectrodynamicsTiles.TILE_ELECTRICFURNACETRIPLE.get(),3);

		addComponent(new ComponentContainerProvider(SubtypeMachine.electricfurnacetriple.tag(), this).createMenu((id, player) -> new ContainerElectricFurnaceTriple(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

	}

}
