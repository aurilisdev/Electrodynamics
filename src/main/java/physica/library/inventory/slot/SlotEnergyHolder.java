package physica.library.inventory.slot;

import org.lwjgl.opengl.GL11;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import physica.library.client.gui.GuiContainerBase;
import physica.library.inventory.tooltip.IToolTipContainer;
import physica.library.inventory.tooltip.ToolTip;
import physica.library.inventory.tooltip.ToolTipSlot;

public class SlotEnergyHolder extends SlotBase implements IRenderableSlot, IToolTipContainer {

	private ToolTip toolTip;

	public SlotEnergyHolder(IInventory inventory, int slotIndex, int x, int y) {
		super(inventory, slotIndex, x, y);
	}

	public SlotEnergyHolder(IInventory inventory, int slotIndex, int x, int y, String info) {
		super(inventory, slotIndex, x, y);
		toolTip = new ToolTipSlot(this, info);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.getItem() instanceof IEnergyContainerItem;
	}

	@Override
	protected void drawIcon(Gui gui, int x, int y)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(GuiContainerBase.GUI_COMPONENTS);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		gui.drawTexturedModalRect(x, y, 0, 18, 18, 18);
	}

	@Override
	public ToolTip getToolTip()
	{
		return toolTip;
	}
}
