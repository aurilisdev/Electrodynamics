package physica.nuclear.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import physica.CoreReferences;
import physica.library.client.gui.GuiContainerBase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.nuclear.common.inventory.ContainerQuantumAssembler;
import physica.nuclear.common.tile.TileQuantumAssembler;

@SideOnly(Side.CLIENT)
public class GuiQuantumAssembler extends GuiContainerBase<TileQuantumAssembler> {

	public static final ResourceLocation GUI_ASSEMBLER = new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.GUI_TEXTURE_DIRECTORY + "gui_assembler.png");

	public GuiQuantumAssembler(EntityPlayer player, TileQuantumAssembler host) {
		super(new ContainerQuantumAssembler(player, host), host);
		ySize = 230;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		drawString("Usage: " + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(host.getElectricityUsage(), Unit.RF, Unit.WATT), Unit.WATT), 6, 6);
		fontRendererObj.drawString("Progress: " + (int) ((float) host.getOperatingTicks() / TileQuantumAssembler.TICKS_REQUIRED * 100) + "%", xSize / 2 - 80, ySize - 106, 0x404040);
		drawString("Inventory", 8, 73 + 230 - defaultYSize);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		containerWidth = (width - xSize) / 2;
		containerHeight = (height - ySize) / 2;

		mc.renderEngine.bindTexture(GUI_ASSEMBLER);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		drawTexturedModalRect(containerWidth, containerHeight, 0, 0, xSize, ySize);

		preDrawContainerSlots();
		drawContainerSlots();
	}
}
