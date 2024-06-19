package electrodynamics.common.packet.types.client;

import java.util.HashSet;

import electrodynamics.common.packet.BarrierMethods;
import electrodynamics.common.packet.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketSetClientCoalGenFuels implements CustomPacketPayload {

    private final HashSet<Item> fuels;

    public PacketSetClientCoalGenFuels(HashSet<Item> fuels) {
        this.fuels = fuels;
    }

    public static void handle(PacketSetClientCoalGenFuels message, PlayPayloadContext context) {
        BarrierMethods.handlerClientCoalGenFuels(message.fuels);
    }

    public static PacketSetClientCoalGenFuels read(FriendlyByteBuf buf) {
        int count = buf.readInt();
        HashSet<Item> fuels = new HashSet<>();
        for (int i = 0; i < count; i++) {
            fuels.add(buf.readItem().getItem());
        }
        return new PacketSetClientCoalGenFuels(fuels);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(fuels.size());
        for (Item item : fuels) {
            buf.writeItem(new ItemStack(item));
        }
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_SETCLIENTCOALGENFUELS_PACKETID;
    }

}
