package electrodynamics.client.guidebook.utils.pagedata.graphics;

import electrodynamics.client.guidebook.utils.components.Page;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * A simple wrapper class that contains render data for an image on the Guidebook screen. The class is capable of displaying images as well as text descriptors for said image.
 * 
 * @author skip999
 *
 */
public class ImageWrapperObject extends AbstractGraphicWrapper<ImageWrapperObject> {

	public final int uStart;
	public final int vStart;
	public final int imgwidth;
	public final int imgheight;
	public final ResourceLocation location;

	public ImageWrapperObject(int xOffset, int yOffset, int uStart, int vStart, int width, int height, int imgheight, int imgwidth, ResourceLocation location, GraphicTextDescriptor... descriptors) {
		this(xOffset, yOffset, uStart, vStart, width, height, imgheight, imgwidth, height, location, descriptors);
	}

	public ImageWrapperObject(int xOffset, int yOffset, int uStart, int vStart, int width, int height, int imgheight, int imgwidth, int trueHeight, ResourceLocation location, GraphicTextDescriptor... descriptors) {
		super(xOffset, yOffset, xOffset, yOffset, width, height, trueHeight, descriptors);
		this.uStart = uStart;
		this.vStart = vStart;
		this.imgwidth = imgwidth;
		this.imgheight = imgheight;
		this.location = location;

	}

	@Override
	public void render(GuiGraphics graphics, int wrapperX, int wrapperY, int xShift, int guiWidth, int guiHeight, Page page) {
		graphics.blit(location, guiWidth + wrapperX + xOffset + xShift, guiHeight + wrapperY + yOffset - descriptorTopOffset, uStart, vStart, width, height, imgheight, imgwidth);
	}

}
