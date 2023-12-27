package electrodynamics.common.packet.types.server;

import java.util.function.Supplier;

import electrodynamics.prefab.properties.IPropertyType;
import electrodynamics.prefab.properties.IPropertyType.BufferReader;
import electrodynamics.prefab.properties.IPropertyType.BufferWriter;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyManager;
import electrodynamics.prefab.properties.PropertyManager.PropertyWrapper;
import electrodynamics.prefab.tile.IPropertyHolderTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketSendUpdatePropertiesServer {

	private final BlockPos tilePos;
	private final PropertyWrapper wrapper;

	public PacketSendUpdatePropertiesServer(Property<?> property, BlockPos tilePos) {
		this.tilePos = tilePos;
		wrapper = new PropertyWrapper(property.getIndex(), property.getType(), property.get(), property);
	}

	public PacketSendUpdatePropertiesServer(PropertyWrapper property, BlockPos tilePos) {
		this.tilePos = tilePos;
		wrapper = property;
	}

	public static void handle(PacketSendUpdatePropertiesServer message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerWorld world = ctx.getSender().getLevel();
			if (world != null) {
				TileEntity tile = world.getBlockEntity(message.tilePos);
				if (tile instanceof IPropertyHolderTile) {
					IPropertyHolderTile holder = (IPropertyHolderTile) tile;
					holder.getPropertyManager().update(message.wrapper.index, message.wrapper.value);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSendUpdatePropertiesServer message, PacketBuffer buf) {

		buf.writeInt(message.wrapper.index);
		buf.writeResourceLocation(message.wrapper.type.getId());
		message.wrapper.type.writeToBuffer(new BufferWriter(message.wrapper.value, buf));

		buf.writeBlockPos(message.tilePos);

	}

	public static PacketSendUpdatePropertiesServer decode(PacketBuffer buf) {

		int index = buf.readInt();
		IPropertyType type = PropertyManager.REGISTERED_PROPERTIES.get(buf.readResourceLocation());

		return new PacketSendUpdatePropertiesServer(new PropertyWrapper(index, type, type.readFromBuffer(new BufferReader(buf)), null), buf.readBlockPos());
	}

}