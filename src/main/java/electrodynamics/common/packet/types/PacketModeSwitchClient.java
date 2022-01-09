package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.item.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketModeSwitchClient {

	private final UUID playerId;
	private final int mode;

	public PacketModeSwitchClient(UUID uuid, int mode) {
		playerId = uuid;
		this.mode = mode;
	}

	public static void handle(PacketModeSwitchClient message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ClientLevel world = Minecraft.getInstance().level;
			if (world != null) {
				Player player = world.getPlayerByUUID(message.playerId);
				ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
				if (ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get())) {
					chest.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> {
						h.setInt(0, message.mode);
					});
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketModeSwitchClient message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
		buf.writeInt(message.mode);
	}

	public static PacketModeSwitchClient decode(FriendlyByteBuf buf) {
		return new PacketModeSwitchClient(buf.readUUID(), buf.readInt());
	}

}
