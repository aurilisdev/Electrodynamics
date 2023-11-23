package electrodynamics.common.packet.types.client;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.common.packet.BarrierMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketRenderJetpackParticles {

	private final UUID player;
	private final boolean isCombat;

	public PacketRenderJetpackParticles(UUID uuid, boolean combat) {
		player = uuid;
		isCombat = combat;
	}

	public static void handle(PacketRenderJetpackParticles message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			BarrierMethods.handleJetpackParticleRendering(message.player, message.isCombat);
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketRenderJetpackParticles message, FriendlyByteBuf buf) {
		buf.writeUUID(message.player);
		buf.writeBoolean(message.isCombat);
	}

	public static PacketRenderJetpackParticles decode(FriendlyByteBuf buf) {
		return new PacketRenderJetpackParticles(buf.readUUID(), buf.readBoolean());
	}

}
