package electrodynamics.common.tile.tanks.gas;

import electrodynamics.api.gas.GasAction;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerGasTankGeneric;
import electrodynamics.common.network.utils.GasUtilities;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.types.GenericGasTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GenericTileGasTank extends GenericGasTile {

	public GenericTileGasTank(BlockEntityType<?> type, BlockPos pos, BlockState state, SubtypeMachine machine, double capacity, int maxPressure, double maxTemperature) {
		super(type, pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentGasHandlerSimple(this, "", capacity, maxTemperature, maxPressure).setInputDirections(Direction.UP).setOutputDirections(Direction.DOWN).setOnGasCondensed(getCondensedHandler()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().gasInputs(1).gasOutputs(1)).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(machine, this).createMenu((id, player) -> new ContainerGasTankGeneric(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tick) {
		ComponentGasHandlerSimple handler = getComponent(ComponentType.GasHandler);
		GasUtilities.drainItem(this, handler.asArray());
		GasUtilities.fillItem(this, handler.asArray());
		GasUtilities.outputToPipe(this, handler.asArray(), handler.outputDirections);

		// Output to tank below
		BlockPos pos = getBlockPos();
		BlockPos below = pos.below();

		if (level.getBlockState(below).hasBlockEntity()) {
			BlockEntity tile = level.getBlockEntity(below);
			if (tile instanceof GenericTileGasTank tankBelow) {
				ComponentGasHandlerSimple belowHandler = tankBelow.getComponent(ComponentType.GasHandler);

				handler.drain(belowHandler.fill(handler.getGas(), GasAction.SIMULATE), GasAction.EXECUTE);
			}
		}
	}

	@Override
	public int getComparatorSignal() {
		ComponentGasHandlerSimple handler = getComponent(ComponentType.GasHandler);
		return (int) ((double) handler.getGasAmount() / (double) Math.max(1, handler.getCapacity()) * 15.0);
	}

}
