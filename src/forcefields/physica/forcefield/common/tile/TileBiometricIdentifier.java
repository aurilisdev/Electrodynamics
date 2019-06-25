package physica.forcefield.common.tile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTank;
import physica.api.core.IGuiInterface;
import physica.api.core.ITileBase;
import physica.api.forcefield.IInvFortronTile;
import physica.forcefield.client.gui.GuiBiometricIdentifier;
import physica.forcefield.common.ForcefieldFluidRegister;
import physica.forcefield.common.inventory.ContainerBiometricIndentifier;
import physica.forcefield.common.item.ItemIdentificationCard;
import physica.forcefield.common.item.Permission;
import physica.library.location.BlockLocation;
import physica.library.network.IPacket;
import physica.library.network.netty.PacketSystem;
import physica.library.network.packet.PacketTile;
import physica.library.tile.TileBaseContainer;

public class TileBiometricIdentifier extends TileBaseContainer implements IGuiInterface, IInvFortronTile {

	public static final int BIOMETRIC_IDENTIFIER_PACKET_ID = 10;
	public static final int SLOT_INPUT_CARD = 0;
	public static final int SLOT_MASTER_CARD = 1;
	protected FluidTank emptyFortronTank = new FluidTank(ForcefieldFluidRegister.LIQUID_FORTRON, 0, 10);
	protected Set<ITileBase> emptyConnections = new HashSet<>();

	private boolean isActivated;

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (ticks % 10 == 0)
		{
			isActivated = false;
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			{
				BlockLocation loc = new BlockLocation(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				if (loc.getTile(worldObj) instanceof TileInterdictionMatrix)
				{
					isActivated = true;
				}
			}
		}
	}

	public void actionPerformed(Permission perm, Side side)
	{
		if (side == Side.CLIENT)
		{
			PacketSystem.INSTANCE.sendToServer(new PacketTile("", BIOMETRIC_IDENTIFIER_PACKET_ID, xCoord, yCoord, zCoord, perm.id));
		} else
		{
			ItemStack card = getStackInSlot(SLOT_INPUT_CARD);
			if (card != null && card.getItem() instanceof ItemIdentificationCard)
			{
				ItemIdentificationCard id = (ItemIdentificationCard) card.getItem();
				if (id.hasPermission(card, perm))
				{
					id.removePermission(card, perm);
				} else
				{
					id.addPermission(card, perm);
				}
			}
		}
	}

	public boolean isPlayerValidated(EntityPlayer player, Permission perm)
	{
		ItemStack stack = getStackInSlot(SLOT_MASTER_CARD);
		if (stack != null && stack.getItem() instanceof ItemIdentificationCard)
		{
			ItemIdentificationCard id = (ItemIdentificationCard) stack.getItem();
			UUID itemSavedId = id.getUniqueId(stack);
			if (itemSavedId != null && itemSavedId.equals(player.getUniqueID()))
			{
				return true;
			}
		}
		for (int i = 2; i < getSizeInventory(); i++)
		{
			stack = getStackInSlot(i);
			if (stack != null && stack.getItem() instanceof ItemIdentificationCard)
			{
				ItemIdentificationCard id = (ItemIdentificationCard) stack.getItem();
				UUID itemSavedId = id.getUniqueId(stack);
				if (itemSavedId != null && itemSavedId.equals(player.getUniqueID()) && id.hasPermission(stack, perm))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof ItemIdentificationCard;
	}

	@Override
	public void readCustomPacket(int id, EntityPlayer player, Side side, IPacket type)
	{
		if (id == BIOMETRIC_IDENTIFIER_PACKET_ID && side.isServer() && type instanceof PacketTile)
		{
			actionPerformed(Permission.getPermission(((PacketTile) type).customInteger), side);
		}
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		dataList.add(isActivated);
		super.writeSynchronizationPacket(dataList, player);
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		isActivated = buf.readBoolean();
		super.readSynchronizationPacket(buf, player);
	}

	@Override
	public int getSizeInventory()
	{
		return 11;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiBiometricIdentifier(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerBiometricIndentifier(player, this);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
	{
		return ACCESSIBLE_SLOTS_NONE;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_)
	{
		return false;
	}

	@Override
	public boolean isActivated()
	{
		return isActivated;
	}

	@Override
	public Set<ITileBase> getFortronConnections()
	{
		return emptyConnections;
	}

	@Override
	public FluidTank getFortronTank()
	{
		return emptyFortronTank;
	}

}
