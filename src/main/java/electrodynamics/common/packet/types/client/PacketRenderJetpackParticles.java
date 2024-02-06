package electrodynamics.common.packet.types.client;

import java.util.UUID;

import electrodynamics.common.packet.BarrierMethods;
import electrodynamics.common.packet.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketRenderJetpackParticles implements CustomPacketPayload {

    private final UUID player;
    private final boolean isCombat;

    public PacketRenderJetpackParticles(UUID uuid, boolean combat) {
        player = uuid;
        isCombat = combat;
    }

    public static void handle(PacketRenderJetpackParticles message, PlayPayloadContext context) {
        BarrierMethods.handleJetpackParticleRendering(message.player, message.isCombat);
    }

    public static PacketRenderJetpackParticles read(FriendlyByteBuf buf) {
        return new PacketRenderJetpackParticles(buf.readUUID(), buf.readBoolean());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(player);
        buf.writeBoolean(isCombat);
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_RENDERJETPACKPARTICLES_PACKETID;
    }

}
