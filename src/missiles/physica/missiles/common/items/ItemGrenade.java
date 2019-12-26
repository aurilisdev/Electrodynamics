package physica.missiles.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import physica.library.item.ItemDescriptable;
import physica.missiles.MissileReferences;
import physica.missiles.common.MissileTabRegister;
import physica.missiles.common.entity.EntityGrenade;
import physica.missiles.common.explosive.Explosive;

public class ItemGrenade extends ItemDescriptable {

	public static final IIcon[]	ICONS			= new IIcon[256];
	private static final int	firingDelay		= 1000;
	private HashMap<UUID, Long>	clickTimePlayer	= new HashMap<>();

	public ItemGrenade() {
		super("grenades", "grenade");
		setMaxStackSize(16);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(MissileTabRegister.missilesTab);
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 3 * 20;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if (itemStack != null)
		{
			entityPlayer.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
		}

		return itemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer entityPlayer, int itemInUseCount)
	{
		if (!world.isRemote)
		{
			long clickMs = System.currentTimeMillis();
			if (clickTimePlayer.containsKey(entityPlayer.getUniqueID()))
			{
				if (clickMs - clickTimePlayer.get(entityPlayer.getUniqueID()) < (entityPlayer.capabilities.isCreativeMode ? firingDelay / 2 : firingDelay))
				{
					return;
				}
			}
			Explosive explosive = Explosive.get(itemStack.getItemDamage());
			if (!entityPlayer.capabilities.isCreativeMode)
			{
				itemStack.stackSize--;

				if (itemStack.stackSize <= 0)
				{
					entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
				}
			}

			world.playSoundAtEntity(entityPlayer, "game.tnt.primed", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			world.spawnEntityInWorld(new EntityGrenade(world, entityPlayer, explosive.getId(), (float) (getMaxItemUseDuration(itemStack) - itemInUseCount) / (float) getMaxItemUseDuration(itemStack)));
			clickTimePlayer.put(entityPlayer.getUniqueID(), clickMs);
		}
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return getUnlocalizedName() + "." + Explosive.get(itemstack.getItemDamage()).localeName;
	}

	@Override
	public String getUnlocalizedName()
	{
		return "physicaMissiles.grenade";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		for (Explosive ex : Explosive.explosiveSet)
		{
			if (ex.hasGrenade)
			{
				ICONS[ex.getId()] = iconRegister.registerIcon(MissileReferences.PREFIX + "grenades/grenade." + ex.localeName);
			}
		}
	}

	@Override
	public IIcon getIconFromDamage(int i)
	{
		return ICONS[i];
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, @SuppressWarnings("rawtypes") List par3List)
	{
		for (Explosive ex : Explosive.explosiveSet)
		{
			if (ex.hasGrenade)
			{
				par3List.add(new ItemStack(par1, 1, ex.getId()));
			}
		}
	}
}
