package electrodynamics.common.packet.types.client;

import java.util.function.Supplier;

import electrodynamics.common.packet.BarrierMethods;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketResetGuidebookPages {

	public static void handle(PacketResetGuidebookPages message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			Minecraft minecraft = Minecraft.getInstance();
			ClientWorld world = minecraft.level;
			if (world != null && minecraft.player != null) {
				BarrierMethods.handlerSetGuidebookInitFlag();
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketResetGuidebookPages message, PacketBuffer buf) {
	}

	public static PacketResetGuidebookPages decode(PacketBuffer buf) {
		return new PacketResetGuidebookPages();
	}
	
}