package electrodynamics.compatibility.jei.screenhandlers;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.client.guidebook.utils.components.Page.GraphicWrapper;
import electrodynamics.client.guidebook.utils.components.Page.TextWrapper;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper.GraphicTextDescriptor;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

public class ScreenHandlerGuidebook implements IGuiContainerHandler<ScreenGuidebook> {

	@Override
	public @Nullable Object getIngredientUnderMouse(ScreenGuidebook screen, double mouseX, double mouseY) {

		int refX = screen.getXRef();
		int refY = screen.getYRef();

		int guiWidth = (int) screen.getGuiWidth();
		int guiHeight = (int) screen.getGuiHeight();

		int xAxis = (int) (mouseX - guiWidth);
		int yAxis = (int) (mouseY - guiHeight);

		Object returned = getJeiLookup(ScreenGuidebook.LEFT_X_SHIFT, (int) mouseX, (int) mouseY, refX, refY, xAxis, yAxis, guiWidth, guiHeight, screen.getCurrentPage(), screen);
		if (returned != null) {
			return returned;
		}
		return getJeiLookup(ScreenGuidebook.RIGHT_X_SHIFT - 8, (int) mouseX, (int) mouseY, refX, refY, xAxis, yAxis, guiWidth, guiHeight, screen.getNextPage(), screen);
	}

	private Object getJeiLookup(int xPageShift, int mouseX, int mouseY, int refX, int refY, int xAxis, int yAxis, int guiWidth, int guiHeight, Page page, ScreenGuidebook screen) {

		int textWidth = 0;
		int xShift = 0;

		int x = 0;
		int y = 0;

		for (TextWrapper text : page.keyPressText) {

			textWidth = screen.getFontRenderer().width(text.characters());

			if (text.centered()) {
				xShift = (ScreenGuidebook.TEXT_WIDTH - textWidth) / 2;

			}

			x = refX + xShift + xPageShift + text.x();
			y = refY + text.y();

			if (screen.isPointInRegionText(x, y, xAxis, yAxis, textWidth, ScreenGuidebook.LINE_HEIGHT)) {
				return text.onKeyPress().getJeiLookup();
			}

		}

		for (GraphicWrapper wrapper : page.keyPressGraphics) {

			AbstractGraphicWrapper<?> image = wrapper.graphic();

			x = guiWidth + wrapper.x() + image.lookupXOffset + xPageShift;
			y = guiHeight + wrapper.y() + image.lookupYOffset - image.descriptorTopOffset;

			if (screen.isPointInRegionGraphic(mouseX, mouseY, x, y, image.width, image.height)) {
				return wrapper.onKeyPress().getJeiLookup();
			}

			for (GraphicTextDescriptor descriptor : image.descriptors) {

				x = refX + wrapper.x() + descriptor.xOffsetFromImage + xPageShift;
				y = refY + wrapper.y() + descriptor.yOffsetFromImage;

				if (descriptor.onKeyPress != null && screen.isPointInRegionText(x, y, xAxis, yAxis, screen.getFontRenderer().width(descriptor.text), ScreenGuidebook.LINE_HEIGHT)) {
					return descriptor.onKeyPress.getJeiLookup();
				}

			}

		}
		return null;
	}

	@Override
	public List<Rect2i> getGuiExtraAreas(ScreenGuidebook screen) {
		Rect2i area1 = new Rect2i(((int) (screen.getGuiLeft() + screen.getGuiWidth())), screen.getGuiTop(), ((int) (ScreenGuidebook.LEFT_X_SHIFT + ScreenGuidebook.LEFT_TEXTURE_WIDTH + ScreenGuidebook.RIGHT_TEXTURE_WIDTH - screen.getGuiHeight())), ScreenGuidebook.LEFT_TEXTURE_HEIGHT);
		Rect2i area2 = new Rect2i(screen.getGuiLeft() + ScreenGuidebook.LEFT_X_SHIFT, screen.getGuiTop(), -ScreenGuidebook.LEFT_X_SHIFT, ScreenGuidebook.LEFT_TEXTURE_HEIGHT);
		return Arrays.asList(area1, area2);
	}

}
