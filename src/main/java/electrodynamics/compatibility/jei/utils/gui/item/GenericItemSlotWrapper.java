package electrodynamics.compatibility.jei.utils.gui.item;

import electrodynamics.compatibility.jei.utils.gui.ScreenObjectWrapper;
import net.minecraft.resources.ResourceLocation;

public abstract class GenericItemSlotWrapper extends ScreenObjectWrapper {

	public GenericItemSlotWrapper(ResourceLocation texture, int xStart, int yStart, int textX, int textY, int width, int height) {
		super(texture, xStart, yStart, textX, textY, width, height);
	}

	public abstract int itemXStart();

	public abstract int itemYStart();

}
