package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCreativePowerSource;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.electricity.formatting.MeasurementUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.utilities.math.Color;

public class ScreenCreativePowerSource extends GenericScreen<ContainerCreativePowerSource> {

    private ScreenComponentEditBox voltage;
    private ScreenComponentEditBox power;
    private boolean needsUpdate = true;

    public ScreenCreativePowerSource(ContainerCreativePowerSource container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	addComponent(voltage = new ScreenComponentEditBox(80, 27, 49, 16, getFontRenderer()).setTextColor(Color.WHITE)
		.setTextColorUneditable(Color.WHITE).setMaxLength(6).setFilter(ScreenComponentEditBox.POSITIVE_INTEGER)
		.setResponder(this::setVoltage));
	addComponent(power = new ScreenComponentEditBox(80, 45, 49, 16, getFontRenderer()).setTextColor(Color.WHITE)
		.setTextColorUneditable(Color.WHITE).setFilter(ScreenComponentEditBox.POSITIVE_DECIMAL)
		.setResponder(this::setPower));
	addComponent(new ScreenComponentSimpleLabel(40, 31, 10, Color.TEXT_GRAY,
		ElectroTextUtils.gui("creativepowersource.voltage")));
	addComponent(new ScreenComponentSimpleLabel(40, 49, 10, Color.TEXT_GRAY,
		ElectroTextUtils.gui("creativepowersource.power")));
	addComponent(new ScreenComponentSimpleLabel(131, 31, 10, Color.TEXT_GRAY, DisplayUnits.VOLTAGE.getSymbol()));
	addComponent(new ScreenComponentSimpleLabel(131, 49, 10, Color.TEXT_GRAY,
		MeasurementUnits.MEGA.getSymbol().copy().append(DisplayUnits.WATT.getSymbol())));
    }

    private void setVoltage(String val) {
	voltage.setFocus(true);
	power.setFocus(false);
	handleVoltage(val);
    }

    private void handleVoltage(String val) {
	if (val.isEmpty())
	    return;
	int parsedVoltage;
	try {
	    parsedVoltage = Integer.parseInt(val);
	} catch (NumberFormatException e) {
	    return;
	}
	menu.getSafeHost().ifPresent(tile -> tile.voltage.setValue(parsedVoltage));
    }

    private void setPower(String val) {
	voltage.setFocus(false);
	power.setFocus(true);
	handlePower(val);
    }

    private void handlePower(String val) {
	if (val.isEmpty())
	    return;
	double parsedPower;
	try {
	    parsedPower = Double.parseDouble(val);
	} catch (NumberFormatException e) {
	    return;
	}
	menu.getSafeHost().ifPresent(tile -> tile.power.setValue(parsedPower));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
	super.render(graphics, mouseX, mouseY, partialTicks);
	if (needsUpdate) {
	    needsUpdate = false;
	    menu.getSafeHost().ifPresent(source -> {
		voltage.setValue(String.valueOf(source.voltage.getValue()));
		power.setValue(String.valueOf(source.power.getValue()));
	    });
	}
    }
}