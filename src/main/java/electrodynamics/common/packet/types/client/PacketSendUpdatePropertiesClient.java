package electrodynamics.common.packet.types.client;

import java.util.HashSet;
import java.util.function.Supplier;

import electrodynamics.prefab.properties.IPropertyType;
import electrodynamics.prefab.properties.IPropertyType.BufferReader;
import electrodynamics.prefab.properties.IPropertyType.BufferWriter;
import electrodynamics.prefab.properties.PropertyManager;
import electrodynamics.prefab.properties.PropertyManager.PropertyWrapper;
import electrodynamics.prefab.tile.IPropertyHolderTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSendUpdatePropertiesClient {

	private final BlockPos pos;
	private final HashSet<PropertyWrapper> values;

	public PacketSendUpdatePropertiesClient(IPropertyHolderTile tile) {
		this(tile.getTile().getBlockPos(), tile.getPropertyManager().getClientUpdateProperties());
	}

	public PacketSendUpdatePropertiesClient(BlockPos pos, HashSet<PropertyWrapper> dirtyProperties) {
		this.pos = pos;
		values = dirtyProperties;
	}

	public static void handle(PacketSendUpdatePropertiesClient message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ClientLevel world = Minecraft.getInstance().level;
			if (world != null) {
				BlockEntity tile = world.getBlockEntity(message.pos);
				if (tile instanceof IPropertyHolderTile holder) {
					for (PropertyWrapper wrapper : message.values) {

						holder.getPropertyManager().update(wrapper.index(), wrapper.value());

					}
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSendUpdatePropertiesClient message, FriendlyByteBuf buf) {
		buf.writeBlockPos(message.pos);
		buf.writeInt(message.values.size());
		message.values.forEach(wrapper -> {
			buf.writeInt(wrapper.index());
			buf.writeResourceLocation(wrapper.type().getId());
			wrapper.type().writeToBuffer(new BufferWriter(wrapper.value(), buf));
		});
	}

	public static PacketSendUpdatePropertiesClient decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();

		int size = buf.readInt();
		HashSet<PropertyWrapper> properties = new HashSet<>();

		int index;
		IPropertyType propertyType;

		for (int i = 0; i < size; i++) {

			index = buf.readInt();
			propertyType = PropertyManager.REGISTERED_PROPERTIES.get(buf.readResourceLocation());
			properties.add(new PropertyWrapper(index, propertyType, propertyType.readFromBuffer(new BufferReader(buf)), null));

		}
		return new PacketSendUpdatePropertiesClient(pos, properties);
	}

}