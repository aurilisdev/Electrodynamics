package electrodynamics.common.tile.machines.mineralcrusher;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.common.inventory.container.ContainerO2OProcessorDouble;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileMineralCrusherDouble extends TileMineralCrusher {

	public TileMineralCrusherDouble() {
		super(ElectrodynamicsTiles.TILE_MINERALCRUSHERDOUBLE.get(), 2);

		addComponent(new ComponentContainerProvider(SubtypeMachine.mineralcrusherdouble.tag(), this).createMenu((id, player) -> new ContainerO2OProcessorDouble(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

	}

}
