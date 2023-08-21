package electrodynamics.common.packet.types.server;

import java.util.function.Supplier;

import electrodynamics.prefab.properties.IPropertyType;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyManager;
import electrodynamics.prefab.properties.PropertyManager.PropertyWrapper;
import electrodynamics.prefab.tile.IPropertyHolderTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;

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
			ServerLevel world = ctx.getSender().serverLevel();
			if (world != null) {
				BlockEntity tile = world.getBlockEntity(message.tilePos);
				if (tile instanceof IPropertyHolderTile holder) {
					holder.getPropertyManager().update(message.wrapper.index(), message.wrapper.value());
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSendUpdatePropertiesServer message, FriendlyByteBuf buf) {

		buf.writeInt(message.wrapper.index());
		buf.writeResourceLocation(message.wrapper.type().getId());
		message.wrapper.type().writeToBuffer(message.wrapper.value(), buf);

		buf.writeBlockPos(message.tilePos);

	}

	public static PacketSendUpdatePropertiesServer decode(FriendlyByteBuf buf) {

		int index = buf.readInt();
		IPropertyType type = PropertyManager.REGISTERED_PROPERTIES.get(buf.readResourceLocation());

		return new PacketSendUpdatePropertiesServer(new PropertyWrapper(index, type, type.readFromBuffer(buf), null), buf.readBlockPos());
	}

}
