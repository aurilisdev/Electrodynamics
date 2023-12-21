package electrodynamics.common.packet.types.client;

import java.util.HashSet;
import java.util.function.Supplier;

import electrodynamics.common.packet.BarrierMethods;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSetClientCombustionFuel {

	private final HashSet<CombustionFuelSource> fuels;

	public PacketSetClientCombustionFuel(HashSet<CombustionFuelSource> fuels) {
		this.fuels = fuels;
	}

	public static void handle(PacketSetClientCombustionFuel message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			BarrierMethods.handlerClientCombustionFuels(message.fuels);
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSetClientCombustionFuel pkt, FriendlyByteBuf buf) {
		buf.writeInt(pkt.fuels.size());
		for (CombustionFuelSource source : pkt.fuels) {
			source.writeToBuffer(buf);
		}
	}

	public static PacketSetClientCombustionFuel decode(FriendlyByteBuf buf) {
		int count = buf.readInt();
		HashSet<CombustionFuelSource> fuels = new HashSet<>();
		for (int i = 0; i < count; i++) {
			fuels.add(CombustionFuelSource.readFromBuffer(buf));
		}
		return new PacketSetClientCombustionFuel(fuels);
	}

}