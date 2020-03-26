package physica.core.common.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id) {
	}

	public void registerBlockItemRenderer(Item item, int meta, String id, String state) {
	}

	public EntityPlayer getPlayer(MessageContext context) {
		return context.getServerHandler().player;
	}

	public void addScheduledTask(Runnable runnable, IBlockAccess world) {
		((WorldServer) world).addScheduledTask(runnable);
	}
}
