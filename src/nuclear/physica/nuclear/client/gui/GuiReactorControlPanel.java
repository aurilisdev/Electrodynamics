package physica.nuclear.client.gui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.utilities.IBaseUtilities;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.inventory.ContainerBase;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.tile.TileFissionReactor;
import physica.nuclear.common.tile.TileReactorControlPanel;

@SideOnly(Side.CLIENT)
public class GuiReactorControlPanel extends GuiContainerBase<TileReactorControlPanel> implements IBaseUtilities {

	public GuiReactorControlPanel(EntityPlayer player, TileReactorControlPanel host) {
		super(new ContainerBase<>(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		drawStringCentered(StatCollector.translateToLocal("tile." + NuclearReferences.PREFIX + "reactorControlPanel.gui"), xSize / 2, 5);
		if (host.reactor == null || host.reactor.isInvalid())
		{
			drawStringCentered("Invalid Reactor", xSize / 2, 35);
		} else
		{
			double ticksLeft = 0;
			if (host.reactor.hasFuelRod())
			{
				ticksLeft = (host.reactor.getStackInSlot(TileFissionReactor.SLOT_INPUT).getMaxDamage() - host.reactor.getStackInSlot(TileFissionReactor.SLOT_INPUT).getItemDamage())
						/ (1 + Math.round(host.reactor.getTemperature() / (TileFissionReactor.MELTDOWN_TEMPERATURE / 2.0f)));
			}
			float temperature = host.reactor.getTemperature();

			drawString("Temperature: " + roundPrecise(temperature, 2) + " C", 9, 39);
			drawString("Time Left: " + roundPrecise(ticksLeft / 20, 1) + " seconds", 9, 50);

			drawString("Rod Insertion: " + (host.rod != null && !host.rod.isInvalid() ? host.rod.getInsertion() : host.reactor.getInsertion()) + "%", 9, 61);
			int productionFlux = 0;
			if (host.reactor.getTemperature() > 100)
			{
				float steam = (temperature - 100) / 10 * 0.65f * 20 * 20 * (TileFissionReactor.STEAM_GEN_DIAMETER * TileFissionReactor.STEAM_GEN_DIAMETER);
				productionFlux = (int) (steam * ConfigNuclearPhysics.TURBINE_STEAM_TO_RF_RATIO / 4.0);
			}
			drawString("Theoretical Max", 9, 82);
			drawString("Power Production: " + ElectricityDisplay.getDisplayShortTicked(ElectricityUtilities.convertEnergy(productionFlux, Unit.RF, Unit.WATT), Unit.WATT), 9, 93);
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();
		if (host.rod != null && !host.rod.isInvalid())
		{
			addButton(new GuiButton(1, width / 2 - 70, height / 2 + 25, "Raise 5%".length() * 8, 20, "Raise 5%"));
			addButton(new GuiButton(2, width / 2 - 70 + "Raise 5%".length() * 8 + 10, height / 2 + 25, "Lower 5%".length() * 8, 20, "Lower 5%"));
			addButton(new GuiButton(3, width / 2 - 70, height / 2 + 50, "Emergency Shutdown".length() * 8, 20, "Emergency Shutdown"));
		}
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
		if (host.rod != null && !host.rod.isInvalid())
		{
			host.rod.actionPerformed(button.id == 1 ? -5 : button.id == 2 ? 5 : 100, Side.CLIENT);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawTexturedModalRect(containerWidth + (xSize - electricityMeterWidth) / 2, containerHeight + 18, 54, 0, electricityMeterWidth, electricityMeterHeight);
		if (host.reactor != null)
		{
			if (host.reactor.getTemperature() > 0)
			{
				drawGradientRect(
						(int) (containerWidth + (xSize - electricityMeterWidth) / 2 + Math.min(host.reactor.getTemperature(), TileFissionReactor.MELTDOWN_TEMPERATURE) / TileFissionReactor.MELTDOWN_TEMPERATURE * electricityMeterWidth - 1),
						containerHeight + 18 + electricityMeterHeight - 1, containerWidth + (xSize - electricityMeterWidth) / 2 + 1, containerHeight + 18 + 1,
						host.reactor.getTemperature() < TileFissionReactor.MELTDOWN_TEMPERATURE ? Color.yellow.getRGB() : Color.red.getRGB(), Color.red.getRGB());
			}
		}
	}
}
