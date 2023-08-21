package electrodynamics.client.guidebook.utils.pagedata.graphics;

import javax.annotation.Nullable;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.client.guidebook.utils.pagedata.AbstractWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.OnClick;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

/**
 * A specialized version of AbstractWrapperObject for drawing images amongst other graphics to the guidebook screen. The image can also have text associated with it.
 * 
 * @author skip999
 *
 * @param <T> The non-abstract class to keep the builder from breaking
 */
public abstract class AbstractGraphicWrapper<T extends AbstractGraphicWrapper<?>> extends AbstractWrapperObject<T> {

	public final int width;
	public final int height;

	public final int trueHeight;

	public final int xOffset;// based upon location on page
	public final int yOffset;// based upon location on page

	public final int lookupXOffset; // distinction due to issues with item renderer
	public final int lookupYOffset;

	public final int descriptorTopOffset;
	public final int descriptorBottomOffset;

	public boolean allowNextToOthers = false; // allows the image to be placed horizontally next to another if space permits

	public final GraphicTextDescriptor[] descriptors;

	public AbstractGraphicWrapper(int xOffset, int yOffset, int lookupXOffset, int lookupYOffset, int width, int height, int trueHeight, GraphicTextDescriptor... descriptors) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.lookupXOffset = lookupXOffset;
		this.lookupYOffset = lookupYOffset;
		this.width = width;
		this.height = height;
		this.trueHeight = trueHeight;

		if (descriptors != null && descriptors.length > 0) {
			this.descriptors = descriptors;
			int highestDescriptor = 0;
			int lowestDescriptor = height + yOffset;
			for (GraphicTextDescriptor descriptor : descriptors) {

				if (descriptor.yOffsetFromImage < highestDescriptor) {
					highestDescriptor = descriptor.yOffsetFromImage;
				} else if (descriptor.yOffsetFromImage + ScreenGuidebook.LINE_HEIGHT > lowestDescriptor) {
					lowestDescriptor = descriptor.yOffsetFromImage + ScreenGuidebook.LINE_HEIGHT;
				}
			}
			descriptorTopOffset = highestDescriptor;
			descriptorBottomOffset = lowestDescriptor - height - yOffset;

		} else {
			this.descriptors = new GraphicTextDescriptor[0];
			descriptorTopOffset = 0;
			descriptorBottomOffset = 0;
		}
	}

	@Override
	public T setNewPage() {
		newPage = true;
		return (T) this;
	}

	public T setHorizontalPlacement() {
		allowNextToOthers = true;
		return (T) this;
	}

	public abstract void render(GuiGraphics graphics, int wrapperX, int wrapperY, int xShift, int guiWidth, int guiHeight, Page page);

	public static class GraphicTextDescriptor {

		public int xOffsetFromImage;
		public int yOffsetFromImage;
		public Component text;
		public int color;

		@Nullable
		public OnTooltip onTooltip = null;
		@Nullable
		public OnClick onClick = null;
		@Nullable
		public OnKeyPress onKeyPress = null;

		public GraphicTextDescriptor(int xOffsetFromImage, int yOffsetFromImage, Component text) {
			this(xOffsetFromImage, yOffsetFromImage, 4210752, text);
		}

		public GraphicTextDescriptor(int xOffsetFromImage, int yOffsetFromImage, int color, Component text) {
			this.xOffsetFromImage = xOffsetFromImage;
			this.yOffsetFromImage = yOffsetFromImage;
			this.text = text;
			this.color = color;
		}

		public GraphicTextDescriptor onTooltip(OnTooltip onTooltip) {
			this.onTooltip = onTooltip;
			return this;
		}

		public GraphicTextDescriptor onClick(OnClick onClick) {
			this.onClick = onClick;
			return this;
		}

		public GraphicTextDescriptor onKeyPress(OnKeyPress onKeyPress) {
			this.onKeyPress = onKeyPress;
			return this;
		}

	}

}
