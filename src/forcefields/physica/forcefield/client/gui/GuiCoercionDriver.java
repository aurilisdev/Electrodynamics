package physica.forcefield.client.gui;

import java.awt.Rectangle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.IBaseUtilities;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.inventory.ContainerCoercionDriver;
import physica.forcefield.common.tile.TileCoercionDriver;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.inventory.tooltip.ToolTipTank;

@SideOnly(Side.CLIENT)
public class GuiCoercionDriver extends GuiContainerBase<TileCoercionDriver> implements IBaseUtilities {

	public GuiCoercionDriver(EntityPlayer player, TileCoercionDriver host) {
		super(new ContainerCoercionDriver(player, host), host);
		ySize += 51;
	}

	@Override
	public void initGui() {
		super.initGui();
		addToolTip(new ToolTipTank(new Rectangle(8, 115, electricityMeterWidth, electricityMeterHeight), "gui.coercionDriver.fortron_tank", host.getFortronTank()));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Transfer rate: " + host.getFortronTransferRate() / 1000.0 + "L/t", 8, 105);
		drawString("Linked Devices: " + host.getFortronConnections().size(), 8, 95);
		drawString("Usage: " + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(host.getEnergyUsage(), Unit.RF, Unit.WATT), Unit.WATT) + "/t", 8, 85);
		drawString("Status: " + (host.isActivated() ? "Active" : "Disabled"), 8, 75);
		drawString("Frequency: " + host.getFrequency(), 8, 65);

		drawStringCentered(StatCollector.translateToLocal("tile." + ForcefieldReferences.PREFIX + "coercionDriver.gui"), xSize / 2, 5);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		drawElectricity(8, 115, (float) host.getFortronTank().getFluidAmount() / host.getMaxEnergyStored());
	}
}
