package electrodynamics.common.packet.types.client;

import electrodynamics.common.packet.BarrierMethods;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketSpawnSmokeParticle implements CustomPacketPayload {

	private final BlockPos pos;

	public PacketSpawnSmokeParticle(BlockPos pos) {
		this.pos = pos;
	}

	public static void handle(PacketSpawnSmokeParticle message, PlayPayloadContext context) {
		BarrierMethods.handlerSpawnSmokeParicle(message.pos);
	}

	public static PacketSpawnSmokeParticle read(FriendlyByteBuf buf) {
		return new PacketSpawnSmokeParticle(buf.readBlockPos());
	}

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public ResourceLocation id() {
        // TODO Auto-generated method stub
        return null;
    }
}