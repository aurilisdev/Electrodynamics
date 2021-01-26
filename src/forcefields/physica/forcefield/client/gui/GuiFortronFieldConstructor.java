package physica.forcefield.client.gui;

import java.awt.Rectangle;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.utilities.IBaseUtilities;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.inventory.ContainerFortronFieldConstructor;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.library.client.gui.GuiContainerBase;
import physica.library.inventory.tooltip.ToolTipTank;

@SideOnly(Side.CLIENT)
public class GuiFortronFieldConstructor extends GuiContainerBase<TileFortronFieldConstructor> implements IBaseUtilities {

	private static final DecimalFormat HEALTH_FORMAT = new DecimalFormat("#.##");

	static {
		HEALTH_FORMAT.setRoundingMode(RoundingMode.HALF_UP);
	}

	public GuiFortronFieldConstructor(EntityPlayer player, TileFortronFieldConstructor host) {
		super(new ContainerFortronFieldConstructor(player, host), host);
		ySize += 71;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		addToolTip(new ToolTipTank(new Rectangle(8, 115, electricityMeterWidth, electricityMeterHeight), "gui.fortronFieldConstructor.fortron_tank", host.getFortronTank()));
		addButton(new GuiButton(1, width / 2 - 80, height / 2 - 112, "Toggle".length() * 8, 20, "Toggle"));
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
		host.actionPerformed(button.id, Side.CLIENT);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Linked to: " + host.getFortronConnections().size(), 8, 40);
		drawString("Status: " + (host.isActivated() ? "Active" : "Disabled"), 8, 30);
		drawString("Usage: " + roundPrecise(host.getFortronUse() / 1000.0, 2) + "L/t", 8, 105);
		String stats = host.isFullySealed ? "Sealed" : host.isCurrentlyConstructing ? "Constructing" : host.isCalculating ? "Calculating" : host.getFortronTank().getFluidAmount() > host.getFortronUse() ? "Unsealed" : "Needs fortron";
		drawString(stats, 8, 92);
		drawString("Health: " + HEALTH_FORMAT.format((TileFortronFieldConstructor.MAX_HEALTH_LOSS - host.getHealthLost()) / (double) TileFortronFieldConstructor.MAX_HEALTH_LOSS * 100) + "%", 8, 130);
		drawString("Frequency: " + host.getFrequency(), 100, 103);
		drawStringCentered(StatCollector.translateToLocal("tile." + ForcefieldReferences.PREFIX + "fortronFieldConstructor.gui"), (int) (xSize / 1.65), 5);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		drawElectricity(8, 115, (float) host.getFortronTank().getFluidAmount() / host.getMaxFortron());
		drawElectricity(8, 140, (TileFortronFieldConstructor.MAX_HEALTH_LOSS - host.getHealthLost()) / TileFortronFieldConstructor.MAX_HEALTH_LOSS);
	}
}
