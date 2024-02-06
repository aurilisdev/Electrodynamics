package electrodynamics.common.packet.types.server;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.prefab.properties.IPropertyType;
import electrodynamics.prefab.properties.IPropertyType.BufferReader;
import electrodynamics.prefab.properties.IPropertyType.BufferWriter;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyManager;
import electrodynamics.prefab.properties.PropertyManager.PropertyWrapper;
import electrodynamics.prefab.tile.IPropertyHolderTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketSendUpdatePropertiesServer implements CustomPacketPayload {

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

    public static void handle(PacketSendUpdatePropertiesServer message, PlayPayloadContext context) {
        ServerLevel world = (ServerLevel) context.level().get();
        if (world == null) {
            return;
        }
        BlockEntity tile = world.getBlockEntity(message.tilePos);
        if (tile instanceof IPropertyHolderTile holder) {
            holder.getPropertyManager().update(message.wrapper.index(), message.wrapper.value());
        }
    }

    public static PacketSendUpdatePropertiesServer read(FriendlyByteBuf buf) {

        int index = buf.readInt();
        IPropertyType type = PropertyManager.REGISTERED_PROPERTIES.get(buf.readResourceLocation());

        return new PacketSendUpdatePropertiesServer(new PropertyWrapper(index, type, type.readFromBuffer(new BufferReader(buf)), null), buf.readBlockPos());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(wrapper.index());
        buf.writeResourceLocation(wrapper.type().getId());
        wrapper.type().writeToBuffer(new BufferWriter(wrapper.value(), buf));

        buf.writeBlockPos(tilePos);
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_SENDUPDATEPROPERTIESSERVER_PACKETID;
    }

}
