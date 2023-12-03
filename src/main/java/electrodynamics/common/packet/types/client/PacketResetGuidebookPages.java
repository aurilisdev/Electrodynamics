package electrodynamics.common.packet.types.client;

import java.util.function.Supplier;

import electrodynamics.common.packet.BarrierMethods;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketResetGuidebookPages {

	public static void handle(PacketResetGuidebookPages message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			Minecraft minecraft = Minecraft.getInstance();
			ClientLevel world = minecraft.level;
			if (world != null && minecraft.player != null) {
				BarrierMethods.handlerSetGuidebookInitFlag();
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketResetGuidebookPages message, FriendlyByteBuf buf) {
	}

	public static PacketResetGuidebookPages decode(FriendlyByteBuf buf) {
		return new PacketResetGuidebookPages();
	}
	
}