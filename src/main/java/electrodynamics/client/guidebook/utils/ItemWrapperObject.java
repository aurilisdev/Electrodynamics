package electrodynamics.client.guidebook.utils;

import net.minecraft.world.item.Item;

public class ItemWrapperObject {
	
	public int xOffset;
	public int yOffset;
	public float scale;
	public Item item;
	
	public ItemWrapperObject(int xOffset, int yOffset, float scale, Item item) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.scale = scale;
		this.item = item;
	}

}
