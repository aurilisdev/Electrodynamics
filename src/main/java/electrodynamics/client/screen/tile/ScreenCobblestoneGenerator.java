package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCobblestoneGenerator;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.machines.TileCobblestoneGenerator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;

public class ScreenCobblestoneGenerator extends GenericScreen<ContainerCobblestoneGenerator> {

	public ScreenCobblestoneGenerator(ContainerCobblestoneGenerator container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileCobblestoneGenerator cobble = container.getSafeHost();
			if (cobble != null && cobble.isPowered.getValue()) {
				FluidTank tank = new FluidTank(1000);
				tank.fill(new FluidStack(Fluids.WATER, 1000), FluidAction.EXECUTE);
				return tank;
			}
			return null;
		}, 21, 18));
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileCobblestoneGenerator cobble = container.getSafeHost();
			if (cobble != null && cobble.isPowered.getValue()) {
				FluidTank tank = new FluidTank(1000);
				tank.fill(new FluidStack(Fluids.LAVA, 1000), FluidAction.EXECUTE);
				return tank;
			}
			return null;
		}, 117, 18));
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			TileCobblestoneGenerator cobble = container.getSafeHost();
			if (cobble != null) {
				return cobble.progress.getValue();
			}
			return 0;
		}, 40, 34));
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_LEFT, () -> {
			TileCobblestoneGenerator cobble = container.getSafeHost();
			if (cobble != null) {
				return cobble.progress.getValue();
			}
			return 0;
		}, 90, 34));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(ElectroConstants.COBBLE_GEN_USAGE_PER_TICK * 20));
		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

}
