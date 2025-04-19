package electrodynamics.common.tile.machines.mineralgrinder;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorTriple;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileMineralGrinderTriple extends TileMineralGrinder {

	public TileMineralGrinderTriple(BlockPos pos, BlockState state) {
		super(ElectrodynamicsTiles.TILE_MINERALGRINDERTRIPLE.get(), 3, pos, state);

		addComponent(new ComponentContainerProvider(SubtypeMachine.mineralgrindertriple.tag(), this).createMenu((id, player) -> new ContainerO2OProcessorTriple(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

}
