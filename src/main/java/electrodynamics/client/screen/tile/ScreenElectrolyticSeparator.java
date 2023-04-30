package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerElectrolyticSeparator;
import electrodynamics.common.tile.TileElectrolyticSeparator;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentCondensedFluid;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluidInput;
import electrodynamics.prefab.screen.component.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.component.ScreenComponentGasPressure;
import electrodynamics.prefab.screen.component.ScreenComponentGasTemperature;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenElectrolyticSeparator extends GenericScreen<ContainerElectrolyticSeparator> {

	public ScreenElectrolyticSeparator(ContainerElectrolyticSeparator container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		components.add(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.operatingTicks.get() > 0) {
					return Math.min(1.0, processor.operatingTicks.get() / (processor.requiredTicks.get() / 2.0));
				}
			}
			return 0;
		}, this, 38, 30));
		components.add(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.operatingTicks.get() > processor.requiredTicks.get() / 2.0) {
					return Math.min(1.0, (processor.operatingTicks.get() - processor.requiredTicks.get() / 2.0) / (processor.requiredTicks.get() / 2.0));
				}
			}
			return 0;
		}, this, 78, 30));
		components.add(new ScreenComponentFluidInput(() -> {
			TileElectrolyticSeparator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, this, 21, 18));
		components.add(new ScreenComponentGasGauge(() -> {
			TileElectrolyticSeparator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerMulti>getComponent(ComponentType.GasHandler).getOutputTanks()[0];
			}
			return null;
		}, this, 62, 18));
		components.add(new ScreenComponentGasGauge(() -> {
			TileElectrolyticSeparator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerMulti>getComponent(ComponentType.GasHandler).getOutputTanks()[1];
			}
			return null;
		}, this, 102, 18));
		components.add(new ScreenComponentGasTemperature(this, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 2));
		components.add(new ScreenComponentGasPressure(this, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE));
		components.add(new ScreenComponentElectricInfo(this, -AbstractScreenComponentInfo.SIZE + 1, 2));
		components.add(new ScreenComponentCondensedFluid(this, () -> {
			TileElectrolyticSeparator electric = container.getHostFromIntArray();
			if (electric == null) {
				return null;
			}

			return electric.condensedFluidFromGas;

		}, 122, 20));
	}

}
