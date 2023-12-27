package electrodynamics.common.packet.types.client;

import java.util.HashSet;
import java.util.function.Supplier;

import electrodynamics.common.packet.BarrierMethods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketSetClientCoalGenFuels {

	private final HashSet<Item> fuels;

	public PacketSetClientCoalGenFuels(HashSet<Item> fuels) {
		this.fuels = fuels;
	}

	public static void handle(PacketSetClientCoalGenFuels message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			BarrierMethods.handlerClientCoalGenFuels(message.fuels);
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSetClientCoalGenFuels pkt, PacketBuffer buf) {
		buf.writeInt(pkt.fuels.size());
		for (Item item : pkt.fuels) {
			buf.writeItem(new ItemStack(item));
		}
	}

	public static PacketSetClientCoalGenFuels decode(PacketBuffer buf) {
		int count = buf.readInt();
		HashSet<Item> fuels = new HashSet<>();
		for (int i = 0; i < count; i++) {
			fuels.add(buf.readItem().getItem());
		}
		return new PacketSetClientCoalGenFuels(fuels);
	}

}