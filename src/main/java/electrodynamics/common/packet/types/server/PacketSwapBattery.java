package electrodynamics.common.packet.types.server;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.api.item.IItemElectric;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSwapBattery {

	private final UUID playerId;

	public PacketSwapBattery(UUID uuid) {
		playerId = uuid;
	}

	public static void handle(PacketSwapBattery message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if (world != null) {
				Player player = world.getPlayerByUUID(message.playerId);
				ItemStack handItem = player.getItemInHand(InteractionHand.MAIN_HAND);
				if(!handItem.isEmpty() && handItem.getItem() instanceof IItemElectric electric) {
					electric.swapBatteryPackFirstItem(handItem, player);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSwapBattery message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
	}

	public static PacketSwapBattery decode(FriendlyByteBuf buf) {
		return new PacketSwapBattery(buf.readUUID());
	}
	
}