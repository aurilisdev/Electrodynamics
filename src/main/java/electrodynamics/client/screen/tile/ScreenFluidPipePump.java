package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerFluidPipePump;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.utilities.math.Color;

public class ScreenFluidPipePump extends GenericScreen<ContainerFluidPipePump> {

    private ScreenComponentEditBox priority;
    private boolean needsUpdate = true;

    public ScreenFluidPipePump(ContainerFluidPipePump screenContainer, Inventory inv, Component titleIn) {
	super(screenContainer, inv, titleIn);
	addComponent(priority = new ScreenComponentEditBox(94, 35, 59, 16, getFontRenderer()).setTextColor(Color.WHITE)
		.setTextColorUneditable(Color.WHITE).setMaxLength(1).setResponder(this::setPriority)
		.setFilter(ScreenComponentEditBox.POSITIVE_INTEGER));
	addComponent(new ScreenComponentSimpleLabel(20, 39, 10, Color.TEXT_GRAY,
		ElectroTextUtils.gui("prioritypump.priority")));
    }

    private void setPriority(String prior) {
	if (prior.isEmpty())
	    return;
	int parsedPriority;
	try {
	    parsedPriority = Integer.parseInt(prior);
	} catch (NumberFormatException e) {
	    return;
	}
	int clampedPriority = Math.max(0, Math.min(9, parsedPriority));
	if (clampedPriority != parsedPriority)
	    priority.setValue(String.valueOf(clampedPriority));
	menu.getSafeHost().ifPresent(pump -> pump.priority.setValue(clampedPriority));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
	super.render(graphics, mouseX, mouseY, partialTicks);
	if (needsUpdate) {
	    needsUpdate = false;
	    menu.getSafeHost().ifPresent(pump -> priority.setValue(String.valueOf(pump.priority.getValue())));
	}
    }
}