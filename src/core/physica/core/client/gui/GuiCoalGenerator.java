package physica.core.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.CoreReferences;
import physica.core.common.inventory.ContainerCoalGenerator;
import physica.core.common.tile.TileCoalGenerator;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.base.Unit;

@SideOnly(Side.CLIENT)
public class GuiCoalGenerator extends GuiContainerBase<TileCoalGenerator> {

	public GuiCoalGenerator(EntityPlayer player, TileCoalGenerator host) {
		super(new ContainerCoalGenerator(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String displayText = "";
		if (host.generate <= 0.0D) {
			displayText = "Not Generating";
		} else if (host.generate < 100.0D) {
			displayText = "Required Heat: " + (int) (host.generate / 100.0D * 100.0D) + "%";
		} else {
			drawString("Generating", 70, 33, 4210752);
			displayText = ElectricityDisplay.getDisplay(host.generate - 100, Unit.WATT);
		}
		drawString("Time Left: " + host.itemCookTime / 20 + "s", 70, 45);
		drawString(displayText, 70, 57);
		drawString("Inventory", 8, 73);
		drawStringCentered(StatCollector.translateToLocal("tile." + CoreReferences.PREFIX + "coalGenerator.gui"), xSize / 2, 5);
	}

}
