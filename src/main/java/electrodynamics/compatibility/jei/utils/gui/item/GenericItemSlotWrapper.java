package electrodynamics.compatibility.jei.utils.gui.item;

import electrodynamics.compatibility.jei.utils.gui.ScreenObjectWrapper;

public abstract class GenericItemSlotWrapper extends ScreenObjectWrapper {

    public GenericItemSlotWrapper(String texture, int xStart, int yStart, int textX, int textY, int height, int width) {
	super(texture, xStart, yStart, textX, textY, height, width);
    }

    public abstract int itemXStart();

    public abstract int itemYStart();

}
