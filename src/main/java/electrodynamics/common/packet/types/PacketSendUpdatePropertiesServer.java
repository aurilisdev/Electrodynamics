package electrodynamics.common.packet.types;

import java.util.function.Supplier;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.IPropertyHolderTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSendUpdatePropertiesServer {

	private final int propertyIndex;
	private final Property<?> property;
	private final BlockPos tilePos;
	private final Object value;

	public PacketSendUpdatePropertiesServer(int propertyIndex, Property<?> property, BlockPos tilePos) {
		this.propertyIndex = propertyIndex;
		this.property = property;
		this.tilePos = tilePos;
		value = null;
	}

	public PacketSendUpdatePropertiesServer(int propertyIndex, Object value, BlockPos tilePos) {
		this.propertyIndex = propertyIndex;
		this.property = null;
		this.tilePos = tilePos;
		this.value = value;
	}

	public static void handle(PacketSendUpdatePropertiesServer message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = ctx.getSender().getLevel();
			if (world != null) {
				BlockEntity tile = world.getBlockEntity(message.tilePos);
				if (tile instanceof IPropertyHolderTile holder) {
					holder.getPropertyManager().update(message.propertyIndex, message.value);
					holder.getPropertyManager().setDirty();
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSendUpdatePropertiesServer message, FriendlyByteBuf buf) {

		buf.writeBlockPos(message.tilePos);
		buf.writeInt(message.propertyIndex);
		buf.writeInt(message.property.getType().ordinal());
		message.property.getType().write(message.property, buf);

	}

	public static PacketSendUpdatePropertiesServer decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		int propertyIndex = buf.readInt();
		int type = buf.readInt();
		Object value = PropertyType.values()[type].read(buf);

		return new PacketSendUpdatePropertiesServer(propertyIndex, value, pos);
	}

}
