package electrodynamics.common.packet.types.client;

import java.util.HashSet;

import electrodynamics.common.packet.BarrierMethods;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.prefab.properties.IPropertyType;
import electrodynamics.prefab.properties.IPropertyType.BufferReader;
import electrodynamics.prefab.properties.IPropertyType.BufferWriter;
import electrodynamics.prefab.properties.PropertyManager;
import electrodynamics.prefab.properties.PropertyManager.PropertyWrapper;
import electrodynamics.prefab.tile.IPropertyHolderTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketSendUpdatePropertiesClient implements CustomPacketPayload {

    private final BlockPos pos;
    private final HashSet<PropertyWrapper> values;

    public PacketSendUpdatePropertiesClient(IPropertyHolderTile tile) {
        this(tile.getTile().getBlockPos(), tile.getPropertyManager().getClientUpdateProperties());
    }

    public PacketSendUpdatePropertiesClient(BlockPos pos, HashSet<PropertyWrapper> dirtyProperties) {
        this.pos = pos;
        values = dirtyProperties;
    }

    public static void handle(PacketSendUpdatePropertiesClient message, PlayPayloadContext context) {
        BarrierMethods.handlePropertiesUpdateClient(message.pos, message.values);
    }

    public static PacketSendUpdatePropertiesClient read(FriendlyByteBuf buf) {
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

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(values.size());
        values.forEach(wrapper -> {
            buf.writeInt(wrapper.index());
            buf.writeResourceLocation(wrapper.type().getId());
            wrapper.type().writeToBuffer(new BufferWriter(wrapper.property().get(), buf));
        });
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_SENDUPDATEPROPERTIESCLIENT_PACKETID;
    }

}