package physica.core.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import physica.CoreReferences;
import physica.core.common.inventory.ContainerBlastFurnace;
import physica.core.common.tile.TileBlastFurnace;
import physica.library.client.gui.GuiContainerBase;

@SideOnly(Side.CLIENT)
public class GuiBlastFurnace extends GuiContainerBase<TileBlastFurnace> {

	private static final ResourceLocation GUI_FURNACE = new ResourceLocation("textures/gui/container/furnace.png");

	public GuiBlastFurnace(EntityPlayer player, TileBlastFurnace host) {
		super(new ContainerBlastFurnace(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Inventory", 8, 73);
		drawStringCentered(StatCollector.translateToLocal("tile." + CoreReferences.PREFIX + "blastFurnace.gui"), xSize / 2, 5);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		containerWidth = (width - xSize) / 2;
		containerHeight = (height - ySize) / 2;
		mc.renderEngine.bindTexture(GUI_FURNACE);
		drawTexturedModalRect(containerWidth, containerHeight, 0, 0, xSize, ySize);
		if (host.isBurning()) {
			int i1 = host.getBurnTimeRemainingScaled(13);
			drawTexturedModalRect(containerWidth + 56, containerHeight + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
		}
		mc.renderEngine.bindTexture(GUI_COMPONENTS);

		renderFurnaceCookArrow(80, 35, host.getCookProgressScaled(TileBlastFurnace.TOTAL_BURN_TIME), TileBlastFurnace.TOTAL_BURN_TIME);
	}
}
