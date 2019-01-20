package physica.content.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import physica.References;
import physica.api.energy.ElectricityDisplay;
import physica.api.energy.ElectricityUtilities;
import physica.api.energy.base.Unit;
import physica.api.lib.inventory.GuiContainerBase;
import physica.content.common.inventory.ContainerAssembler;
import physica.content.common.tile.TileAssembler;

public class GuiAssembler extends GuiContainerBase<TileAssembler> {
	public static final ResourceLocation GUI_ASSEMBLER = new ResourceLocation(References.DOMAIN, References.GUI_TEXTURE_DIRECTORY + "gui_assembler.png");

	public GuiAssembler(EntityPlayer player, TileAssembler host) {
		super(new ContainerAssembler(player, host), host);
		ySize = 230;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		drawString("Usage: " + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(host.getEnergyUsage(), Unit.RF, Unit.WATT), Unit.WATT) + "/t", 6, 6);
		fontRendererObj.drawString("Progress: " + (int) ((float) host.getOperatingTicks() / (float) TileAssembler.TICKS_REQUIRED * 100) + "%", xSize / 2 - 80, ySize - 106, 0x404040);
		drawString("Inventory", 8, 73 + 230 - defaultYSize);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		containerWidth = (width - xSize) / 2;
		containerHeight = (height - ySize) / 2;

		mc.renderEngine.bindTexture(GUI_ASSEMBLER);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		drawTexturedModalRect(containerWidth, containerHeight, 0, 0, xSize, ySize);

		preDrawContainerSlots();
		drawContainerSlots();
	}
}
