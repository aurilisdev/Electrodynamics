package electrodynamics.common.tile.machines.arcfurnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricArcFurnaceDouble;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileElectricArcFurnaceDouble extends TileElectricArcFurnace {

	public TileElectricArcFurnaceDouble() {
		super(ElectrodynamicsTiles.TILE_ELECTRICARCFURNACEDOUBLE.get(), 2);

		addComponent(new ComponentContainerProvider(SubtypeMachine.electricarcfurnacedouble.tag(), this).createMenu((id, player) -> new ContainerElectricArcFurnaceDouble(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

}
