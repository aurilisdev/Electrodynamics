package physica.library.inventory.slot;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import physica.library.client.gui.GuiContainerBase;
import physica.library.inventory.tooltip.IToolTipContainer;
import physica.library.inventory.tooltip.ToolTip;
import physica.library.inventory.tooltip.ToolTipSlot;

public class SlotBase extends Slot implements IRenderableSlot, IToolTipContainer {

	protected Color edgeColor = null;
	protected Color baseColor = null;

	protected String toolTip;

	public SlotBase(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	public SlotBase setEdgeColor(Color color)
	{
		edgeColor = color;
		return this;
	}

	public SlotBase setBaseColor(Color color)
	{
		baseColor = color;
		return this;
	}

	public SlotBase setToolTip(String toolTip)
	{
		this.toolTip = toolTip;
		return this;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return inventory.isItemValidForSlot(getSlotIndex(), stack);
	}

	@Override
	public void renderSlotOverlay(Gui gui, int x, int y)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(GuiContainerBase.GUI_COMPONENTS);
		if (edgeColor != null)
		{
			GL11.glColor4f(edgeColor.getRed() / 255f, edgeColor.getGreen() / 255f, edgeColor.getBlue() / 255f, edgeColor.getAlpha() / 255f);
			gui.drawTexturedModalRect(x, y, 0, 0, 18, 18);
		} else
		{
			gui.drawTexturedModalRect(x, y, 0, 0, 18, 18);
		}
		if (baseColor != null)
		{
			GL11.glColor4f(baseColor.getRed() / 255f + 0.2f, baseColor.getGreen() / 255f + 0.2f, baseColor.getBlue() / 255f + 0.2f, baseColor.getAlpha() / 255f);
			gui.drawTexturedModalRect(x + 1, y + 1, 1, 1, 16, 16);
		}
		if (!getHasStack())
		{
			drawIcon(gui, x, y);
		}
	}

	protected void drawIcon(Gui gui, int x, int y)
	{
	}

	@Override
	public ToolTip getToolTip()
	{
		if (toolTip != null)
		{
			return new ToolTipSlot(this, toolTip);
		}
		return null;
	}
}
