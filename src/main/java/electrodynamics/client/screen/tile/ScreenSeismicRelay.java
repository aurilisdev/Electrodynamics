package electrodynamics.client.screen.tile;

import java.util.List;

import electrodynamics.common.inventory.container.tile.ContainerSeismicRelay;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.utilities.math.Color;

public class ScreenSeismicRelay extends GenericScreen<ContainerSeismicRelay> {
    public ScreenSeismicRelay(ContainerSeismicRelay container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	addComponent(new ScreenComponentSimpleLabel(70, 20, 10, Color.TEXT_GRAY,
		ElectroTextUtils.gui("seismicrelay.dataheader")));
	addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
	    List<BlockPos> markers = menu.getSafeHost().map(relay -> relay.markerLocs.getValue()).orElseGet(List::of);
	    for (int i = 0; i < 4; i++) {
		if (i < markers.size())
		    renderCoordinate(graphics, markers.get(i), i * 10, i + 1);
		else
		    renderNotFound(graphics, i * 10, i + 1);
	    }
	}));
    }

    private void renderNotFound(GuiGraphics graphics, int offset, int index) {
	graphics.drawString(font, ElectroTextUtils.gui("seismicrelay.posnotfound", index), 80, 30 + offset,
		Color.TEXT_GRAY.color(), false);
    }

    private void renderCoordinate(GuiGraphics graphics, BlockPos pos, int offset, int index) {
	graphics.drawString(font, ElectroTextUtils.gui("seismicrelay.posfound", index, pos.toShortString()), 80,
		30 + offset, Color.TEXT_GRAY.color(), false);
    }

}
