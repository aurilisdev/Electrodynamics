package electrodynamics.api.gui.component;

import electrodynamics.api.gui.IGuiWrapper;
import electrodynamics.api.utilities.UtilitiesRendering;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

@OnlyIn(Dist.CLIENT)
public class GuiComponentFluid extends GuiComponentGauge {
    private FluidTankSupplier fluidInfoHandler;

    public GuiComponentFluid(FluidTankSupplier fluidInfoHandler, IGuiWrapper gui, int x, int y) {
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
	    return fluidStack.getFluid().getAttributes().getFlowingTexture();
	}
	return resource;
    }

    @Override
    protected ITextComponent getTooltip() {
	IFluidTank tank = fluidInfoHandler.getTank();
	if (tank != null) {
	    FluidStack fluidStack = tank.getFluid();
	    if (fluidStack.getAmount() > 0) {
		return new TranslationTextComponent(fluidStack.getTranslationKey())
			.append(new StringTextComponent(" " + tank.getFluidAmount() + " mB"));
	    }
	}
	return FluidStack.EMPTY.getDisplayName();
    }
}