package electrodynamics.common.network;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.network.cable.type.IFluidPipe;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidUtilities {

	public static boolean isFluidReceiver(TileEntity acceptor) {
		for (Direction dir : Direction.values()) {
			if (isFluidReceiver(acceptor, dir)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFluidReceiver(TileEntity acceptor, Direction dir) {
		return acceptor != null && acceptor.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, dir).isPresent();
	}

	public static boolean isConductor(TileEntity acceptor) {
		return acceptor instanceof IFluidPipe;
	}

	public static int receiveFluid(TileEntity acceptor, Direction direction, FluidStack perReceiver, boolean debug) {

		if (!isFluidReceiver(acceptor, direction)) {
			return 0;
		}

		LazyOptional<IFluidHandler> cap = acceptor.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);

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

	public static boolean canInputFluid(TileEntity acceptor, Direction direction) {
		return isFluidReceiver(acceptor, direction);
	}

	public static void outputToPipe(GenericTile tile, FluidTank[] tanks, Direction... outputDirections) {

		Direction facing = tile.getFacing();

		for (Direction relative : outputDirections) {

			Direction direction = BlockEntityUtils.getRelativeSide(facing, relative.getOpposite());

			BlockPos face = tile.getBlockPos().relative(direction.getOpposite());

			TileEntity faceTile = tile.getLevel().getBlockEntity(face);

			if (faceTile == null) {
				continue;
			}

			LazyOptional<IFluidHandler> cap = faceTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);

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

			IFluidHandlerItem handler = (IFluidHandlerItem) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).cast().resolve().get();

			int taken = handler.fill(fluid, FluidAction.EXECUTE);

			tank.drain(taken, FluidAction.EXECUTE);

			inv.setItem(index, handler.getContainer());
		}
	}

	@Deprecated // "don't set a filter if you want to allow for all fluids"
	public static Fluid[] getAllRegistryFluids() {
		List<Fluid> list = new ArrayList<>(ForgeRegistries.FLUIDS.getValues());
		Fluid[] fluids = new Fluid[list.size()];
		for (int i = 0; i < list.size(); i++) {
			fluids[i] = list.get(i);
		}
		return fluids;
	}

}