package electrodynamics.common.packet;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;

public class PacketSpawnSmokeParticle {

    private final BlockPos pos;

    public PacketSpawnSmokeParticle(BlockPos pos) {
	this.pos = pos;
    }

    public static void handle(PacketSpawnSmokeParticle message, Supplier<Context> context) {
	Context ctx = context.get();
	ctx.enqueueWork(() -> {
	    ClientLevel world = Minecraft.getInstance().level;
	    if (world != null) {
		world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, message.pos.getX() + 0.5, message.pos.getY() + 0.5, message.pos.getZ() + 0.5, 0,
			0, 0);
	    }
	});
	ctx.setPacketHandled(true);
    }

    public static void encode(PacketSpawnSmokeParticle pkt, FriendlyByteBuf buf) {
	buf.writeBlockPos(pkt.pos);
    }

    public static PacketSpawnSmokeParticle decode(FriendlyByteBuf buf) {
	return new PacketSpawnSmokeParticle(buf.readBlockPos());
    }
}