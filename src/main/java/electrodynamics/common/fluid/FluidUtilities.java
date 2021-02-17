package electrodynamics.common.fluid;

import electrodynamics.api.network.pipe.IPipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class FluidUtilities {

	public static boolean isFluidReceiver(TileEntity acceptor) {
		for (Direction dir : Direction.values()) {
			boolean is = isFluidReceiver(acceptor, dir);
			if (is) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFluidReceiver(TileEntity acceptor, Direction dir) {
		if (acceptor != null) {
			if (acceptor.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, dir).isPresent()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isConductor(TileEntity acceptor) {
		return acceptor instanceof IPipe;
	}

	public static Integer receiveFluid(TileEntity acceptor, Direction direction, FluidStack perReceiver, boolean debug) {
		if (acceptor != null) {
			LazyOptional<IFluidHandler> cap = acceptor.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
			IFluidHandler handler = null;
			if (cap.isPresent()) {
				handler = cap.resolve().get();
			}
			if (handler != null) {
				boolean canPass = false;
				for (int i = 0; i < handler.getTanks(); i++) {
					if (handler.isFluidValid(i, perReceiver)) {
						canPass = true;
						break;
					}
				}
				if (canPass) {
					return handler.fill(perReceiver, debug ? FluidAction.SIMULATE : FluidAction.EXECUTE);
				}
			}
		}
		return 0;
	}

	public static boolean canInputFluid(TileEntity acceptor, Direction direction, FluidStack stack) {
		LazyOptional<IFluidHandler> cap = acceptor.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
		IFluidHandler handler = null;
		FluidStack per = new FluidStack(stack.getFluid(), Integer.MAX_VALUE);
		if (cap.isPresent()) {
			handler = cap.resolve().get();
		}
		if (handler != null) {
			for (int i = 0; i < handler.getTanks(); i++) {
				if (handler.isFluidValid(i, per)) {
					return true;
				}
			}
		}
		return false;
	}

}
