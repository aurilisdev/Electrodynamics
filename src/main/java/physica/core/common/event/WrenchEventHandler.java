package physica.core.common.event;

import appeng.api.implementations.items.IAEWrench;
import buildcraft.api.tools.IToolWrench;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mekanism.api.IMekWrench;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import physica.core.common.block.BlockCopperCable;
import physica.library.block.BlockBaseContainer;

public class WrenchEventHandler {

	public static final WrenchEventHandler INSTANCE = new WrenchEventHandler();

	public static boolean canWrench(ItemStack item, int x, int y, int z, EntityPlayer player)
	{
		if (item != null)
		{
			Item type = item.getItem();
			if (type instanceof IMekWrench)
			{
				if (!((IMekWrench) type).canUseWrench(player, x, y, z))
				{
					return false;
				} else if (!(type instanceof IToolWrench) && !(type instanceof IAEWrench))
				{
					return true;
				}
			}
			if (type instanceof IToolWrench)
			{
				if (((IToolWrench) type).canWrench(player, x, y, z))
				{
					((IToolWrench) type).wrenchUsed(player, x, y, z);
					return true;
				}
			}
			if (type instanceof IAEWrench)
			{
				if (((IAEWrench) type).canWrench(item, player, x, y, z))
				{
					return true;
				}
			}
		}
		return false;
	}

	@SubscribeEvent
	public void onClick(PlayerInteractEvent event)
	{
		if (event.action == Action.RIGHT_CLICK_BLOCK)
		{
			if (event.entityPlayer.isSneaking())
			{
				if (canWrench(event.entityPlayer.getCurrentEquippedItem(), event.x, event.y, event.z, event.entityPlayer))
				{
					Block block = event.world.getBlock(event.x, event.y, event.z);
					if (block instanceof BlockBaseContainer || block instanceof BlockCopperCable)
					{
						if (!event.world.isRemote)
						{
							if (((BlockBaseContainer) block).canWrench(event.world, event.x, event.y, event.z))
							{
								block.breakBlock(event.world, event.x, event.y, event.z, block, event.world.getBlockMetadata(event.x, event.y, event.z));
								block.dropBlockAsItem(event.world, event.x, event.y, event.z, 0, 0);
								event.world.setBlockToAir(event.x, event.y, event.z);
							}
						}
						event.entityPlayer.swingItem();
					}
				}
			}
		}
	}
}
