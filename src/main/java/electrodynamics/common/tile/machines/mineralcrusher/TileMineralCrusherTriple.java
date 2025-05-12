package electrodynamics.common.tile.machines.mineralcrusher;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.common.inventory.container.ContainerO2OProcessorTriple;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileMineralCrusherTriple extends TileMineralCrusher {

	public TileMineralCrusherTriple() {
		super(ElectrodynamicsTiles.TILE_MINERALCRUSHERTRIPLE.get(), 3);

		addComponent(new ComponentContainerProvider(SubtypeMachine.mineralcrushertriple.tag(), this).createMenu((id, player) -> new ContainerO2OProcessorTriple(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

}
