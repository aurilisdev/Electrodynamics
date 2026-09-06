package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerElectrolyticSeparator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.component.types.ScreenComponentCondensedFluid;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasPressure;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasTemperature;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentProcessor;

public class ScreenElectrolyticSeparator extends GenericMaterialScreen<ContainerElectrolyticSeparator> {

    public ScreenElectrolyticSeparator(ContainerElectrolyticSeparator container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT,
		() -> container.getSafeHost()
			.map(tile -> tile.<ComponentProcessor>requireComponent(IComponentType.Processor))
			.filter(processor -> processor.operatingTicks.getValue()[0] > 0)
			.map(processor -> Math.min(1.0,
				processor.operatingTicks.getValue()[0] / (processor.requiredTicks.getValue()[0] / 2.0)))
			.orElse(0.0),
		38, 30));
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT,
		() -> container.getSafeHost()
			.map(tile -> tile.<ComponentProcessor>requireComponent(IComponentType.Processor))
			.filter(processor -> processor.operatingTicks
				.getValue()[0] > processor.requiredTicks.getValue()[0] / 2.0)
			.map(processor -> Math.min(1.0,
				(processor.operatingTicks.getValue()[0] - processor.requiredTicks.getValue()[0] / 2.0)
					/ (processor.requiredTicks.getValue()[0] / 2.0)))
			.orElse(0.0),
		78, 30));
	addComponent(new ScreenComponentFluidGauge(() -> container
		.getSafeHost().map(tile -> tile
			.<ComponentFluidHandlerMulti>requireComponent(IComponentType.FluidHandler).getInputTanks()[0])
		.orElse(null), 21, 18));
	for (int i = 0; i < 2; i++) {
	    int tankIndex = i;
	    addComponent(
		    new ScreenComponentGasGauge(
			    () -> container.getSafeHost()
				    .map(tile -> tile
					    .<ComponentGasHandlerMulti>requireComponent(IComponentType.GasHandler)
					    .getOutputTanks()[tankIndex])
				    .orElse(null),
			    62 + i * 40, 18));
	}
	addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE * 2));
	addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	addComponent(new ScreenComponentCondensedFluid(
		() -> container.getSafeHost().map(tile -> tile.condensedFluidFromGas).orElse(null), 122, 20));
    }
}