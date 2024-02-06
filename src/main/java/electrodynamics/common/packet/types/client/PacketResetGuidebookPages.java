package electrodynamics.common.packet.types.client;

import electrodynamics.common.packet.BarrierMethods;
import electrodynamics.common.packet.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketResetGuidebookPages implements CustomPacketPayload {

	public static void handle(PacketResetGuidebookPages message, PlayPayloadContext context) {
	    Minecraft minecraft = Minecraft.getInstance();
        ClientLevel world = minecraft.level;
        if (world == null || minecraft.player == null) {
            return;
        }
        BarrierMethods.handlerSetGuidebookInitFlag();
	}

	public static PacketResetGuidebookPages read(FriendlyByteBuf buf) {
		return new PacketResetGuidebookPages();
	}

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_RESETGUIDEBOOKPAGES_PACKETID;
    }

}
