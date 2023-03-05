package electrodynamics.client.guidebook.utils.pagedata;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject.ImageTextDescriptor;
import net.minecraft.world.item.Item;

/**
 * A simple wrapper class that contains render data for an item or block on the
 * Guidebook screen
 * The class is capable of displaying items/blocks as well as text descriptors
 * for said items/blocks
 * 
 * @author skip999
 *
 */
public class ItemWrapperObject {

	public int xOffset;// based upon location on screen
	public int yOffset;
	public float scale;
	public Item item;
	public int width; // for setting space next to the image
	public int height; // for setting space below the image

	public int descriptorTopOffset = 0;
	public int descriptorBottomOffset = 0;

	public boolean newPage = false;

	public boolean allowNextToOthers = false; // allows the image to be placed horizontally next to another if space permits

	public ImageTextDescriptor[] descriptors = {};

	public ItemWrapperObject(int xOffset, int yOffset, float scale, int width, int height, Item item, ImageTextDescriptor... descriptors) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.scale = scale;
		this.item = item;
		this.width = width;
		this.height = height;

		if (descriptors != null && descriptors.length > 0) {
			this.descriptors = descriptors;
			int highestDescriptor = 0;
			int lowestDescriptor = height + yOffset;
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

	public ItemWrapperObject setNewPage() {
		newPage = true;
		return this;
	}

	public ItemWrapperObject setHorizontalPlacement() {
		allowNextToOthers = true;
		return this;
	}

}
