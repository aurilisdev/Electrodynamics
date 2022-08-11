package physica.forcefield.client.gui;

import java.awt.Color;
import java.awt.Rectangle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.utilities.IBaseUtilities;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.inventory.ContainerInterdictionMatrix;
import physica.forcefield.common.tile.TileInterdictionMatrix;
import physica.library.client.gui.GuiContainerBase;
import physica.library.inventory.slot.SlotBase;
import physica.library.inventory.tooltip.ToolTipTank;

@SideOnly(Side.CLIENT)
public class GuiInterdictionMatrix extends GuiContainerBase<TileInterdictionMatrix> implements IBaseUtilities {

	public GuiInterdictionMatrix(EntityPlayer player, TileInterdictionMatrix host) {
		super(new ContainerInterdictionMatrix(player, host), host);
		ySize += 51;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		addToolTip(new ToolTipTank(new Rectangle(8, 115, electricityMeterWidth, electricityMeterHeight), "gui.interdictionMatrix.fortron_tank", host.getFortronTank()));
		addButton(new GuiButton(1, width / 2 - 80, height / 2 - 102, "Toggle".length() * 8, 20, "Toggle"));
		String value = host.isBlackList ? "Blacklist" : "Whitelist";
		addButton(new GuiButton(2, width / 2 + 28, height / 2 - 15, value.length() * 6, 20, value));
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
		host.actionPerformed(button.id, Side.CLIENT);
		if (button.id == 2)
		{
			for (Object obj : inventorySlots.inventorySlots)
			{
				if (obj instanceof SlotBase)
				{
					SlotBase base = (SlotBase) obj;
					if (base.getSlotIndex() >= TileInterdictionMatrix.SLOT_STARTBANLIST)
					{
						base.setBaseColor(host.isBlackList ? Color.DARK_GRAY.darker() : Color.GRAY).setEdgeColor(host.isBlackList ? Color.DARK_GRAY.darker() : Color.GRAY);
					}
				}
			}
			button.displayString = host.isBlackList ? "Blacklist" : "Whitelist";
		}

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Status: " + (host.isActivated() ? "Active" : "Disabled"), 8, 29);
		drawString("Frequency: " + host.getFrequency(), 8, 93);
		drawString("Warn range: " + host.getWarnRange(), 8, 39);
		drawString("Action range: " + host.getActionRange(), 8, 49);
		drawString("Usage: " + host.getFortronUse() / 1000.0 + "L/t", 8, 108);
		drawStringCentered(StatCollector.translateToLocal("tile." + ForcefieldReferences.PREFIX + "interdictionMatrix.gui"), (int) (xSize / 1.65), 13);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		drawElectricity(8, 120, (float) host.getFortronTank().getFluidAmount() / host.getMaxFortron());
	}
}
