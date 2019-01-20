package physica.content.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.References;
import physica.api.energy.ElectricityDisplay;
import physica.api.energy.ElectricityUtilities;
import physica.api.energy.base.Unit;
import physica.api.lib.IBaseUtilities;
import physica.api.lib.inventory.GuiContainerBase;
import physica.content.common.configuration.ConfigMain;
import physica.content.common.inventory.ContainerAccelerator;
import physica.content.common.tile.TileAccelerator;

public class GuiAccelerator extends GuiContainerBase<TileAccelerator> implements IBaseUtilities {

	public GuiAccelerator(EntityPlayer player, TileAccelerator host) {
		super(new ContainerAccelerator(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Velocity: " + roundPrecise((double) (host.getParticleVelocity() / ConfigMain.antimatterCreationSpeed * 100.0F), 1) + "%", 8, 19);
		drawString("Status: " + host.getAcceleratorStatus().name(), 8, 30);
		drawString("Usage: " + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(host.getEnergyUsage(), Unit.RF, Unit.WATT), Unit.WATT) + "/t", 8, 41);

		drawString("Antimatter: " + host.getAntimatterAmount() + " mg", 8, ySize - 96 + 2, 4210752);
		drawString("Used: " + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(host.getSessionUse(), Unit.RF, Unit.WATT), Unit.WATT), 8, 52);
		drawStringCentered(StatCollector.translateToLocal("tile." + References.PREFIX + "accelerator.gui"), xSize / 2, 5);
	}
}
