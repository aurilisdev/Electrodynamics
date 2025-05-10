package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerElectrolyticSeparator;
import electrodynamics.common.tile.machines.TileElectrolyticSeparator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentProcessor;

public class ScreenElectrolyticSeparator extends GenericMaterialScreen<ContainerElectrolyticSeparator> {

	public ScreenElectrolyticSeparator(ContainerElectrolyticSeparator container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getSafeHost();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
				if (processor.operatingTicks.getValue()[0] > 0) {
					return Math.min(1.0, processor.operatingTicks.getValue()[0] / (processor.requiredTicks.getValue()[0] / 2.0));
				}
			}
			return 0;
		}, 38, 30));
		addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getSafeHost();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
				if (processor.operatingTicks.getValue()[0] > processor.requiredTicks.getValue()[0] / 2.0) {
					return Math.min(1.0, (processor.operatingTicks.getValue()[0] - processor.requiredTicks.getValue()[0] / 2.0) / (processor.requiredTicks.getValue()[0] / 2.0));
				}
			}
			return 0;
		}, 78, 30));
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileElectrolyticSeparator boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, 21, 18));
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileElectrolyticSeparator boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getOutputTanks()[0];
			}
			return null;
		}, 62, 18));
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileElectrolyticSeparator boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getOutputTanks()[1];
			}
			return null;
		}, 102, 18));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	}

}
