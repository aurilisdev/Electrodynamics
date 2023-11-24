package electrodynamics.common.network.utils;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.capability.types.gas.IGasHandlerItem;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;

public class GasUtilities {

	public static boolean isGasReciever(BlockEntity acceptor, Direction dir) {
		return acceptor != null && acceptor.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER, dir).isPresent();
	}

	public static double recieveGas(BlockEntity reciever, Direction dir, GasStack gas, GasAction action) {
		if (gas.isEmpty() || gas.getAmount() <= 0) {
			return 0;
		}
		GasStack copy = gas.copy();
		return reciever.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER, dir).map(cap -> {
			double taken = 0;
			double recieved = 0;
			for (int i = 0; i < cap.getTanks(); i++) {
				if (cap.isGasValid(i, copy)) {
					recieved = cap.fillTank(i, copy, action);
					copy.shrink(recieved);
					taken += recieved;
				}
			}
			return taken;
		}).orElse(0.0);
	}

	public static void outputToPipe(GenericTile tile, GasTank[] tanks, Direction... outputDirections) {

		Direction facing = tile.getFacing();

		for (Direction relative : outputDirections) {

			Direction direction = BlockEntityUtils.getRelativeSide(facing, relative.getOpposite());

			BlockPos face = tile.getBlockPos().relative(direction.getOpposite());

			BlockEntity faceTile = tile.getLevel().getBlockEntity(face);

			if (faceTile == null) {
				continue;
			}

			LazyOptional<IGasHandler> cap = faceTile.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER, direction);

			if (!cap.isPresent()) {
				continue;
			}

			IGasHandler gHandler = cap.resolve().get();

			for (GasTank gasTank : tanks) {

				for (int i = 0; i < gHandler.getTanks(); i++) {

					GasStack tankGas = gasTank.getGas().copy();

					double amtAccepted = gHandler.fillTank(i, tankGas, GasAction.EXECUTE);

					GasStack taken = new GasStack(tankGas.getGas(), amtAccepted, tankGas.getTemperature(), tankGas.getPressure());

					gasTank.drain(taken, GasAction.EXECUTE);
				}

			}
		}
	}

	public static void drainItem(GenericTile tile, GasTank[] tanks) {

		ComponentInventory inv = tile.getComponent(IComponentType.Inventory);

		int cylinderIndex = inv.getInputGasStartIndex();

		int size = inv.getInputGasContents().size();

		if (tanks.length < size) {

			return;

		}

		int index;

		for (int i = 0; i < size; i++) {

			index = cylinderIndex + i;

			GasTank tank = tanks[i];

			ItemStack stack = inv.getItem(index);

			double room = tank.getRoom();

			if (stack.isEmpty() || CapabilityUtils.isGasItemNull() || room <= 0 || !CapabilityUtils.hasGasItemCap(stack)) {
				continue;
			}

			IGasHandlerItem handler = CapabilityUtils.getGasHandlerItem(stack);

			for (int j = 0; j < handler.getTanks(); j++) {

				if (room <= 0) {
					break;
				}

				GasStack taken = handler.drainTank(0, room, GasAction.SIMULATE);

				if (taken.isEmpty() || !tank.isGasValid(taken)) {
					continue;
				}

				double takenAmt = tank.fill(taken, GasAction.EXECUTE);

				handler.drainTank(j, takenAmt, GasAction.EXECUTE);

				room = tank.getRoom();

			}

			inv.setItem(index, handler.getContainer());

		}

	}

	public static void fillItem(GenericTile tile, GasTank[] tanks) {

		ComponentInventory inv = tile.getComponent(IComponentType.Inventory);

		int cylinderIndex = inv.getOutputGasStartIndex();

		int size = inv.getOutputGasContents().size();

		if (tanks.length < size) {

			return;

		}

		int index;

		for (int i = 0; i < size; i++) {

			index = cylinderIndex + i;

			GasTank tank = tanks[i];

			ItemStack stack = inv.getItem(index);

			if (tank.isEmpty() || stack.isEmpty() || CapabilityUtils.isGasItemNull() || !CapabilityUtils.hasGasItemCap(stack)) {
				continue;
			}

			IGasHandlerItem handler = CapabilityUtils.getGasHandlerItem(stack);

			for (int j = 0; j < handler.getTanks(); j++) {

				if (tank.isEmpty()) {
					break;
				}

				GasStack gas = tank.getGas();

				if (gas.getTemperature() > handler.getTankMaxTemperature(0) || gas.getPressure() > handler.getTankMaxPressure(0)) {

					tile.getLevel().playSound(null, tile.getBlockPos(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0F, 1.0F);

				}

				double taken = handler.fillTank(j, gas, GasAction.EXECUTE);

				tank.drain(taken, GasAction.EXECUTE);

			}

			inv.setItem(index, handler.getContainer());

		}
	}

}
