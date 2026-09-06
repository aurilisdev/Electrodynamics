package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerPotentiometer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.utilities.math.Color;

public class ScreenPotentiometer extends GenericScreen<ContainerPotentiometer> {

    private ScreenComponentEditBox consumption;

    private boolean needsUpdate = true;

    public ScreenPotentiometer(ContainerPotentiometer container, Inventory inv, Component title) {
	super(container, inv, title);
	addEditBox(consumption = new ScreenComponentEditBox(72, 35, 80, 16, getFontRenderer()).setTextColor(Color.WHITE)
		.setTextColorUneditable(Color.WHITE).setMaxLength(30).setFilter(ScreenComponentEditBox.DECIMAL)
		.setResponder(this::setConsumption));
	addComponent(new ScreenComponentSimpleLabel(10, 39, 10, Color.TEXT_GRAY,
		ElectroTextUtils.gui("potentiometer.usage")));
	addComponent(new ScreenComponentSimpleLabel(155, 39, 10, Color.TEXT_GRAY, DisplayUnits.WATT.getSymbol()));
    }

    private void setConsumption(String value) {
	if (value.isEmpty())
	    return;
	double parsedConsumption;
	try {
	    parsedConsumption = Double.parseDouble(value);
	} catch (NumberFormatException e) {
	    return;
	}
	menu.getSafeHost().ifPresent(potentiometer -> potentiometer.powerConsumption.setValue(parsedConsumption));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
	super.render(graphics, mouseX, mouseY, partialTicks);
	if (needsUpdate) {
	    needsUpdate = false;
	    menu.getSafeHost()
		    .ifPresent(source -> consumption.setValue(String.valueOf(source.powerConsumption.getValue())));
	}
    }

}
