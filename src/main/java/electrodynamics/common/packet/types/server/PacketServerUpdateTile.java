package electrodynamics.common.packet.types.server;

import electrodynamics.api.tile.IPacketServerUpdateTile;
import electrodynamics.common.packet.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketServerUpdateTile implements CustomPacketPayload {
	private final BlockPos target;
	private final CompoundTag nbt;

	public PacketServerUpdateTile(CompoundTag nbt, BlockPos target) {
		this.nbt = nbt;
		this.target = target;
	}

	public static void handle(PacketServerUpdateTile message, PlayPayloadContext context) {
	    ServerLevel world = (ServerLevel) context.level().get();
        if (world == null) {
            return;
        }
        BlockEntity tile = world.getBlockEntity(message.target);
        if (tile instanceof IPacketServerUpdateTile serv) {
            serv.readCustomUpdate(message.nbt);
        }
	}

	public static PacketServerUpdateTile read(FriendlyByteBuf buf) {
		return new PacketServerUpdateTile(buf.readNbt(), buf.readBlockPos());
	}

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeNbt(nbt);
        buf.writeBlockPos(target);
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_SERVERUPDATETILE_PACKETID;
    }
}