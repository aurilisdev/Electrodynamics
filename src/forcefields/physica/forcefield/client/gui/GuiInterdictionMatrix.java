package physica.forcefield.client.gui;

import java.awt.Rectangle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.IBaseUtilities;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.inventory.ContainerInterdictionMatrix;
import physica.forcefield.common.tile.TileInterdictionMatrix;
import physica.library.client.gui.GuiContainerBase;
import physica.library.inventory.tooltip.ToolTipTank;

@SideOnly(Side.CLIENT)
public class GuiInterdictionMatrix extends GuiContainerBase<TileInterdictionMatrix> implements IBaseUtilities {

	public GuiInterdictionMatrix(EntityPlayer player, TileInterdictionMatrix host) {
		super(new ContainerInterdictionMatrix(player, host), host);
		ySize += 51;
	}

	@Override
	public void initGui() {
		super.initGui();
		addToolTip(new ToolTipTank(new Rectangle(8, 115, electricityMeterWidth, electricityMeterHeight), "gui.interdictionMatrix.fortron_tank", host.getFortronTank()));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Frequency: " + host.getFrequency(), 8, 93);
		drawString("Warn range: " + host.getWarnRange(), 8, 39);
		drawString("Action range: " + host.getActionRange(), 8, 49);
		drawString("Usage: " + host.getFortronUse() / 1000.0 + "L/t", 8, 108);
		drawStringCentered(StatCollector.translateToLocal("tile." + ForcefieldReferences.PREFIX + "interdictionMatrix.gui"), xSize / 2, 5);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		drawElectricity(8, 120, (float) host.getFortronTank().getFluidAmount() / host.getMaxFortron());
	}
}
