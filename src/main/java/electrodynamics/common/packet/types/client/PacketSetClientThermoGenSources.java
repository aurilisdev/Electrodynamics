package electrodynamics.common.packet.types.client;

import java.util.HashMap;
import java.util.function.Supplier;

import electrodynamics.common.packet.BarrierMethods;
import net.minecraft.fluid.Fluid;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketSetClientThermoGenSources {

	private final HashMap<Fluid, Double> heatSources;

	public PacketSetClientThermoGenSources(HashMap<Fluid, Double> heatSources) {
		this.heatSources = heatSources;
	}

	public static void handle(PacketSetClientThermoGenSources message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			BarrierMethods.handlerClientThermoGenHeatSources(message.heatSources);
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSetClientThermoGenSources pkt, PacketBuffer buf) {
		buf.writeInt(pkt.heatSources.size());
		pkt.heatSources.forEach((fluid, value) -> {
			buf.writeFluidStack(new FluidStack(fluid, 1));
			buf.writeDouble(value);
		});
	}

	public static PacketSetClientThermoGenSources decode(PacketBuffer buf) {
		int count = buf.readInt();
		HashMap<Fluid, Double> values = new HashMap<>();
		for (int i = 0; i < count; i++) {
			values.put(buf.readFluidStack().getFluid(), buf.readDouble());
		}
		return new PacketSetClientThermoGenSources(values);
	}
	
}