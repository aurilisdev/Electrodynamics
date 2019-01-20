package physica.api.lib.inventory.slot;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import physica.api.lib.inventory.GuiContainerBase;
import physica.api.lib.inventory.tooltip.IToolTipContainer;
import physica.api.lib.inventory.tooltip.ToolTip;
import physica.api.lib.inventory.tooltip.ToolTipSlot;

public class SlotBase extends Slot implements IRenderableSlot, IToolTipContainer {
	protected Color		edgeColor	= null;

	protected String	toolTip;

	public SlotBase(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	public SlotBase setEdgeColor(Color color) {
		edgeColor = color;
		return this;
	}

	public SlotBase setToolTip(String toolTip) {
		this.toolTip = toolTip;
		return this;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return inventory.isItemValidForSlot(getSlotIndex(), stack);
	}

	@Override
	public void renderSlotOverlay(Gui gui, int x, int y) {
		Minecraft.getMinecraft().renderEngine.bindTexture(GuiContainerBase.GUI_COMPONENTS);
		if (edgeColor != null)
		{
			GL11.glColor4f(edgeColor.getRed() / 255f, edgeColor.getGreen() / 255f, edgeColor.getBlue() / 255f, edgeColor.getAlpha() / 255f);
			gui.drawTexturedModalRect(x, y, 0, 0, 18, 18);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			gui.drawTexturedModalRect(x + 1, y + 1, 1, 1, 16, 16);
		} else
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			gui.drawTexturedModalRect(x, y, 0, 0, 18, 18);
		}
		if (!getHasStack())
		{
			drawIcon(gui, x, y);
		}
	}

	protected void drawIcon(Gui gui, int x, int y) {
	}

	@Override
	public ToolTip getToolTip() {
		if (toolTip != null) return new ToolTipSlot(this, toolTip);
		return null;
	}
}
