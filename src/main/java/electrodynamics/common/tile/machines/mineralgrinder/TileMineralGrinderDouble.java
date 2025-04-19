package electrodynamics.common.tile.machines.mineralgrinder;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorDouble;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileMineralGrinderDouble extends TileMineralGrinder {

	public TileMineralGrinderDouble(BlockPos pos, BlockState state) {
		super(ElectrodynamicsTiles.TILE_MINERALGRINDERDOUBLE.get(), 2, pos, state);

		addComponent(new ComponentContainerProvider(SubtypeMachine.mineralgrinderdouble.tag(), this).createMenu((id, player) -> new ContainerO2OProcessorDouble(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}
}
