package electrodynamics.common.tile.machines.furnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceTriple;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileElectricFurnaceTriple extends TileElectricFurnace {

    public TileElectricFurnaceTriple(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_ELECTRICFURNACETRIPLE.get(), 3, worldPosition, blockState);

	addComponent(new ComponentContainerProvider(SubtypeMachine.electricfurnacetriple.tag(), this)
		.createMenu((id, player) -> new ContainerElectricFurnaceTriple(id, player,
			getComponent(IComponentType.Inventory), getCoordsArray())));

    }

}
