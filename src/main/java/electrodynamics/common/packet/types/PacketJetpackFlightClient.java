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

public class PacketJetpackFlightClient {

	private final UUID playerId;
	private final boolean bool;
	
	public PacketJetpackFlightClient(UUID uuid, boolean bool) {
		playerId = uuid;
		this.bool = bool;
	}
	
	public static void handle(PacketJetpackFlightClient message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if (world != null) {
				Player player = world.getPlayerByUUID(message.playerId);
				ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
				if (ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get())) {
					chest.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).ifPresent(h ->{
						h.setBoolean(message.bool);
					});
				}
			}
		});
		ctx.setPacketHandled(true);
	}
	
	public static void encode(PacketJetpackFlightClient message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
		buf.writeBoolean(message.bool);
	}
	
	public static PacketJetpackFlightClient decode(FriendlyByteBuf buf) {
		return new PacketJetpackFlightClient(buf.readUUID(), buf.readBoolean());
	}
	
}
