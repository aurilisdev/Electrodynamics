package electrodynamics.common.tile.battery;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCarbyneBatteryBox;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileCarbyneBatteryBox extends TileBatteryBox {

	public TileCarbyneBatteryBox(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CARBYNEBATTERYBOX.get(), SubtypeMachine.carbynebatterybox, 480, 359.0 * (4 * ElectrodynamicsCapabilities.DEFAULT_VOLTAGE) / 20.0, 80000000, worldPosition, blockState);
		forceComponent(new ComponentContainerProvider("container.carbynebatterybox", this).createMenu((id, player) -> new ContainerCarbyneBatteryBox(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

}