package electrodynamics.common.network;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.network.cable.type.IFluidPipe;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidUtilities {

	public static boolean isFluidReceiver(BlockEntity acceptor) {
		for (Direction dir : Direction.values()) {
			if (isFluidReceiver(acceptor, dir)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFluidReceiver(BlockEntity acceptor, Direction dir) {
		return acceptor != null && acceptor.getCapability(ForgeCapabilities.FLUID_HANDLER, dir).isPresent();
	}

	public static boolean isConductor(BlockEntity acceptor) {
		return acceptor instanceof IFluidPipe;
	}

	public static int receiveFluid(BlockEntity acceptor, Direction direction, FluidStack perReceiver, boolean debug) {

		if (!isFluidReceiver(acceptor, direction)) {
			return 0;
		}

		LazyOptional<IFluidHandler> cap = acceptor.getCapability(ForgeCapabilities.FLUID_HANDLER, direction);

		if (!cap.isPresent()) {
			return 0;
		}

		IFluidHandler handler = cap.resolve().get();

		for (int i = 0; i < handler.getTanks(); i++) {

			if (handler.isFluidValid(i, perReceiver)) {

				return handler.fill(perReceiver, debug ? FluidAction.SIMULATE : FluidAction.EXECUTE);

			}
		}

		return 0;
	}

	public static boolean canInputFluid(BlockEntity acceptor, Direction direction) {
		return isFluidReceiver(acceptor, direction);
	}

	public static void outputToPipe(GenericTile tile, FluidTank[] tanks, Direction... outputDirections) {

		Direction facing = tile.getFacing();

		for (Direction relative : outputDirections) {

			Direction direction = BlockEntityUtils.getRelativeSide(facing, relative.getOpposite());

			BlockPos face = tile.getBlockPos().relative(direction.getOpposite());

			BlockEntity faceTile = tile.getLevel().getBlockEntity(face);

			if (faceTile == null) {
				continue;
			}

			LazyOptional<IFluidHandler> cap = faceTile.getCapability(ForgeCapabilities.FLUID_HANDLER, direction);

			if (!cap.isPresent()) {
				continue;
			}

			IFluidHandler fHandler = cap.resolve().get();

			for (FluidTank fluidTank : tanks) {

				FluidStack tankFluid = fluidTank.getFluid();

				int amtAccepted = fHandler.fill(tankFluid, FluidAction.EXECUTE);

				FluidStack taken = new FluidStack(tankFluid.getFluid(), amtAccepted);

				fluidTank.drain(taken, FluidAction.EXECUTE);
			}
		}
	}

	public static void drainItem(GenericTile tile, FluidTank[] tanks) {

		ComponentInventory inv = tile.getComponent(IComponentType.Inventory);

		int bucketIndex = inv.getInputBucketStartIndex();

		int size = inv.getInputBucketContents().size();

		if (tanks.length < size) {

			return;

		}

		int index;

		for (int i = 0; i < size; i++) {

			index = bucketIndex + i;

			FluidTank tank = tanks[i];
			ItemStack stack = inv.getItem(index);

			int room = tank.getSpace();

			if (stack.isEmpty() || CapabilityUtils.isFluidItemNull() || room <= 0 || !CapabilityUtils.hasFluidItemCap(stack)) {
				continue;
			}

			IFluidHandlerItem handler = CapabilityUtils.getFluidHandlerItem(stack);

			FluidStack containerFluid = handler.drain(room, FluidAction.SIMULATE);

			if (containerFluid.isEmpty() || !tank.isFluidValid(containerFluid)) {
				continue;
			}

			int accepted = tank.fill(containerFluid, FluidAction.EXECUTE);

			handler.drain(accepted, FluidAction.EXECUTE);

			inv.setItem(index, handler.getContainer());

		}

	}

	public static void fillItem(GenericTile tile, FluidTank[] tanks) {

		ComponentInventory inv = tile.getComponent(IComponentType.Inventory);

		int bucketIndex = inv.getOutputBucketStartIndex();

		int size = inv.getOutputBucketContents().size();

		if (tanks.length < size) {

			return;

		}

		int index;

		for (int i = 0; i < size; i++) {

			index = bucketIndex + i;

			ItemStack stack = inv.getItem(index);

			FluidTank tank = tanks[i];

			if (stack.isEmpty() || CapabilityUtils.isFluidItemNull() || tank.isEmpty() || !CapabilityUtils.hasFluidItemCap(stack)) {
				continue;
			}

			FluidStack fluid = tank.getFluid();

			IFluidHandlerItem handler = (IFluidHandlerItem) stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).cast().resolve().get();

			int taken = handler.fill(fluid, FluidAction.EXECUTE);

			tank.drain(taken, FluidAction.EXECUTE);

			inv.setItem(index, handler.getContainer());
		}
	}

	@Deprecated(since = "don't set a filter if you want to allow for all fluids")
	public static Fluid[] getAllRegistryFluids() {
		List<Fluid> list = new ArrayList<>(ForgeRegistries.FLUIDS.getValues());
		Fluid[] fluids = new Fluid[list.size()];
		for (int i = 0; i < list.size(); i++) {
			fluids[i] = list.get(i);
		}
		return fluids;
	}

}

