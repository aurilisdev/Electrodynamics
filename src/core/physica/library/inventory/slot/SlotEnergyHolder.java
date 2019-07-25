package physica.library.inventory.slot;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import physica.api.core.abstraction.AbstractionLayer;
import physica.library.client.gui.GuiContainerBase;
import physica.library.inventory.tooltip.ToolTip;
import physica.library.inventory.tooltip.ToolTipSlot;

public class SlotEnergyHolder extends SlotBase {

	private ToolTip toolTipBase;

	public SlotEnergyHolder(IInventory inventory, int slotIndex, int x, int y) {
		super(inventory, slotIndex, x, y);
	}

	public SlotEnergyHolder(IInventory inventory, int slotIndex, int x, int y, String info) {
		super(inventory, slotIndex, x, y);
		toolTipBase = new ToolTipSlot(this, info);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return AbstractionLayer.Electricity.isItemElectric(stack);
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
		return toolTipBase;
	}
}
