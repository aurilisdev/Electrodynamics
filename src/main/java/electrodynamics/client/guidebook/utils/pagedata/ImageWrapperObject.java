package electrodynamics.client.guidebook.utils.pagedata;

import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * A simple wrapper class that contains render data for an image on the
 * Guidebook screen
 * 
 * The class is capable of displaying images as well as text descriptors for
 * said image
 * 
 * @author skip999
 *
 */
public class ImageWrapperObject {

	public int xOffset;// based upon location on page
	public int yOffset;// based upon location on page
	public int uStart;
	public int vStart;
	public int width;
	public int height;
	public int imgwidth;
	public int imgheight;
	public ResourceLocation location;

	public int trueHeight;// for creating space below the image

	public boolean newPage = false;

	public boolean allowNextToOthers = false; // allows the image to be placed horizontally next to another if space permits

	public int descriptorTopOffset = 0;
	public int descriptorBottomOffset = 0;

	public ImageTextDescriptor[] descriptors = {};

	public ImageWrapperObject(int xOffset, int yOffset, int uStart, int vStart, int width, int height, int imgheight, int imgwidth, ResourceLocation location, ImageTextDescriptor... descriptors) {
		this(xOffset, yOffset, uStart, vStart, width, height, imgheight, imgwidth, height, location, descriptors);
	}

	public ImageWrapperObject(int xOffset, int yOffset, int uStart, int vStart, int width, int height, int imgheight, int imgwidth, int trueHeight, ResourceLocation location, ImageTextDescriptor... descriptors) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.uStart = uStart;
		this.vStart = vStart;
		this.width = width;
		this.height = height;
		this.imgwidth = imgwidth;
		this.imgheight = imgheight;
		this.location = location;
		this.trueHeight = trueHeight;

		if (descriptors != null && descriptors.length > 0) {
			this.descriptors = descriptors;
			int highestDescriptor = 0;
			int lowestDescriptor = trueHeight + yOffset;
			for (ImageTextDescriptor descriptor : descriptors) {

				if (descriptor.yOffsetFromImage < highestDescriptor) {
					highestDescriptor = descriptor.yOffsetFromImage;
				} else if (descriptor.yOffsetFromImage + ScreenGuidebook.LINE_HEIGHT > lowestDescriptor) {
					lowestDescriptor = descriptor.yOffsetFromImage + ScreenGuidebook.LINE_HEIGHT;
				}
			}
			descriptorTopOffset = highestDescriptor;
			descriptorBottomOffset = lowestDescriptor - height - yOffset;
		}

	}

	public ImageWrapperObject setNewPage() {
		newPage = true;
		return this;
	}

	public ImageWrapperObject setHorizontalPlacement() {
		allowNextToOthers = true;
		return this;
	}

	public static class ImageTextDescriptor {

		public int xOffsetFromImage;
		public int yOffsetFromImage;
		public Component text;
		public int color;

		public ImageTextDescriptor(int xOffsetFromImage, int yOffsetFromImage, Component text) {
			this(xOffsetFromImage, yOffsetFromImage, 4210752, text);
		}

		public ImageTextDescriptor(int xOffsetFromImage, int yOffsetFromImage, int color, Component text) {
			this.xOffsetFromImage = xOffsetFromImage;
			this.yOffsetFromImage = yOffsetFromImage;
			this.text = text;
			this.color = color;
		}

	}

}
