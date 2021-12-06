package electrodynamics.compatability.jei.utils.gui.item;

public class BigItemSlotWrapper extends GenericItemSlotWrapper {

    public BigItemSlotWrapper(int xStart, int yStart) {
	super(ITEM_SLOTS, xStart, yStart, 0, 36, 26, 26);
    }

    @Override
    public int itemXStart() {
	return getXPos() + 4;
    }

    @Override
    public int itemYStart() {
	return getYPos() + 4;
    }

}
