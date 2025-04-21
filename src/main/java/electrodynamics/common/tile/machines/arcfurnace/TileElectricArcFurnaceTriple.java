package electrodynamics.common.tile.machines.arcfurnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricArcFurnaceTriple;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileElectricArcFurnaceTriple extends TileElectricArcFurnace {

	public TileElectricArcFurnaceTriple(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsTiles.TILE_ELECTRICARCFURNACETRIPLE.get(), 3, worldPosition, blockState);

		addComponent(new ComponentContainerProvider(SubtypeMachine.electricarcfurnacetriple.tag(), this).createMenu((id, player) -> new ContainerElectricArcFurnaceTriple(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

}
