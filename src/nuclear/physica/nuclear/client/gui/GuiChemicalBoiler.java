package physica.nuclear.client.gui;

import java.awt.Rectangle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.utilities.IBaseUtilities;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.inventory.tooltip.ToolTipTank;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.inventory.ContainerChemicalBoiler;
import physica.nuclear.common.tile.TileChemicalBoiler;

@SideOnly(Side.CLIENT)
public class GuiChemicalBoiler extends GuiContainerBase<TileChemicalBoiler> implements IBaseUtilities {

	public Rectangle	AREA_WATER_TANK	= new Rectangle(8, 18, meterWidth, meterHeight);
	public Rectangle	AREA_HEX_TANK	= new Rectangle(xSize - 8 - meterWidth, 18, meterWidth, meterHeight);

	public GuiChemicalBoiler(EntityPlayer player, TileChemicalBoiler host) {
		super(new ContainerChemicalBoiler(player, host), host);
		ySize += 10;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		addToolTip(new ToolTipTank(AREA_WATER_TANK, "gui.chemicalBoiler.water_tank", host.getWaterTank()));
		addToolTip(new ToolTipTank(AREA_HEX_TANK, "gui.chemicalBoiler.hex_tank", host.getHexTank()));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Status: " + (host.getOperatingTicks() > 1 ? "Running" : host.getOperatingTicks() == 1 ? "Starting" : host.canProcess() ? "Insufficient power" : "Invalid input"), 8, 73);
		drawString("Usage: " + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(host.getEnergyUsage(), Unit.RF, Unit.WATT), Unit.WATT), 8, 83);
		drawStringCentered(StatCollector.translateToLocal("tile." + NuclearReferences.PREFIX + "chemicalBoiler.gui"), xSize / 2, 5);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		drawFluidTank(AREA_WATER_TANK.x, AREA_WATER_TANK.y, host.getWaterTank());
		drawFluidTank(AREA_HEX_TANK.x, AREA_HEX_TANK.y, host.getHexTank());
		renderFurnaceCookArrow(30, 36, Math.min(TileChemicalBoiler.TICKS_REQUIRED / 2, host.getOperatingTicks()), TileChemicalBoiler.TICKS_REQUIRED / 2);
		renderFurnaceCookArrow(118, 36, Math.max(0, host.getOperatingTicks() - TileChemicalBoiler.TICKS_REQUIRED / 2), TileChemicalBoiler.TICKS_REQUIRED / 2);
	}
}
