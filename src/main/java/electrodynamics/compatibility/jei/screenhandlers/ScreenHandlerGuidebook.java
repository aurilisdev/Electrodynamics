package electrodynamics.compatibility.jei.screenhandlers;

import java.util.Arrays;
import java.util.List;

import electrodynamics.client.guidebook.ScreenGuidebook;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

public class ScreenHandlerGuidebook implements IGuiContainerHandler<ScreenGuidebook> {
	
	@Override
	public List<Rect2i> getGuiExtraAreas(ScreenGuidebook screen) {
		Rect2i area = new Rect2i(screen.getGuiLeft() + screen.getXPos(), screen.getGuiTop(), ScreenGuidebook.LEFT_X_SHIFT + ScreenGuidebook.LEFT_TEXTURE_WIDTH + ScreenGuidebook.RIGHT_TEXTURE_WIDTH - screen.getXPos(), ScreenGuidebook.LEFT_TEXTURE_HEIGHT);
		return Arrays.asList(area);
	}

}
