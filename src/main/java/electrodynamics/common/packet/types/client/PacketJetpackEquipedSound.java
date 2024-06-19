package electrodynamics.common.packet.types.client;

import java.util.UUID;

import electrodynamics.common.packet.BarrierMethods;
import electrodynamics.common.packet.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketJetpackEquipedSound implements CustomPacketPayload {

	private final UUID player;

	public PacketJetpackEquipedSound(UUID uuid) {
		player = uuid;
	}

	public static void handle(PacketJetpackEquipedSound message, PlayPayloadContext context) {
	    BarrierMethods.handlePacketJetpackEquipedSound(message.player);
	}

	public static PacketJetpackEquipedSound read(FriendlyByteBuf buf) {
		return new PacketJetpackEquipedSound(buf.readUUID());
	}

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(player);
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_JETPACKEQUIPEDSOUND_PACKETID;
    }

}
