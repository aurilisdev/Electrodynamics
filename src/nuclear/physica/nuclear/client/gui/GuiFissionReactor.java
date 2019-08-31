 package physica.nuclear.client.gui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.utilities.IBaseUtilities;
import physica.library.client.gui.GuiContainerBase;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.inventory.ContainerFissionReactor;
import physica.nuclear.common.tile.TileFissionReactor;

@SideOnly(Side.CLIENT)
public class GuiFissionReactor extends GuiContainerBase<TileFissionReactor> implements IBaseUtilities {

	public GuiFissionReactor(EntityPlayer player, TileFissionReactor host) {
		super(new ContainerFissionReactor(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		double ticksLeft = 0;
		if (host.hasFuelRod())
		{
			ticksLeft = (host.getStackInSlot(TileFissionReactor.SLOT_INPUT).getMaxDamage() - host.getStackInSlot(TileFissionReactor.SLOT_INPUT).getItemDamage())
					/ (+1 + Math.round(host.getTemperature() / (TileFissionReactor.MELTDOWN_TEMPERATURE / 2.0f)));
		}
		drawString("Temperature: " + roundPrecise(host.getTemperature(), 2) + " C", 9, 59);
		drawString("Time Left: " + roundPrecise(ticksLeft / 20, 1) + " seconds", 9, 70);

		drawStringCentered(StatCollector.translateToLocal("tile." + NuclearReferences.PREFIX + "fissionReactor.gui"), xSize / 2, 5);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawTexturedModalRect(containerWidth + (xSize - electricityMeterWidth) / 2, containerHeight + 18, 54, 0, electricityMeterWidth, electricityMeterHeight);
		if (host.getTemperature() > 0)
		{
			drawGradientRect((int) (containerWidth + (xSize - electricityMeterWidth) / 2 + Math.min(host.getTemperature(), TileFissionReactor.MELTDOWN_TEMPERATURE) / TileFissionReactor.MELTDOWN_TEMPERATURE * electricityMeterWidth - 1),
					containerHeight + 18 + electricityMeterHeight - 1, containerWidth + (xSize - electricityMeterWidth) / 2 + 1, containerHeight + 18 + 1,
					host.getTemperature() < TileFissionReactor.MELTDOWN_TEMPERATURE ? Color.yellow.getRGB() : Color.red.getRGB(), Color.red.getRGB());
		}
	}
}
