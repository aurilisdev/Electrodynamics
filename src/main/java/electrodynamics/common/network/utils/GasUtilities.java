package electrodynamics.common.network.utils;

import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.capability.types.gas.IGasHandlerItem;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
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
		if (acceptor == null) {
			return false;
		}
		return acceptor.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER, dir).isPresent();
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
		ComponentDirection componentDirection = tile.getComponent(ComponentType.Direction);
		for (Direction relative : outputDirections) {
			Direction direction = BlockEntityUtils.getRelativeSide(componentDirection.getDirection(), relative.getOpposite());
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
		ComponentInventory inv = tile.getComponent(ComponentType.Inventory);
		List<ItemStack> cylinders = inv.getInputGasContents();
		if (tanks.length >= cylinders.size()) {
			for (int i = 0; i < cylinders.size(); i++) {
				GasTank tank = tanks[i];
				ItemStack stack = cylinders.get(i);
				if (!stack.isEmpty() && !CapabilityUtils.isGasItemNull()) {
					GasStack containerGas = CapabilityUtils.drainGasItem(stack, Integer.MAX_VALUE, GasAction.SIMULATE);
					if (tank.isGasValid(containerGas)) {
						double amtDrained = tank.fill(containerGas, GasAction.SIMULATE);
						GasStack drained = new GasStack(containerGas.getGas(), amtDrained, containerGas.getTemperature(), containerGas.getPressure());
						CapabilityUtils.drainGasItem(stack, drained, GasAction.EXECUTE);
						tank.fill(drained, GasAction.EXECUTE);
					}
				}
			}
		}

	}

	public static void fillItem(GenericTile tile, GasTank[] tanks) {
		ComponentInventory inv = tile.getComponent(ComponentType.Inventory);
		List<ItemStack> cylinders = inv.getOutputGasContents();
		if (tanks.length >= cylinders.size()) {
			for (int i = 0; i < cylinders.size(); i++) {
				ItemStack stack = cylinders.get(i);
				GasTank tank = tanks[i];
				if (!stack.isEmpty() && !CapabilityUtils.isGasItemNull()) {
					IGasHandlerItem handler = (IGasHandlerItem) stack.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).cast().resolve().get();
					GasStack gas = tank.getGas();
					GasStack taken;
					if (gas.getTemperature() > handler.getTankMaxTemperature(0) || gas.getPressure() > handler.getTankMaxPressure(0)) {
						taken = gas.copy();
						tile.getLevel().playSound(null, tile.getBlockPos(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0F, 1.0F);
						CapabilityUtils.fillGasItem(stack, gas, GasAction.EXECUTE);
					} else {
						double amtFilled = CapabilityUtils.fillGasItem(stack, gas, GasAction.EXECUTE);
						taken = new GasStack(gas.getGas(), amtFilled, gas.getTemperature(), gas.getPressure());
					}
					tank.drain(taken, GasAction.EXECUTE);

				}
			}
		}
	}

}
