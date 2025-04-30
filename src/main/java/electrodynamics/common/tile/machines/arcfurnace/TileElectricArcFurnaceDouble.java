package electrodynamics.common.tile.machines.arcfurnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricArcFurnaceDouble;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileElectricArcFurnaceDouble extends TileElectricArcFurnace {

	public TileElectricArcFurnaceDouble(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsTiles.TILE_ELECTRICARCFURNACEDOUBLE.get(), 2, worldPosition, blockState);

		addComponent(new ComponentContainerProvider(SubtypeMachine.electricarcfurnacedouble.tag(), this).createMenu((id, player) -> new ContainerElectricArcFurnaceDouble(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

}
