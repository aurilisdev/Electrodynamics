package physica.core.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.CoreReferences;
import physica.core.common.inventory.ContainerElectricFurnace;
import physica.core.common.tile.TileElectricFurnace;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;

@SideOnly(Side.CLIENT)
public class GuiElectricFurnace extends GuiContainerBase<TileElectricFurnace> {

	public GuiElectricFurnace(EntityPlayer player, TileElectricFurnace host) {
		super(new ContainerElectricFurnace(player, host), host);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Inventory", 8, 73);
		drawString("Smelting:", 10, 28, 4210752);
		drawString("Battery:", 10, 53, 4210752);
		String displayText = "";
		if (host.getOperatingTicks() > 0) {
			displayText = "Smelting";
		} else {
			displayText = "Idle";
		}
		drawString("Status: " + displayText, 82, 47, 4210752);
		drawString(ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(TileElectricFurnace.POWER_USAGE * Math.pow(host.getBlockMetadata() + 1, 2), Unit.RF, Unit.WATT), Unit.WATT), 82, 58, 4210752);
		drawStringCentered(StatCollector.translateToLocal("tile." + CoreReferences.PREFIX + "electricFurnace." + host.getBlockMetadata() + ".gui"), xSize / 2, 5);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		renderFurnaceCookArrow(79, 25, host.getOperatingTicks(), TileElectricFurnace.TICKS_REQUIRED);
	}
}