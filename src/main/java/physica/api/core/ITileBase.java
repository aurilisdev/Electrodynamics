package physica.api.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import physica.library.location.BlockLocation;
import physica.library.location.VectorLocation;
import physica.library.network.IPacket;
import physica.library.network.IPacketReciever;
import physica.library.network.netty.PacketSystem;
import physica.library.network.packet.PacketTile;

public interface ITileBase extends IPlayerUsing, ISidedObject, IPacketReciever, IRotatable {

	int	DESC_PACKET_ID	= 0;
	int	GUI_PACKET_ID	= 1;

	default TileEntity This()
	{
		return (TileEntity) this;
	}

	default boolean isClient()
	{
		return This().getWorldObj() != null && This().getWorldObj().isRemote;
	}

	default boolean isServer()
	{
		return This().getWorldObj() != null && !This().getWorldObj().isRemote;
	}

	default void onFirstUpdate()
	{
	}

	default int getGuiSyncRate()
	{
		return 3;
	}

	default int getSyncRate()
	{
		return 20;
	}

	default BlockLocation getBlockLocation()
	{
		return new BlockLocation(This());
	}

	default VectorLocation getLocation()
	{
		return new VectorLocation(This());
	}

	default boolean shouldSendGuiPacket(EntityPlayerMP playerMP)
	{
		return playerMP.isEntityAlive() && playerMP.openContainer != null;
	}

	default boolean isPoweredByRedstone()
	{
		return This().getWorldObj().isBlockIndirectlyGettingPowered(This().xCoord, This().yCoord, This().zCoord);
	}

	default int handleUpdate(int ticks)
	{
		if (ticks == 0)
		{
			onFirstUpdate();
		}
		handleUpdate(This().getWorldObj(), ticks);
		ticks++;
		if (ticks + 1 == Integer.MAX_VALUE)
		{
			ticks = 1;
		}
		if (isServer() && ticks % getGuiSyncRate() == 0 && this instanceof IGuiInterface)
		{
			Iterator<EntityPlayer> it = getPlayersUsingGui().iterator();
			while (it.hasNext())
			{
				EntityPlayer player = it.next();
				if (player instanceof EntityPlayerMP && shouldSendGuiPacket((EntityPlayerMP) player))
				{
					PacketTile packet = new PacketTile("guiSync", GUI_PACKET_ID, This());
					List<Object> objects = new ArrayList<>();
					writeClientGuiPacket(objects, player);
					packet.addData(objects);
					PacketSystem.INSTANCE.sendToPlayer(packet, (EntityPlayerMP) player);
				} else
				{
					it.remove();
				}
			}
		}
		if (ticks % getSyncRate() == 0)
		{
			sendDescPacket();
		}
		return ticks;
	}

	default void handleWriteToNBT(NBTTagCompound nbt)
	{
		if (isRotateAble())
		{
			nbt.setInteger("facing", getFacing().ordinal());
		}
	}

	default void handleReadFromNBT(NBTTagCompound nbt)
	{
		if (isRotateAble())
		{
			setFacing(ForgeDirection.getOrientation(nbt.getInteger("facing")));
		}
	}

	@Override
	default boolean read(ByteBuf buf, int id, EntityPlayer player, IPacket type)
	{
		if (isClient())
		{
			if (id == DESC_PACKET_ID)
			{
				readSynchronizationPacket(buf, player);
				return true;
			} else if (id == GUI_PACKET_ID)
			{
				readClientGuiPacket(buf, player);
				return true;
			}
		}
		if (id != DESC_PACKET_ID && id != GUI_PACKET_ID)
		{
			readCustomPacket(id, player, isClient() ? Side.CLIENT : Side.SERVER, type);
		}
		return false;
	}

	default void readCustomPacket(int id, EntityPlayer player, Side side, IPacket type)
	{
	}

	default void sendDescPacket()
	{
		if (isServer())
		{
			PacketTile packetTile = new PacketTile("descSync", DESC_PACKET_ID, This());
			List<Object> list = new ArrayList<>();
			writeSynchronizationPacket(list, null);
			packetTile.addData(list);

			PacketSystem.INSTANCE.sendToAllAround(packetTile, This());
		}
	}

	default void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		if (isRotateAble())
		{
			setFacing(ForgeDirection.getOrientation(buf.readInt()));
		}
	}

	default void readClientGuiPacket(ByteBuf buf, EntityPlayer player)
	{
	}

	default void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		if (isRotateAble())
		{
			dataList.add(getFacing().ordinal());
		}
	}

	default void writeClientGuiPacket(List<Object> dataList, EntityPlayer player)
	{
	}

	default Set<TileEntity> getNearbyTiles(int radius)
	{
		Set<TileEntity> tiles = new HashSet<>();
		for (int i = -radius; i <= radius; i++)
		{
			for (int j = -radius; j <= radius; j++)
			{
				for (int k = -radius; k <= radius; k++)
				{
					TileEntity tile = This().getWorldObj().getTileEntity(This().xCoord + i, This().yCoord + j, This().zCoord + k);
					if (tile != this && tile != null)
					{
						tiles.add(tile);
					}
				}
			}
		}
		return tiles;
	}

	@Override
	default ForgeDirection getFacing()
	{
		return ForgeDirection.UNKNOWN;
	}

	@Override
	default void setFacing(ForgeDirection facing)
	{
	}

	default boolean isRotateAble()
	{
		return false;
	}

	abstract int getTicksRunning();
}
