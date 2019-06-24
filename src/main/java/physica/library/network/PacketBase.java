package physica.library.network;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;

public class PacketBase implements IPacket {

	protected Set<Object> writeData = new HashSet<>();
	protected ByteBuf readData;

	@SuppressWarnings("unchecked")
	@Override
	public <P extends IPacket> P addData(Object... data)
	{
		for (Object object : data) {
			writeData.add(object);
		}
		return (P) this;
	}

	@Override
	public void decodeInto(ChannelHandlerContext handler, ByteBuf buffer)
	{
		readData = buffer.slice().copy();
	}

	@Override
	public void encodeInto(ChannelHandlerContext handler, ByteBuf buffer)
	{
		for (Object object : writeData) {
			writeData(object, buffer);
		}
	}

	public ByteBuf getReadData()
	{
		return readData;
	}

	protected void writeData(Object object, ByteBuf buffer)
	{
		if (object.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(object); i++) {
				writeData(Array.get(object, i), buffer);
			}
		} else if (object instanceof Collection) {
			for (Object o : (Collection<?>) object) {
				writeData(o, buffer);
			}
		} else if (object instanceof Byte) {
			buffer.writeByte((Byte) object);
		} else if (object instanceof Boolean) {
			buffer.writeBoolean((Boolean) object);
		} else if (object instanceof Enum) {
			buffer.writeInt(((Enum<?>) object).ordinal());
		} else if (object instanceof Integer) {
			buffer.writeInt((Integer) object);
		} else if (object instanceof Short) {
			buffer.writeShort((Short) object);
		} else if (object instanceof Long) {
			buffer.writeLong((Long) object);
		} else if (object instanceof Float) {
			buffer.writeFloat((Float) object);
		} else if (object instanceof Double) {
			buffer.writeDouble((Double) object);
		} else if (object instanceof String) {
			ByteBufUtils.writeUTF8String(buffer, (String) object);
		} else if (object instanceof NBTTagCompound) {
			ByteBufUtils.writeTag(buffer, (NBTTagCompound) object);
		} else if (object instanceof ItemStack) {
			ByteBufUtils.writeItemStack(buffer, (ItemStack) object);
		} else if (object instanceof FluidTank) {
			ByteBufUtils.writeTag(buffer, ((FluidTank) object).writeToNBT(new NBTTagCompound()));
		} else {
			throw new IllegalArgumentException("PacketBase: Unsupported write method for " + object.getClass().getSimpleName());
		}
	}

}
