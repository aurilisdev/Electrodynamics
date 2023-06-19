package electrodynamics.compatibility.jei.screenhandlers;

import org.jetbrains.annotations.Nullable;

import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.utils.IGasTank;
import electrodynamics.prefab.screen.component.ScreenComponentFluidGauge;
import electrodynamics.prefab.screen.component.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class ScreenHandlerMaterialScreen implements IGuiContainerHandler<GenericMaterialScreen<?>> {
	

	@Override
	public @Nullable Object getIngredientUnderMouse(GenericMaterialScreen<?> screen, double mouseX, double mouseY) {
		
		double xAxis = mouseX - screen.getGuiWidth();
		double yAxis = mouseY - screen.getGuiHeight();
		
		for(ScreenComponentFluidGauge gauge : screen.getFluidGauges()) {
			
			if(gauge.isMouseOver(xAxis, yAxis)) {
				IFluidTank tank = gauge.fluidInfoHandler.getTank();
				if(tank == null) {
					continue;
				}
				FluidStack stack = tank.getFluid();
				
				if(stack.isEmpty()) {
					continue;
				}
				return stack;
			}
		}
		
		for(ScreenComponentGasGauge gauge : screen.getGasGauges()) {
			if(gauge.isMouseOver(xAxis, yAxis)) {
				IGasTank tank = gauge.gasTank.get();
				if(tank == null) {
					continue;
				}
				GasStack stack = tank.getGas();
				if(stack.isEmpty()) {
					continue;
				}
				return stack;
			}
		}
		
		return null;
	}
	

}
