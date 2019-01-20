package physica.api.base;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public interface IGuiInterface extends IPlayerUsing {
	GuiScreen getClientGuiElement(int id, EntityPlayer player);

	Container getServerGuiElement(int id, EntityPlayer player);
}
