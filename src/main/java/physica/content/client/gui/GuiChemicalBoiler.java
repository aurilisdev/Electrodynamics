package physica.content.client.gui;

import java.awt.Rectangle;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.References;
import physica.api.energy.ElectricityDisplay;
import physica.api.energy.ElectricityUtilities;
import physica.api.energy.base.Unit;
import physica.api.lib.IBaseUtilities;
import physica.api.lib.inventory.GuiContainerBase;
import physica.api.lib.inventory.tooltip.ToolTipTank;
import physica.content.common.inventory.ContainerChemicalBoiler;
import physica.content.common.tile.TileCentrifuge;
import physica.content.common.tile.TileChemicalBoiler;

public class GuiChemicalBoiler extends GuiContainerBase<TileChemicalBoiler> implements IBaseUtilities {
	public Rectangle	AREA_WATER_TANK	= new Rectangle(8, 18, meterWidth, meterHeight);
	public Rectangle	AREA_HEX_TANK	= new Rectangle(xSize - 8 - meterWidth, 18, meterWidth, meterHeight);

	public GuiChemicalBoiler(EntityPlayer player, TileChemicalBoiler host) {
		super(new ContainerChemicalBoiler(player, host), host);
	}

	@Override
	public void initGui() {
		super.initGui();
		addToolTip(new ToolTipTank(AREA_WATER_TANK, "gui.centrifuge.water_tank", host.getWaterTank()));
		addToolTip(new ToolTipTank(AREA_HEX_TANK, "gui.centrifuge.hex_tank", host.getHexTank()));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Usage: " + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(host.getEnergyUsage(), Unit.RF, Unit.WATT), Unit.WATT) + "/t", 86, 73);
		drawString("Inventory", 8, 73);
		drawStringCentered(StatCollector.translateToLocal("tile." + References.PREFIX + "chemicalBoiler.gui"), xSize / 2, 5);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		drawFluidTank(AREA_WATER_TANK.x, AREA_WATER_TANK.y, host.getWaterTank());
		drawFluidTank(AREA_HEX_TANK.x, AREA_HEX_TANK.y, host.getHexTank());
		renderFurnaceCookArrow(30, 36, Math.min(TileCentrifuge.TICKS_REQUIRED / 2, host.getOperatingTicks()), TileCentrifuge.TICKS_REQUIRED / 2);
		renderFurnaceCookArrow(118, 36, Math.max(0, host.getOperatingTicks() - TileCentrifuge.TICKS_REQUIRED / 2), TileCentrifuge.TICKS_REQUIRED / 2);
	}
}
