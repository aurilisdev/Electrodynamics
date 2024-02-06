package electrodynamics.common.packet.types.client;

import java.util.HashSet;

import electrodynamics.common.packet.BarrierMethods;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketSetClientCombustionFuel implements CustomPacketPayload {

    private final HashSet<CombustionFuelSource> fuels;

    public PacketSetClientCombustionFuel(HashSet<CombustionFuelSource> fuels) {
        this.fuels = fuels;
    }

    public static void handle(PacketSetClientCombustionFuel message, PlayPayloadContext context) {
        BarrierMethods.handlerClientCombustionFuels(message.fuels);
    }

    public static PacketSetClientCombustionFuel read(FriendlyByteBuf buf) {
        int count = buf.readInt();
        HashSet<CombustionFuelSource> fuels = new HashSet<>();
        for (int i = 0; i < count; i++) {
            fuels.add(CombustionFuelSource.readFromBuffer(buf));
        }
        return new PacketSetClientCombustionFuel(fuels);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(fuels.size());
        for (CombustionFuelSource source : fuels) {
            source.writeToBuffer(buf);
        }
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_SETCLIENTCOMBUSTIONFUEL_PACKETID;
    }

}
