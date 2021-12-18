package electrodynamics.api.screen.component;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.IFluidTank;

@OnlyIn(Dist.CLIENT)
@FunctionalInterface
public interface FluidTankSupplier {
	IFluidTank getTank();
}