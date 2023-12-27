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
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

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
			ClientWorld world = Minecraft.getInstance().level;
			if (world != null) {
				TileEntity tile = world.getBlockEntity(message.pos);
				if (tile instanceof IPropertyHolderTile) {
					IPropertyHolderTile holder = (IPropertyHolderTile) tile;
					for (PropertyWrapper wrapper : message.values) {

						holder.getPropertyManager().update(wrapper.index, wrapper.value);

					}
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSendUpdatePropertiesClient message, PacketBuffer buf) {
		buf.writeBlockPos(message.pos);
		buf.writeInt(message.values.size());
		message.values.forEach(wrapper -> {
			buf.writeInt(wrapper.index);
			buf.writeResourceLocation(wrapper.type.getId());
			wrapper.type.writeToBuffer(new BufferWriter(wrapper.property.get(), buf));
		});
	}

	public static PacketSendUpdatePropertiesClient decode(PacketBuffer buf) {
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