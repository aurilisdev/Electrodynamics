package electrodynamics.common.packet.types.server;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketJetpackFlightServer {

	private final UUID playerId;
	private final boolean bool;
	private final double prevDeltaY;

	public PacketJetpackFlightServer(UUID uuid, boolean bool, double prevDeltaY) {
		playerId = uuid;
		this.bool = bool;
		this.prevDeltaY = prevDeltaY;
	}

	public static void handle(PacketJetpackFlightServer message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if (world != null) {
				Player player = world.getPlayerByUUID(message.playerId);
				ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
				if (ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
					CompoundTag tag = chest.getOrCreateTag();
					tag.putBoolean(NBTUtils.USED, message.bool);
					tag.putBoolean(ItemJetpack.WAS_HURT_KEY, false);
					tag.putDouble(ItemJetpack.DELTA_Y_KEY, message.prevDeltaY);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketJetpackFlightServer message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
		buf.writeBoolean(message.bool);
		buf.writeDouble(message.prevDeltaY);
	}

	public static PacketJetpackFlightServer decode(FriendlyByteBuf buf) {
		return new PacketJetpackFlightServer(buf.readUUID(), buf.readBoolean(), buf.readDouble());
	}

}