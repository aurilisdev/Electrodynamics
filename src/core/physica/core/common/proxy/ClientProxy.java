package physica.core.common.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import physica.core.References;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta,
				new ModelResourceLocation(References.DOMAIN + ":" + id, "inventory"));
	}

	@Override
	public void registerBlockItemRenderer(Item item, int meta, String id, String state) {
		ModelLoader.setCustomModelResourceLocation(item, meta,
				new ModelResourceLocation(References.DOMAIN + ":" + id, "state=" + state));
	}

	@Override
	public EntityPlayer getPlayer(MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player;
		}
		return Minecraft.getMinecraft().player;
	}

	@Override
	public void addScheduledTask(Runnable runnable, IBlockAccess world) {
		if (world == null || !FMLCommonHandler.instance().getSide().isServer()) {
			Minecraft.getMinecraft().addScheduledTask(runnable);
		} else {
			super.addScheduledTask(runnable, world);
		}
	}
}
