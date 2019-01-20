package physica.nuclear.client.gui;

import java.awt.Rectangle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.IBaseUtilities;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.inventory.tooltip.ToolTipTank;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.inventory.ContainerChemicalExtractor;
import physica.nuclear.common.tile.TileChemicalExtractor;

@SideOnly(Side.CLIENT)
public class GuiChemicalExtractor extends GuiContainerBase<TileChemicalExtractor> implements IBaseUtilities {

	public Rectangle AREA_WATER_TANK = new Rectangle(8, 18, meterWidth, meterHeight);

	public GuiChemicalExtractor(EntityPlayer player, TileChemicalExtractor host) {
		super(new ContainerChemicalExtractor(player, host), host);
	}

	@Override
	public void initGui() {
		super.initGui();
		addToolTip(new ToolTipTank(AREA_WATER_TANK, "gui.chemicalExtractor.water_tank", host.getTank()));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Usage: " + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(host.getEnergyUsage(), Unit.RF, Unit.WATT), Unit.WATT) + "/t", 86, 73);
		drawString("Inventory", 8, 73);
		drawStringCentered(StatCollector.translateToLocal("tile." + NuclearReferences.PREFIX + "chemicalExtractor.gui"), xSize / 2, 5);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		drawFluidTank(AREA_WATER_TANK.x, AREA_WATER_TANK.y, host.getTank());
		renderFurnaceCookArrow(36, 36, host.getOperatingTicks(), TileChemicalExtractor.TICKS_REQUIRED);
	}
}
