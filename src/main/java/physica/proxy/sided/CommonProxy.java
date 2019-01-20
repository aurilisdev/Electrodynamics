package physica.proxy.sided;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import physica.api.core.IContent;
import physica.api.core.IGuiInterface;

public class CommonProxy implements IGuiHandler, IContent {

	public static final int TILE_GUI_ID = 5000;
	public static final int ENTITY_GUI_ID = 5001;
	public static final int SLOT_GUI_ID = 5002;

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
		case TILE_GUI_ID:
			return world.getTileEntity(x, y, z) instanceof IGuiInterface ? ((IGuiInterface) world.getTileEntity(x, y, z)).getClientGuiElement(id, player) : null;
		case ENTITY_GUI_ID:
			return world.getEntityByID(x) instanceof IGuiInterface ? ((IGuiInterface) world.getEntityByID(x)).getClientGuiElement(id, player) : null;
		case SLOT_GUI_ID:
			ItemStack stack = player.inventory.getStackInSlot(x);
			return stack == null ? null : stack.getItem() instanceof IGuiInterface ? ((IGuiInterface) stack.getItem()).getClientGuiElement(id, player) : null;
		default:
			return null;
		}
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
		case TILE_GUI_ID:
			return world.getTileEntity(x, y, z) instanceof IGuiInterface ? ((IGuiInterface) world.getTileEntity(x, y, z)).getServerGuiElement(id, player) : null;
		case ENTITY_GUI_ID:
			return world.getEntityByID(x) instanceof IGuiInterface ? ((IGuiInterface) world.getEntityByID(x)).getServerGuiElement(id, player) : null;
		case SLOT_GUI_ID:
			ItemStack stack = player.inventory.getStackInSlot(x);
			return stack == null ? null : stack.getItem() instanceof IGuiInterface ? ((IGuiInterface) stack.getItem()).getServerGuiElement(id, player) : null;
		default:
			return null;
		}
	}

	public void onInit() {
	}

	public void onPostInit() {
	}

	public void onPreInit() {
	}

}
