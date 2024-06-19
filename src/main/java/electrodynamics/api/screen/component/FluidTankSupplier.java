package electrodynamics.api.screen.component;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.IFluidTank;

@OnlyIn(Dist.CLIENT)
@FunctionalInterface
public interface FluidTankSupplier {
	IFluidTank getTank();
}