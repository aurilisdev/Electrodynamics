package physica.nuclear.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.base.Unit;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.inventory.ContainerRadioisotopeGenerator;
import physica.nuclear.common.tile.TileRadioisotopeGenerator;

@SideOnly(Side.CLIENT)
public class GuiRadioisotopeGenerator extends GuiContainerBase<TileRadioisotopeGenerator> {

	public GuiRadioisotopeGenerator(EntityPlayer player, TileRadioisotopeGenerator host) {
		super(new ContainerRadioisotopeGenerator(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString(host.generate > 0 ? "Generating" : "Not Generating", 70, 33, 4210752);
		drawString(ElectricityDisplay.getDisplay(host.generate, Unit.WATT), 70, 45);
		drawString("Inventory", 8, 73);
		drawStringCentered(StatCollector.translateToLocal("tile." + NuclearReferences.PREFIX + "radioisotopeGenerator.gui"), xSize / 2, 5);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
	}
}
