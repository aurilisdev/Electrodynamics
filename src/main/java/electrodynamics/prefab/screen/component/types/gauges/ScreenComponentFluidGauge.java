package electrodynamics.prefab.screen.component.types.gauges;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.screen.component.FluidTankSupplier;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentFluidGauge extends AbstractScreenComponentGauge {
	public FluidTankSupplier fluidInfoHandler;

	public ScreenComponentFluidGauge(FluidTankSupplier fluidInfoHandler, int x, int y) {
		super(x, y);
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
				FluidAttributes extensions = fluidStack.getFluid().getAttributes();
				RenderingUtils.setShaderColor(new Color(extensions.getColor(fluidStack)));
			}
		}
	}

	@Override
	protected ResourceLocation getTexture() {
		IFluidTank tank = fluidInfoHandler.getTank();
		if (tank != null) {
			FluidStack fluidStack = tank.getFluid();
			FluidAttributes extensions = fluidStack.getFluid().getAttributes();
			return extensions.getStillTexture();
		}
		return texture.getLocation();
	}

	@Override
	protected List<? extends IReorderingProcessor> getTooltips() {
		List<IReorderingProcessor> tooltips = new ArrayList<>();
		IFluidTank tank = fluidInfoHandler.getTank();
		if (tank != null) {
			FluidStack fluidStack = tank.getFluid();
			if (fluidStack.getAmount() > 0) {
				tooltips.add(new TranslationTextComponent(fluidStack.getTranslationKey()).getVisualOrderText());
				tooltips.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(tank.getFluidAmount()), ChatFormatter.formatFluidMilibuckets(tank.getCapacity())).withStyle(TextFormatting.GRAY).getVisualOrderText());
			} else {
				tooltips.add(ElectroTextUtils.ratio(new StringTextComponent("0"), ChatFormatter.formatFluidMilibuckets(tank.getCapacity())).withStyle(TextFormatting.GRAY).getVisualOrderText());
			}
		}
		return tooltips;
	}
}