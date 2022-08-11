package physica.library.inventory.tooltip;

import java.awt.Rectangle;

import net.minecraft.inventory.Slot;

public class ToolTipSlot extends ToolTip {

	public Slot slot;

	public ToolTipSlot(Slot slot, String info) {
		super(new Rectangle(slot.xDisplayPosition, slot.yDisplayPosition, 18, 18), info);
		this.slot = slot;
	}

	@Override
	public boolean shouldShow()
	{
		return !slot.getHasStack();
	}
}
