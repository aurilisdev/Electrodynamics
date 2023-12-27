package electrodynamics.compatibility.jei.utils.gui.types;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.compatibility.jei.utils.gui.ScreenObject;

public class ItemSlotObject extends ScreenObject {

	private boolean input;
	private ScreenObject icon = null;

	public ItemSlotObject(ISlotTexture slotTexture, int x, int y, boolean input) {
		super(slotTexture, x, y);
		this.input = input;
	}

	public ItemSlotObject(ISlotTexture slotTexture, ITexture iconTexture, int x, int y, boolean input) {
		super(slotTexture, x, y);
		int slotXOffset = (slotTexture.imageWidth() - iconTexture.imageWidth()) / 2;
		int slotYOffset = (slotTexture.imageHeight() - iconTexture.imageHeight()) / 2;
		icon = new ScreenObject(iconTexture, x + slotXOffset, y + slotYOffset);
		this.input = input;
	}

	public ScreenObject getIcon() {
		return icon;
	}

	public int getItemXStart() {
		return x - ((ISlotTexture) texture).xOffset();
	}

	public int getItemYStart() {
		return y - ((ISlotTexture) texture).yOffset();
	}

	public boolean isInput() {
		return input;
	}

}
