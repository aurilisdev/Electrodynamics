package electrodynamics.common.packet.types;

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
	
	public PacketUpdateCarriedItemServer(ItemStack carriedItem, BlockPos tilePos) {
		this.carriedItem = carriedItem;
		this.tilePos = tilePos;
	}
	
	public static void handle(PacketUpdateCarriedItemServer message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if (world != null) {
				GenericTile tile = (GenericTile) world.getBlockEntity(message.tilePos);
				if (tile != null) {
					tile.updateCarriedItemInContainer(message.carriedItem);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketUpdateCarriedItemServer pkt, FriendlyByteBuf buf) {
		buf.writeItem(pkt.carriedItem);
		buf.writeBlockPos(pkt.tilePos);
	}

	public static PacketUpdateCarriedItemServer decode(FriendlyByteBuf buf) {
		return new PacketUpdateCarriedItemServer(buf.readItem(), buf.readBlockPos());
	}

}
