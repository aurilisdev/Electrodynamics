package electrodynamics.common.packet.types.client;

import java.util.HashMap;

import electrodynamics.common.packet.BarrierMethods;
import electrodynamics.common.packet.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketSetClientThermoGenSources implements CustomPacketPayload {

    private final HashMap<Fluid, Double> heatSources;

    public PacketSetClientThermoGenSources(HashMap<Fluid, Double> heatSources) {
        this.heatSources = heatSources;
    }

    public static void handle(PacketSetClientThermoGenSources message, PlayPayloadContext context) {
        BarrierMethods.handlerClientThermoGenHeatSources(message.heatSources);
    }

    public static PacketSetClientThermoGenSources read(FriendlyByteBuf buf) {
        int count = buf.readInt();
        HashMap<Fluid, Double> values = new HashMap<>();
        for (int i = 0; i < count; i++) {
            values.put(buf.readFluidStack().getFluid(), buf.readDouble());
        }
        return new PacketSetClientThermoGenSources(values);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(heatSources.size());
        heatSources.forEach((fluid, value) -> {
            buf.writeFluidStack(new FluidStack(fluid, 1));
            buf.writeDouble(value);
        });
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_SETCLIENTTHERMOGENSOURCES_PACKETID;
    }

}
