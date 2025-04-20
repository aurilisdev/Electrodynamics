package electrodynamics.common.tile.machines.wiremill;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.common.inventory.container.ContainerO2OProcessorTriple;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileWireMillTriple extends TileWireMill {

	public TileWireMillTriple(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsTiles.TILE_WIREMILLTRIPLE.get(), 3, worldPosition, blockState);

		addComponent(new ComponentContainerProvider(SubtypeMachine.wiremilltriple.tag(), this).createMenu((id, player) -> new ContainerO2OProcessorTriple(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

}