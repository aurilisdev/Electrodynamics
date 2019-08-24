package physica.core.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.CoreReferences;
import physica.core.common.inventory.ContainerCircuitPress;
import physica.core.common.tile.TileCircuitPress;
import physica.library.client.gui.GuiContainerBase;

@SideOnly(Side.CLIENT)
public class GuiCircuitPress extends GuiContainerBase<TileCircuitPress> {

	public GuiCircuitPress(EntityPlayer player, TileCircuitPress host) {
		super(new ContainerCircuitPress(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Inventory", 8, 73);
		drawStringCentered(StatCollector.translateToLocal("tile." + CoreReferences.PREFIX + "circuitPress.gui"), xSize / 2, 5);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);

		renderFurnaceCookArrow(80, 35, host.getOperatingTicks(), TileCircuitPress.REQUIRED_TICKS);
	}
}
