package electrodynamics.compatibility.jei.utils.gui.item;

public class DefaultItemSlotWrapper extends GenericItemSlotWrapper {

	public DefaultItemSlotWrapper(int xStart, int yStart) {
		super(ITEM_SLOTS, xStart, yStart, 0, 18, 18, 18);
	}

	@Override
	public int itemXStart() {
		return getXPos() + 1;
	}

	@Override
	public int itemYStart() {
		return getYPos() + 1;
	}

}
