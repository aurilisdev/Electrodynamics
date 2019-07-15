package physica.api.core.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public interface IGuiInterface extends IPlayerUsing {

	@SideOnly(Side.CLIENT)
	GuiScreen getClientGuiElement(int id, EntityPlayer player);

	Container getServerGuiElement(int id, EntityPlayer player);
}
