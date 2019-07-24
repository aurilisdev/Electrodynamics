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
import net.minecraftforge.fluids.FluidTank;
import physica.api.core.abstraction.Face;
import physica.api.core.inventory.IGuiInterface;
import physica.api.core.tile.ITileBase;
import physica.api.forcefield.IInvFortronTile;
import physica.forcefield.client.gui.GuiBiometricIdentifier;
import physica.forcefield.common.ForcefieldFluidRegister;
import physica.forcefield.common.inventory.ContainerBiometricIndentifier;
import physica.forcefield.common.item.ItemIdentificationCard;
import physica.forcefield.common.item.Permission;
import physica.library.location.Location;
import physica.library.network.IPacket;
import physica.library.network.netty.PacketSystem;
import physica.library.network.packet.PacketTile;
import physica.library.tile.TileBaseContainer;

public class TileBiometricIdentifier extends TileBaseContainer implements IGuiInterface, IInvFortronTile {

	public static final int		BIOMETRIC_IDENTIFIER_PACKET_ID	= 10;
	public static final int		SLOT_INPUT_CARD					= 0;
	public static final int		SLOT_MASTER_CARD				= 1;
	protected FluidTank			emptyFortronTank				= new FluidTank(ForcefieldFluidRegister.LIQUID_FORTRON, 0, 10);
	protected Set<ITileBase>	emptyConnections				= new HashSet<>();

	private boolean				isActivated;

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (ticks % 10 == 0)
		{
			isActivated = false;
			Location loc = getLocation();
			for (Face dir : Face.VALID)
			{
				Location offset = loc.OffsetFace(dir);
				if (offset.getTile(World()) instanceof TileInterdictionMatrix)
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
			Location loc = getLocation();
			PacketSystem.INSTANCE.sendToServer(new PacketTile("", BIOMETRIC_IDENTIFIER_PACKET_ID, loc.xCoord, loc.yCoord, loc.zCoord, perm.id));
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
