package electrodynamics.common.packet.types.server;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateCarriedItemServer {
	
	private final ItemStack carriedItem;
	private final BlockPos tilePos;
	private final UUID playerId;
	
	public PacketUpdateCarriedItemServer(ItemStack carriedItem, BlockPos tilePos, UUID playerId) {
		this.carriedItem = carriedItem;
		this.tilePos = tilePos;
		this.playerId = playerId;
	}
	
	public static void handle(PacketUpdateCarriedItemServer message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if (world != null) {
				GenericTile tile = (GenericTile) world.getBlockEntity(message.tilePos);
				if (tile != null) {
					tile.updateCarriedItemInContainer(message.carriedItem, message.playerId);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketUpdateCarriedItemServer pkt, FriendlyByteBuf buf) {
		buf.writeItem(pkt.carriedItem);
		buf.writeBlockPos(pkt.tilePos);
		buf.writeUUID(pkt.playerId);
	}

	public static PacketUpdateCarriedItemServer decode(FriendlyByteBuf buf) {
		return new PacketUpdateCarriedItemServer(buf.readItem(), buf.readBlockPos(), buf.readUUID());
	}

}