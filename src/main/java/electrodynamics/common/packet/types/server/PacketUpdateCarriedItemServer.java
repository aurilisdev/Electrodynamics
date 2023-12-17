package electrodynamics.common.packet.types.server;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent.Context;

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
			ServerWorld world = context.get().getSender().getLevel();
			if (world != null) {
				GenericTile tile = (GenericTile) world.getBlockEntity(message.tilePos);
				if (tile != null) {
					tile.updateCarriedItemInContainer(message.carriedItem, message.playerId);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketUpdateCarriedItemServer pkt, PacketBuffer buf) {
		buf.writeItem(pkt.carriedItem);
		buf.writeBlockPos(pkt.tilePos);
		buf.writeUUID(pkt.playerId);
	}

	public static PacketUpdateCarriedItemServer decode(PacketBuffer buf) {
		return new PacketUpdateCarriedItemServer(buf.readItem(), buf.readBlockPos(), buf.readUUID());
	}

}