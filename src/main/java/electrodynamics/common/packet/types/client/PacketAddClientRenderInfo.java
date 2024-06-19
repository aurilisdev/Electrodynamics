package electrodynamics.common.packet.types.client;

import java.util.UUID;

import electrodynamics.common.packet.BarrierMethods;
import electrodynamics.common.packet.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketAddClientRenderInfo implements CustomPacketPayload {

    private final UUID playerId;
    private final BlockPos pos;

    public PacketAddClientRenderInfo(UUID uuid, BlockPos pos) {
        playerId = uuid;
        this.pos = pos;
    }

    public static void handle(PacketAddClientRenderInfo message, PlayPayloadContext context) {
        BarrierMethods.handleAddClientRenderInfo(message.playerId, message.pos);
    }

    public static PacketAddClientRenderInfo read(FriendlyByteBuf buf) {
        return new PacketAddClientRenderInfo(buf.readUUID(), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(playerId);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_ADDCLIENTRENDERINFO_PACKETID;
    }

}
