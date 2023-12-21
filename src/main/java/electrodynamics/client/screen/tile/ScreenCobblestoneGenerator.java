package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCobblestoneGenerator;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.machines.TileCobblestoneGenerator;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ScreenCobblestoneGenerator extends GenericScreen<ContainerCobblestoneGenerator> {

	public ScreenCobblestoneGenerator(ContainerCobblestoneGenerator container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileCobblestoneGenerator cobble = container.getHostFromIntArray();
			if (cobble != null && cobble.isPowered.get()) {
				FluidTank tank = new FluidTank(1000);
				tank.fill(new FluidStack(Fluids.WATER, 1000), FluidAction.EXECUTE);
				return tank;
			}
			return null;
		}, 21, 18));
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileCobblestoneGenerator cobble = container.getHostFromIntArray();
			if (cobble != null && cobble.isPowered.get()) {
				FluidTank tank = new FluidTank(1000);
				tank.fill(new FluidStack(Fluids.LAVA, 1000), FluidAction.EXECUTE);
				return tank;
			}
			return null;
		}, 117, 18));
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			TileCobblestoneGenerator cobble = container.getHostFromIntArray();
			if (cobble != null) {
				return cobble.progress.get();
			}
			return 0;
		}, 40, 34));
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_LEFT, () -> {
			TileCobblestoneGenerator cobble = container.getHostFromIntArray();
			if (cobble != null) {
				return cobble.progress.get();
			}
			return 0;
		}, 90, 34));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.COBBLE_GEN_USAGE_PER_TICK * 20));
		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

}
