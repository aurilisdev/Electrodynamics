package electrodynamics.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.FluidTankSupplier;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentGauge;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentFluid extends AbstractScreenComponentGauge {
	protected FluidTankSupplier fluidInfoHandler;

	public ScreenComponentFluid(FluidTankSupplier fluidInfoHandler, IScreenWrapper gui, int x, int y) {
		super(gui, x, y);
		this.fluidInfoHandler = fluidInfoHandler;
	}

	@Override
	protected int getScaledLevel() {
		IFluidTank tank = fluidInfoHandler.getTank();
		if (tank != null) {
			if (tank.getFluidAmount() > 0 && tank.getCapacity() > 0) {
				return tank.getFluidAmount() * (GaugeTextures.BACKGROUND_DEFAULT.textureHeight() - 2) / tank.getCapacity();
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
		return texture.getLocation();
	}

	@Override
	protected List<? extends FormattedCharSequence> getTooltips() {
		List<FormattedCharSequence> tooltips = new ArrayList<>();
		IFluidTank tank = fluidInfoHandler.getTank();
		if (tank != null) {
			FluidStack fluidStack = tank.getFluid();
			if (fluidStack.getAmount() > 0) {
				tooltips.add(Component.translatable(fluidStack.getTranslationKey()).getVisualOrderText());
				tooltips.add(Component.literal(tank.getFluidAmount() + " / " + tank.getCapacity() + " mB").withStyle(ChatFormatting.GRAY).getVisualOrderText());
			} else {
				tooltips.add(Component.literal( "0 / " + tank.getCapacity() + " mB").getVisualOrderText());
			}
		} 
		return tooltips;
	}
}