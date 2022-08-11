package physica.nuclear.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import physica.api.core.utilities.IBaseUtilities;
import physica.library.client.gui.GuiContainerBase;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.inventory.ContainerNeutronCaptureChamber;
import physica.nuclear.common.tile.TileNeutronCaptureChamber;

@SideOnly(Side.CLIENT)
public class GuiNeutronCaptureChamber extends GuiContainerBase<TileNeutronCaptureChamber> implements IBaseUtilities {

	public GuiNeutronCaptureChamber(EntityPlayer player, TileNeutronCaptureChamber host) {
		super(new ContainerNeutronCaptureChamber(player, host), host);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Progress: " + (int) (host.getOperatingTicks() * 100 / TileNeutronCaptureChamber.TICKS_REQUIRED) + "%", 86, 73);
		ItemStack input = host.getStackInSlot(TileNeutronCaptureChamber.SLOT_INPUT);
		ItemStack output = host.getStackInSlot(TileNeutronCaptureChamber.SLOT_OUTPUT);
		drawString("Status: " + (host.canProcess() ? "Running" : input == null ? "Invalid input" : output != null && output.stackSize < output.getMaxStackSize() ? "Too cold reactor" : "Invalid output"), 8, 61);
		drawString("Inventory", 8, 73);
		drawStringCentered(StatCollector.translateToLocal("tile." + NuclearReferences.PREFIX + "neutronCaptureChamber.gui"), xSize / 2, 5);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		renderFurnaceCookArrow(38 + (118 - 38) / 2 - 3, 36, host.getOperatingTicks(), TileNeutronCaptureChamber.TICKS_REQUIRED);
	}
}
