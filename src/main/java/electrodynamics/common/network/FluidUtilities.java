package electrodynamics.common.network;

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
		if (isFluidReceiver(acceptor, direction)) {
			LazyOptional<IFluidHandler> cap = acceptor.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
			if (cap.isPresent()) {
				IFluidHandler handler = cap.resolve().get();
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
		return isFluidReceiver(acceptor, direction);
	}

}
