package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerThermoelectricManipulator;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.pipelines.gas.gastransformer.thermoelectricmanipulator.GenericTileThermoelectricManipulator;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasStack;
import voltaic.api.screen.ITexture;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasPressure;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasTemperature;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.utilities.math.Color;

public class ScreenThermoelectricManipulator extends GenericScreen<ContainerThermoelectricManipulator> {

    private ScreenComponentEditBox temperature;

    private boolean needsUpdate = true;

    public ScreenThermoelectricManipulator(ContainerThermoelectricManipulator container, Inventory inv,
	    Component titleIn) {
	super(container, inv, titleIn);
	imageHeight += 30;
	inventoryLabelY += 30;
	addComponent(new ScreenComponentFluidGauge(() -> {
	    GenericTileThermoelectricManipulator boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
	    }
	    return null;
	}, 10, 18));
	addComponent(new ScreenComponentFluidGauge(() -> {
	    GenericTileThermoelectricManipulator boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getOutputTanks()[0];
	    }
	    return null;
	}, 96, 18));
	addComponent(new ScreenComponentGasGauge(() -> {
	    GenericTileThermoelectricManipulator boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentGasHandlerMulti>getComponent(IComponentType.GasHandler).getInputTanks()[0];
	    }
	    return null;
	}, 46, 18));
	addComponent(new ScreenComponentGasGauge(() -> {
	    GenericTileThermoelectricManipulator boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentGasHandlerMulti>getComponent(IComponentType.GasHandler).getOutputTanks()[0];
	    }
	    return null;
	}, 132, 18));
	addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE * 2));
	addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	addComponent(new ScreenComponentGeneric(ITexture.Textures.CONDENSER_COLUMN, 62, 19));

	addEditBox(temperature = new ScreenComponentEditBox(94, 75, 59, 16, getFontRenderer()).setTextColor(Color.WHITE)
		.setTextColorUneditable(Color.WHITE).setMaxLength(20).setResponder(this::setTemperature)
		.setFilter(ScreenComponentEditBox.POSITIVE_INTEGER));

	addComponent(new ScreenComponentSimpleLabel(10, 80, 10, Color.TEXT_GRAY,
		ElectroTextUtils.gui("thermoelectricmanipulator.temp")));
	addComponent(new ScreenComponentSimpleLabel(155, 80, 10, Color.TEXT_GRAY,
		DisplayUnits.TEMPERATURE_KELVIN.getSymbol()));
    }

    private void setTemperature(String temp) {

	GenericTileThermoelectricManipulator manipulator = menu.getSafeHost();

	if ((manipulator == null) || temp.isEmpty()) {
	    return;
	}

	int temperature = Gas.ROOM_TEMPERATURE;

	try {
	    temperature = Integer.parseInt(temp);
	} catch (Exception e) {

	}

	if (temperature < GasStack.ABSOLUTE_ZERO) {
	    temperature = Gas.ROOM_TEMPERATURE;
	} else if (temperature > ElectroConstants.GAS_TRANSFORMER_OUTPUT_TEMP_CAP) {
	    temperature = ElectroConstants.GAS_TRANSFORMER_OUTPUT_TEMP_CAP;
	    this.temperature.setValue("" + temperature);
	}

	manipulator.targetTemperature.setValue(temperature);

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
	super.render(graphics, mouseX, mouseY, partialTicks);
	if (needsUpdate) {
	    needsUpdate = false;
	    GenericTileThermoelectricManipulator manipulator = menu.getSafeHost();
	    if (manipulator != null) {
		temperature.setValue("" + manipulator.targetTemperature.getValue());
	    }
	}
    }

}
