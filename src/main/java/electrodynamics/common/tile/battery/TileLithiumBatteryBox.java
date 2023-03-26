package electrodynamics.common.tile.battery;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerLithiumBatteryBox;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileLithiumBatteryBox extends TileBatteryBox {

	public TileLithiumBatteryBox(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_LITHIUMBATTERYBOX.get(), SubtypeMachine.lithiumbatterybox, 240, 359.0 * (2 * ElectrodynamicsCapabilities.DEFAULT_VOLTAGE) / 20.0, 40000000, worldPosition, blockState);
		forceComponent(new ComponentContainerProvider("container.lithiumbatterybox").createMenu((id, player) -> new ContainerLithiumBatteryBox(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

}