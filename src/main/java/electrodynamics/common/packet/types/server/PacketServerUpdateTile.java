package electrodynamics.common.packet.types.server;

import java.util.function.Supplier;

import electrodynamics.api.tile.IPacketServerUpdateTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketServerUpdateTile {
	private final BlockPos target;
	private final CompoundTag nbt;

	public PacketServerUpdateTile(CompoundTag nbt, BlockPos target) {
		this.nbt = nbt;
		this.target = target;
	}

	public static void handle(PacketServerUpdateTile message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if (world != null) {
				BlockEntity tile = world.getBlockEntity(message.target);
				if (tile instanceof IPacketServerUpdateTile serv) {
					serv.readCustomUpdate(message.nbt);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketServerUpdateTile pkt, FriendlyByteBuf buf) {
		buf.writeNbt(pkt.nbt);
		buf.writeBlockPos(pkt.target);
	}

	public static PacketServerUpdateTile decode(FriendlyByteBuf buf) {
		return new PacketServerUpdateTile(buf.readNbt(), buf.readBlockPos());
	}
}