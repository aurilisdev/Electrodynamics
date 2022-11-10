package electrodynamics.prefab.screen.component;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.FluidTankSupplier;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentFluid extends ScreenComponentGauge {
	private FluidTankSupplier fluidInfoHandler;

	public ScreenComponentFluid(FluidTankSupplier fluidInfoHandler, IScreenWrapper gui, int x, int y) {
		super(gui, x, y);
		this.fluidInfoHandler = fluidInfoHandler;
	}

	@Override
	protected int getScaledLevel() {
		IFluidTank tank = fluidInfoHandler.getTank();
		if (tank != null) {
			if (tank.getFluidAmount() > 0 && tank.getCapacity() > 0) {
				return tank.getFluidAmount() * (HEIGHT - 2) / tank.getCapacity();
			}
		}

		return 0;
	}

	@Override
	protected void applyColor() {
		IFluidTank tank = fluidInfoHandler.getTank();
		if (tank != null) {
			FluidStack fluidStack = tank.getFluid();
			if (!fluidStack.isEmpty()) {
				IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
				RenderingUtils.color(extensions.getTintColor(fluidStack));
			}
		}
	}

	@Override
	protected ResourceLocation getTexture() {
		IFluidTank tank = fluidInfoHandler.getTank();
		if (tank != null) {
			FluidStack fluidStack = tank.getFluid();
			IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
			return extensions.getStillTexture();
		}
		return resource;
	}

	@Override
	protected Component getTooltip() {
		IFluidTank tank = fluidInfoHandler.getTank();
		if (tank != null) {
			FluidStack fluidStack = tank.getFluid();
			if (fluidStack.getAmount() > 0) {
				return Component.translatable(fluidStack.getTranslationKey()).append(Component.literal(" " + tank.getFluidAmount() + " mB"));
			}
		}
		return FluidStack.EMPTY.getDisplayName();
	}
}