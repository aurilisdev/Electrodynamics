package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.item.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketJetpackFlightServer {

	private final UUID playerId;
	private final boolean bool;

	public PacketJetpackFlightServer(UUID uuid, boolean bool) {
		playerId = uuid;
		this.bool = bool;
	}

	public static void handle(PacketJetpackFlightServer message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if (world != null) {
				Player player = world.getPlayerByUUID(message.playerId);
				ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
				if (ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get())) {
					chest.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).ifPresent(h -> {
						h.setBoolean(0, message.bool);
					});
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketJetpackFlightServer message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
		buf.writeBoolean(message.bool);
	}

	public static PacketJetpackFlightServer decode(FriendlyByteBuf buf) {
		return new PacketJetpackFlightServer(buf.readUUID(), buf.readBoolean());
	}

}
