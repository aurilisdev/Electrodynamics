package electrodynamics.common.tile.machines.wiremill;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorDouble;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileWireMillDouble extends TileWireMill {

	public TileWireMillDouble(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsTiles.TILE_WIREMILLDOUBLE.get(), 2, worldPosition, blockState);

		addComponent(new ComponentContainerProvider(SubtypeMachine.wiremilldouble.tag(), this).createMenu((id, player) -> new ContainerO2OProcessorDouble(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

}
