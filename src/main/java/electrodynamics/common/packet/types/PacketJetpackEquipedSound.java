package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.common.packet.BarrierMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketJetpackEquipedSound {

	private final UUID player;

	public PacketJetpackEquipedSound(UUID uuid) {
		player = uuid;
	}

	public static void handle(PacketJetpackEquipedSound message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			BarrierMethods.handlePacketJetpackEquipedSound(message.player);
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketJetpackEquipedSound message, FriendlyByteBuf buf) {
		buf.writeUUID(message.player);
	}

	public static PacketJetpackEquipedSound decode(FriendlyByteBuf buf) {
		return new PacketJetpackEquipedSound(buf.readUUID());
	}

}
