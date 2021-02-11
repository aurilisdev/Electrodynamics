package electrodynamics.common.fluid;

import electrodynamics.api.network.pipe.IPipe;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class FluidUtilities {

	public static boolean isFluidReceiver(TileEntity acceptor) {
		if (acceptor != null) {
			for (Direction dir : Direction.values()) {
				if (acceptor.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, dir).isPresent()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isConductor(TileEntity acceptor) {
		return acceptor instanceof IPipe;
	}

	public static Integer receivePower(TileEntity acceptor, Direction direction, int transfer, boolean debug) {
		if (acceptor != null) {
			LazyOptional<IFluidHandler> cap = acceptor.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
			IFluidHandler handler = null;
			if (cap.isPresent()) {
				handler = cap.resolve().get();
			}
			if (handler != null) {
				boolean canPass = false;
				for (int i = 0; i < handler.getTanks(); i++) {
					if (handler.isFluidValid(i, new FluidStack(Fluids.WATER, transfer))) {
						canPass = true;
						break;
					}
				}
				if (canPass) {
					return handler.fill(new FluidStack(Fluids.WATER, transfer), debug ? FluidAction.SIMULATE : FluidAction.EXECUTE);
				}
			}
		}
		return 0;
	}

	public static boolean canInputPower(TileEntity acceptor, Direction direction) {
		return receivePower(acceptor, direction, Integer.MAX_VALUE, true) > 0;
	}

}
