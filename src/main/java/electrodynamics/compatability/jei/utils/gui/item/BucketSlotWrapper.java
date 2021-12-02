package electrodynamics.compatability.jei.utils.gui.item;

public class BucketSlotWrapper extends GenericItemSlotWrapper {

    public BucketSlotWrapper(int xStart, int yStart) {
	super(ITEM_SLOTS, xStart, yStart, 0, 0, 18, 18);
    }

    @Override
    public int itemXStart() {
	return getXPos();
    }

    @Override
    public int itemYStart() {
	return getYPos();
    }

}
