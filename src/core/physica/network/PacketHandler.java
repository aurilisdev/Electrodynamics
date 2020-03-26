package physica.network;

import java.util.List;
import java.util.Set;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import physica.Physica;
import physica.References;
import physica.utility.PlayerUtilities;

public class PacketHandler {
	public static final SimpleNetworkWrapper	networkWrapper	= NetworkRegistry.INSTANCE.newSimpleChannel(References.DOMAIN);
	public static final PacketHandler			instance		= new PacketHandler();

	public void sendTo(IMessage message, EntityPlayerMP player)
	{
		networkWrapper.sendTo(message, player);
	}

	public void sendToAll(IMessage message)
	{
		networkWrapper.sendToAll(message);
	}

	public void sendToAllAround(IMessage message, TargetPoint point)
	{
		networkWrapper.sendToAllAround(message, point);
	}

	public void sendToDimension(IMessage message, int dimensionId)
	{
		networkWrapper.sendToDimension(message, dimensionId);
	}

	public void sendToServer(IMessage message)
	{
		networkWrapper.sendToServer(message);
	}

	public void sendToCuboid(IMessage message, AxisAlignedBB cuboid, int dimensionId)
	{
		if (cuboid != null)
		{
			for (EntityPlayerMP player : PlayerUtilities.getPlayers())
			{
				if (player.dimension == dimensionId && cuboid.contains(new Vec3d(player.posX, player.posY, player.posZ)))
				{
					sendTo(message, player);
				}
			}
		}
	}

	public void sendToReceivers(IMessage message, Set<EntityPlayer> playerList)
	{
		for (EntityPlayer player : playerList)
		{
			sendTo(message, (EntityPlayerMP) player);
		}
	}

	public void sendToReceivers(IMessage message, Range range)
	{
		for (EntityPlayerMP player : PlayerUtilities.getPlayers())
		{
			if (player.getEntityWorld().equals(range.getWorld()) && Range.getChunkRange(player).intersects(range))
			{
				sendTo(message, player);
			}
		}
	}

	public void sendToReceivers(IMessage message, Entity entity)
	{
		sendToReceivers(message, new Range(entity));
	}

	public void sendToReceivers(IMessage message, TileEntity tileEntity)
	{
		sendToReceivers(message, new Range(tileEntity));
	}

	public static EntityPlayer getPlayer(MessageContext context)
	{
		return Physica.proxy.getPlayer(context);
	}

	public static World getWorld(MessageContext context)
	{
		return getPlayer(context).getEntityWorld();
	}

	public static void writeObject(Object object, ByteBuf dataStream)
	{
		try
		{
			if (object instanceof Boolean)
			{
				dataStream.writeBoolean((Boolean) object);
			} else if (object instanceof Byte)
			{
				dataStream.writeByte((Byte) object);
			} else if (object instanceof byte[])
			{
				dataStream.writeBytes((byte[]) object);
			} else if (object instanceof Double)
			{
				dataStream.writeDouble((Double) object);
			} else if (object instanceof Float)
			{
				dataStream.writeFloat((Float) object);
			} else if (object instanceof Integer)
			{
				dataStream.writeInt((Integer) object);
			} else if (object instanceof int[])
			{
				for (int i : (int[]) object)
				{
					dataStream.writeInt(i);
				}
			} else if (object instanceof Long)
			{
				dataStream.writeLong((Long) object);
			} else if (object instanceof String)
			{
				ByteBufUtils.writeUTF8String(dataStream, (String) object);
			} else if (object instanceof ItemStack)
			{
				ByteBufUtils.writeItemStack(dataStream, (ItemStack) object);
			} else if (object instanceof NBTTagCompound)
			{
				ByteBufUtils.writeTag(dataStream, (NBTTagCompound) object);
			}
		} catch (Exception e)
		{
			Physica.logger.error("An error occurred when sending packet data.");
			e.printStackTrace();
		}
	}

	public static void writeObjects(List<Object> objects, ByteBuf dataStream)
	{
		for (Object object : objects)
		{
			writeObject(object, dataStream);
		}
	}
}