package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerThermoelectricManipulator;
import electrodynamics.common.tile.gastransformer.thermoelectricmanipulator.TileThermoelectricManipulator;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentFluidInput;
import electrodynamics.prefab.screen.component.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.component.ScreenComponentGasInput;
import electrodynamics.prefab.screen.component.ScreenComponentGasPressure;
import electrodynamics.prefab.screen.component.ScreenComponentGasTemperature;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenThermoelectricManipulator extends GenericScreen<ContainerThermoelectricManipulator> {

	public ScreenThermoelectricManipulator(ContainerThermoelectricManipulator container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		imageHeight += 20;
		inventoryLabelY += 20;
		components.add(new ScreenComponentFluidInput(() -> {
			TileThermoelectricManipulator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, this, 10, 18));
		components.add(new ScreenComponentFluid(() -> {
			TileThermoelectricManipulator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getOutputTanks()[0];
			}
			return null;
		}, this, 96, 18));
		components.add(new ScreenComponentGasInput(() -> {
			TileThermoelectricManipulator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerMulti>getComponent(ComponentType.GasHandler).getInputTanks()[0];
			}
			return null;
		}, this, 46, 18));
		components.add(new ScreenComponentGasGauge(() -> {
			TileThermoelectricManipulator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerMulti>getComponent(ComponentType.GasHandler).getOutputTanks()[0];
			}
			return null;
		}, this, 132, 18));
		components.add(new ScreenComponentGasTemperature(this, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 2));
		components.add(new ScreenComponentGasPressure(this, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE));
		components.add(new ScreenComponentElectricInfo(this, -AbstractScreenComponentInfo.SIZE + 1, 2));
	}

}
