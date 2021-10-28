package electrodynamics.prefab.screen.component;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.FluidTankSupplier;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
	    if (tank.getFluidAmount() > 0) {
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
		UtilitiesRendering.color(fluidStack.getFluid().getAttributes().getColor());
	    }
	}
    }

    @Override
    protected ResourceLocation getTexture() {
	IFluidTank tank = fluidInfoHandler.getTank();
	if (tank != null) {
	    FluidStack fluidStack = tank.getFluid();
	    return fluidStack.getFluid().getAttributes().getStillTexture();
	}
	return resource;
    }

    @Override
    protected Component getTooltip() {
	IFluidTank tank = fluidInfoHandler.getTank();
	if (tank != null) {
	    FluidStack fluidStack = tank.getFluid();
	    if (fluidStack.getAmount() > 0) {
		return new TranslatableComponent(fluidStack.getTranslationKey()).append(new TextComponent(" " + tank.getFluidAmount() + " mB"));
	    }
	}
	return FluidStack.EMPTY.getDisplayName();
    }
}