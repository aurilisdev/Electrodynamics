package electrodynamics.common.packet.types.client;

import java.util.ArrayList;
import java.util.function.Supplier;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.IPropertyHolderTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSendUpdatePropertiesClient {

	private final BlockPos pos;
	private final ArrayList<Property<?>> dirtyProperties;
	private final ArrayList<Object> objects;

	public PacketSendUpdatePropertiesClient(IPropertyHolderTile tile) {
		this(tile.getTile().getBlockPos(), tile.getPropertyManager().getDirtyProperties());
	}

	public PacketSendUpdatePropertiesClient(BlockPos pos, ArrayList<Property<?>> dirtyProperties) {
		this.pos = pos;
		this.dirtyProperties = dirtyProperties;
		objects = null;
	}

	public PacketSendUpdatePropertiesClient(BlockPos pos, ArrayList<Object> values, boolean object) {
		this.pos = pos;
		dirtyProperties = null;
		objects = values;
	}

	public static void handle(PacketSendUpdatePropertiesClient message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ClientLevel world = Minecraft.getInstance().level;
			if (world != null) {
				BlockEntity tile = world.getBlockEntity(message.pos);
				if (tile instanceof IPropertyHolderTile holder) {
					for (int index = 0; index < message.objects.size(); index++) {
						Object obj = message.objects.get(index);
						if (obj != null) {
							holder.getPropertyManager().update(index, obj);
						}
					}
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSendUpdatePropertiesClient message, FriendlyByteBuf buf) {
		buf.writeBlockPos(message.pos);
		buf.writeInt(message.dirtyProperties.size());
		int size = 0;
		for (Property<?> prop : message.dirtyProperties) {
			if (prop != null) {
				size += 1;
			}
		}
		buf.writeInt(size);
		for (Property<?> prop : message.dirtyProperties) {
			if (prop != null) {
				buf.writeInt(prop.getType().ordinal());
				buf.writeInt(message.dirtyProperties.indexOf(prop));
				prop.getType().writeToBuffer.accept(prop, buf);
			}
		}
	}

	public static PacketSendUpdatePropertiesClient decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		int fullSize = buf.readInt();
		int size = buf.readInt();
		ArrayList<Object> values = new ArrayList<>(fullSize);
		for (int i = 0; i < fullSize; i++) {
			values.add(null);
		}
		while (size > 0) {
			size--;
			int type = buf.readInt();
			int index = buf.readInt();
			Object value = PropertyType.values()[type].readFromBuffer.apply(buf);
			values.set(index, value);
		}
		return new PacketSendUpdatePropertiesClient(pos, values, true);
	}
}