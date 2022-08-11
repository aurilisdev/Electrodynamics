package physica.nuclear.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import physica.api.core.utilities.IBaseUtilities;
import physica.library.client.gui.GuiContainerBase;
import physica.library.inventory.ContainerBase;
import physica.nuclear.common.tile.TileInsertableControlRod;

@SideOnly(Side.CLIENT)
public class GuiInsertableControlRod extends GuiContainerBase<TileInsertableControlRod> implements IBaseUtilities {

	public GuiInsertableControlRod(EntityPlayer player, TileInsertableControlRod host) {
		super(new ContainerBase<>(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Insertion: " + host.getInsertion() + "%", 50, 59);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		addButton(new GuiButton(1, width / 2 - 70, height / 2, "Raise 5%".length() * 8, 20, "Raise 5%"));
		addButton(new GuiButton(2, width / 2 - 70 + "Raise 5%".length() * 8 + 10, height / 2, "Lower 5%".length() * 8, 20, "Lower 5%"));
		addButton(new GuiButton(3, width / 2 - 70, height / 2 + 40, "Emergency Shutdown".length() * 8, 20, "Emergency Shutdown"));
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
		host.actionPerformed(button.id == 1 ? -5 : button.id == 2 ? 5 : 100, Side.CLIENT);
	}
}
