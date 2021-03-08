package electrodynamics.common.packet;

import java.util.function.Supplier;

import electrodynamics.common.tile.quantumcapacitor.TileQuantumCapacitor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketSetQuantumCapacitorData {

    private final double outputJoules;
    private final int frequency;
    private final BlockPos pos;

    public PacketSetQuantumCapacitorData(BlockPos pos, double outputJoules, int frequency) {
	this.pos = pos;
	this.outputJoules = outputJoules;
	this.frequency = frequency;
    }

    public static void handle(PacketSetQuantumCapacitorData message, Supplier<Context> context) {
	Context ctx = context.get();
	ctx.enqueueWork(() -> {
	    ServerWorld world = context.get().getSender().getServerWorld();
	    if (world != null) {
		TileQuantumCapacitor tile = (TileQuantumCapacitor) world.getTileEntity(message.pos);
		if (tile != null) {
		    tile.outputJoules = message.outputJoules;
		    tile.frequency = message.frequency;
		}
	    }
	});
	ctx.setPacketHandled(true);
    }

    public static void encode(PacketSetQuantumCapacitorData pkt, PacketBuffer buf) {
	buf.writeBlockPos(pkt.pos);
	buf.writeDouble(pkt.outputJoules);
	buf.writeInt(pkt.frequency);
    }

    public static PacketSetQuantumCapacitorData decode(PacketBuffer buf) {
	return new PacketSetQuantumCapacitorData(buf.readBlockPos(), buf.readDouble(), buf.readInt());
    }
}