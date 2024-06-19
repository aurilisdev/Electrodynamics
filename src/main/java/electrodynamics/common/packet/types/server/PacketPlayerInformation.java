package electrodynamics.common.packet.types.server;

import java.util.List;

import electrodynamics.common.packet.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketPlayerInformation implements CustomPacketPayload {

	private String information;

	public PacketPlayerInformation() {
		String actual = "";
		List<ModInfo> total = FMLLoader.getLoadingModList().getMods();
		for (ModInfo info : total) {
			actual += info.getModId() + ":";
		}
		for (Pack pack : Minecraft.getInstance().getResourcePackRepository().getAvailablePacks()) {
			actual += pack.getId() + "," + pack.getTitle().getString() + "," + pack.getDescription() + ":";
		}
		information = actual;
	}

	public PacketPlayerInformation(String info) {
		information = info;
	}

	public static void handle(PacketPlayerInformation message, PlayPayloadContext context) {
	    NetworkHandler.playerInformation.put(context.channelHandlerContext().name(), message.information);
	}

	public static PacketPlayerInformation read(FriendlyByteBuf buf) {
		return new PacketPlayerInformation(buf.readUtf(999999));
	}

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(information);
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_PLAYERINFORMATION_PACKETID;
    }
}