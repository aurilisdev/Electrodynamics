package electrodynamics.compatibility.jei.screenhandlers;

import java.util.Optional;

import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.utils.IGasTank;
import electrodynamics.compatibility.jei.screenhandlers.cliableingredients.ClickableFluidIngredient;
import electrodynamics.compatibility.jei.screenhandlers.cliableingredients.ClickableGasIngredient;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.runtime.IClickableIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;

public class ScreenHandlerMaterialScreen implements IGuiContainerHandler<GenericMaterialScreen<?>> {

	@Override
	public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(GenericMaterialScreen<?> screen, double mouseX, double mouseY) {

		double xAxis = mouseX - screen.getGuiWidth();
		double yAxis = mouseY - screen.getGuiHeight();

		for (ScreenComponentFluidGauge gauge : screen.getFluidGauges()) {

			if (gauge.isMouseOver(xAxis, yAxis)) {
				IFluidTank tank = gauge.fluidInfoHandler.getTank();
				if (tank == null) {
					continue;
				}
				FluidStack stack = tank.getFluid();

				if (stack.isEmpty()) {
					continue;
				}
				return Optional.of(new ClickableFluidIngredient(gauge.getClickArea(), stack));
			}
		}

		for (ScreenComponentGasGauge gauge : screen.getGasGauges()) {
			if (gauge.isMouseOver(xAxis, yAxis)) {
				IGasTank tank = gauge.gasTank.get();
				if (tank == null) {
					continue;
				}
				GasStack stack = tank.getGas();
				if (stack.isEmpty()) {
					continue;
				}
				return Optional.of(new ClickableGasIngredient(gauge.getClickArea(), stack));
			}
		}

		return Optional.empty();
	}

}
