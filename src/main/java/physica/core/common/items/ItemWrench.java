package physica.core.common.items;

import appeng.api.implementations.items.IAEWrench;
import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IToolHammer;
import mekanism.api.IMekWrench;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import physica.CoreReferences;
import physica.core.common.CoreTabRegister;

public class ItemWrench extends Item implements IToolWrench, IToolHammer, IMekWrench, IAEWrench {

	public ItemWrench() {

		setUnlocalizedName("wrench");
		setTextureName(CoreReferences.PREFIX + "wrench");
		setMaxStackSize(1);
		setCreativeTab(CoreTabRegister.coreTab);
	}

	@Override
	public boolean canUseWrench(EntityPlayer player, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		return true;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		player.swingItem();
	}

	@Override
	public boolean isUsable(ItemStack itemStack, EntityLivingBase entity, int x, int y, int z) {
		return true;
	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase entity, int x, int y, int z) {
		entity.swingItem();
	}

	@Override
	public boolean canWrench(ItemStack wrench, EntityPlayer player, int x, int y, int z) {
		return true;
	}

}
