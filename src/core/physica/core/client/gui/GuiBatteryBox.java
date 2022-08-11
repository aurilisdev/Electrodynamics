package physica.core.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.CoreReferences;
import physica.api.core.abstraction.Face;
import physica.core.common.inventory.ContainerBatteryBox;
import physica.core.common.tile.TileBatteryBox;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;

@SideOnly(Side.CLIENT)
public class GuiBatteryBox extends GuiContainerBase<TileBatteryBox> {

	public GuiBatteryBox(EntityPlayer player, TileBatteryBox host) {
		super(new ContainerBatteryBox(player, host), host);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Inventory", 8, 73);
		String display = ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy((double) host.getElectricityStored(Face.UNKNOWN), Unit.RF, Unit.WATTHOUR), Unit.WATTHOUR) + " of";
		int layer2X = 74;
		drawString(display, layer2X, 30, 4210752);
		drawString(ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy((double) host.getElectricCapacity(Face.UNKNOWN), Unit.RF, Unit.WATTHOUR), Unit.WATTHOUR), layer2X, 40, 4210752);
		drawString("Transfer: " + ElectricityDisplay.getDisplayShort(host.getElectricCapacity(Face.UNKNOWN) / 500, Unit.WATT), layer2X, 52);

		drawString("Input:", 7, 28);
		drawString("Output:", 7, 52);
		drawStringCentered(StatCollector.translateToLocal("tile." + CoreReferences.PREFIX + "batteryBox." + host.getBlockMetadata() + ".gui"), xSize / 2, 5);
	}

}
