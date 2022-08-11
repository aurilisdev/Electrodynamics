package physica.forcefield.common.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.ForcefieldTabRegister;

public class ItemIdentificationCard extends Item implements ICardIdentification {

	private static final String NBT_PERM_PREFIX = "mffs_permission_";

	public ItemIdentificationCard(String name) {
		setUnlocalizedName(name);
		setTextureName(ForcefieldReferences.PREFIX + name.toLowerCase());
		setCreativeTab(ForcefieldTabRegister.forcefieldTab);
		setMaxStackSize(1);
	}

	@Override
	public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase hitter)
	{
		if (target instanceof EntityPlayer)
		{
			setUsername(itemStack, target.getCommandSenderName());
			setUniqueId(itemStack, target.getUniqueID());
			if (hitter instanceof EntityPlayer)
			{
				notifyIdentificationChange((EntityPlayer) hitter, target.getCommandSenderName());
			}
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
	{
		if (getUsername(itemStack) != null && !getUsername(itemStack).isEmpty())
		{
			info.add("Username: " + getUsername(itemStack));
		} else
		{
			info.add("Unidentified");
		}
		String tooltip = "";
		boolean isFirst = true;
		for (Permission permission : Permission.getPermissions())
		{
			if (hasPermission(itemStack, permission))
			{
				if (!isFirst)
				{
					tooltip = tooltip + ", ";
				}
				isFirst = false;
				tooltip = tooltip + permission.name;
			}
		}
		if (!isFirst)
		{
			info.add(tooltip);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		setUsername(itemStack, entityPlayer.getCommandSenderName());
		setUniqueId(itemStack, entityPlayer.getUniqueID());
		if (!world.isRemote)
		{
			notifyIdentificationChange(entityPlayer, entityPlayer.getCommandSenderName());
		}
		return itemStack;
	}

	public void notifyIdentificationChange(EntityPlayer sender, String username)
	{
		sender.addChatMessage(new ChatComponentText("Identification card linked to player: " + username));
	}

	@Override
	public void setUsername(ItemStack itemStack, String username)
	{
		NBTTagCompound nbtTagCompound = getSafeTagCompound(itemStack);
		nbtTagCompound.setString("username", username);
	}

	@Override
	public void setUniqueId(ItemStack itemStack, UUID uniqueId)
	{
		NBTTagCompound nbtTagCompound = getSafeTagCompound(itemStack);
		nbtTagCompound.setLong("uuid_least", uniqueId.getLeastSignificantBits());
		nbtTagCompound.setLong("uuid_most", uniqueId.getMostSignificantBits());
	}

	@Override
	public String getUsername(ItemStack itemStack)
	{
		NBTTagCompound nbtTagCompound = getSafeTagCompound(itemStack);
		if (nbtTagCompound != null)
		{
			if (!nbtTagCompound.getString("username").isEmpty())
			{
				return nbtTagCompound.getString("username");
			}
		}
		return null;
	}

	@Override
	public UUID getUniqueId(ItemStack itemStack)
	{
		NBTTagCompound nbtTagCompound = getSafeTagCompound(itemStack);
		if (nbtTagCompound != null)
		{
			long least = nbtTagCompound.getLong("uuid_least");
			long most = nbtTagCompound.getLong("uuid_most");
			if (least != 0 && most != 0)
			{
				return new UUID(most, least);
			}
		}
		return null;
	}

	@Override
	public boolean hasPermission(ItemStack itemStack, Permission permission)
	{
		NBTTagCompound nbt = getSafeTagCompound(itemStack);
		if (permission == null || nbt == null)
		{
			return true;
		}
		return nbt.getBoolean(NBT_PERM_PREFIX + permission.id);
	}

	@Override
	public boolean addPermission(ItemStack itemStack, Permission permission)
	{
		NBTTagCompound nbt = getSafeTagCompound(itemStack);
		if (permission == null || nbt == null)
		{
			return false;
		}
		nbt.setBoolean(NBT_PERM_PREFIX + permission.id, true);
		return false;
	}

	@Override
	public boolean removePermission(ItemStack itemStack, Permission permission)
	{
		NBTTagCompound nbt = getSafeTagCompound(itemStack);
		if (permission == null || nbt == null)
		{
			return false;
		}
		nbt.setBoolean(NBT_PERM_PREFIX + permission.id, false);
		return false;
	}

	public NBTTagCompound getSafeTagCompound(ItemStack itemStack)
	{
		if (itemStack != null)
		{
			if (itemStack.getTagCompound() == null)
			{
				itemStack.setTagCompound(new NBTTagCompound());
			}
			return itemStack.getTagCompound();
		}
		return null;
	}
}
