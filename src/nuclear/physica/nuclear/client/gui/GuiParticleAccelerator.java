package physica.nuclear.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.IBaseUtilities;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.inventory.ContainerParticleAccelerator;
import physica.nuclear.common.tile.TileParticleAccelerator;

@SideOnly(Side.CLIENT)
public class GuiParticleAccelerator extends GuiContainerBase<TileParticleAccelerator> implements IBaseUtilities {

	public GuiParticleAccelerator(EntityPlayer player, TileParticleAccelerator host) {
		super(new ContainerParticleAccelerator(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Velocity: " + roundPrecise((double) (host.getParticleVelocity() / ConfigNuclearPhysics.ANTIMATTER_CREATION_SPEED * 100.0F), 1) + "%", 8, 19);
		drawString("Status: " + host.getAcceleratorStatus().name(), 8, 30);
		drawString("Usage: " + ElectricityDisplay.getDisplayShortTicked(ElectricityUtilities.convertEnergy(host.getEnergyUsage(), Unit.RF, Unit.WATT), Unit.WATT), 8, 41);
		drawString("Antimatter: " + host.getAntimatterAmount() + " mg", 8, ySize - 96 + 2, 4210752);
		drawString("Used: " + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy((host.getSessionUse() * host.getCurrentSessionTicks()) / 72000.0, Unit.RF, Unit.WATT), Unit.WATT) + "h", 8, 52);
		drawStringCentered(StatCollector.translateToLocal("tile." + NuclearReferences.PREFIX + "accelerator.gui"), xSize / 2, 5);
	}
}
