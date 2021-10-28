package electrodynamics.common.packet;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.packs.repository.Pack;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

public class PacketPlayerInformation {

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

    public static void handle(PacketPlayerInformation message, Supplier<Context> context) {
	Context ctx = context.get();
	ctx.enqueueWork(() -> NetworkHandler.playerInformation.put(context.get().getSender().getName().getString(), message.information));
	ctx.setPacketHandled(true);
    }

    public static void encode(PacketPlayerInformation pkt, FriendlyByteBuf buf) {
	buf.writeUtf(pkt.information);
    }

    public static PacketPlayerInformation decode(FriendlyByteBuf buf) {
	return new PacketPlayerInformation(buf.readUtf(999999));
    }
}