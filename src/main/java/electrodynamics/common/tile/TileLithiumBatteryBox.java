package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.inventory.container.tile.ContainerLithiumBatteryBox;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TileLithiumBatteryBox extends TileBatteryBox {

	public TileLithiumBatteryBox(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_LITHIUMBATTERYBOX.get(), 359.0 * (2 * ElectrodynamicsCapabilities.DEFAULT_VOLTAGE) / 20.0, 40000000, worldPosition, blockState);
		forceComponent(new ComponentContainerProvider("container.lithiumbatterybox").createMenu((id, player) -> new ContainerLithiumBatteryBox(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	@Override
	protected void tickServer(ComponentTickable tickable) {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing.getOpposite()));
		}
		if (tickable.getTicks() % 40 == 0) {
			output.update(worldPosition.relative(facing.getOpposite()));
		}
		receiveLimitLeft.set(powerOutput.get() * currentCapacityMultiplier.get());
		if (electro.getJoulesStored() > 0 && output.valid()) {
			electro.joules(electro.getJoulesStored() - ElectricityUtils.receivePower(output.getSafe(), facing, TransferPack.joulesVoltage(Math.min(electro.getJoulesStored(), powerOutput.get() * currentCapacityMultiplier.get()), electro.getVoltage()), false).getJoules());
		}
		currentCapacityMultiplier.set(1.0, true);
		currentVoltageMultiplier.set(1.0, true);
		for (ItemStack stack : this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems()) {
			if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade) {
				for (int i = 0; i < stack.getCount(); i++) {
					upgrade.subtype.applyUpgrade.accept(this, null, null);
				}
			}
		}
		electro.maxJoules(maxJoules.get() * currentCapacityMultiplier.get());
		electro.voltage(240.0 * currentVoltageMultiplier.get());
		if (electro.getJoulesStored() > electro.getMaxJoulesStored()) {
			electro.joules(electro.getMaxJoulesStored());
		}
		electro.drainElectricItem(3);
	}
}