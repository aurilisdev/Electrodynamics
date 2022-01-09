package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.item.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketModeSwitchServer {

	private final UUID playerId;

	public PacketModeSwitchServer(UUID uuid) {
		playerId = uuid;
	}

	public static void handle(PacketModeSwitchServer message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel serverWorld = context.get().getSender().getLevel();
			if (serverWorld != null) {
				ServerPlayer serverPlayer = (ServerPlayer) serverWorld.getPlayerByUUID(message.playerId);
				ItemStack chest = serverPlayer.getItemBySlot(EquipmentSlot.CHEST);
				if (ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get())) {
					boolean sucessful = chest.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> {
						int curMode = m.getInt(0);
						if (curMode < 2) {
							curMode++;
						} else {
							curMode = 0;
						}
						m.setInt(0, curMode);
						return true;
					}).orElse(false);
					if (sucessful) {
						serverPlayer.playNotifySound(SoundRegister.SOUND_JETPACKSWITCHMODE.get(), SoundSource.PLAYERS, 1, 1);
						/*
						 * doesn't fix problem serverWorld.getChunkSource().chunkMap.getPlayers(serverPlayer.chunkPosition(), false).forEach(play ->{ if(play.getUUID().equals(message.playerId)) { int mode = chest.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> m.getInt(0)).orElse(-1); NetworkHandler.CHANNEL.sendTo(new PacketModeSwitchClient(message.playerId, mode), play.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT); } });
						 */
					}
				}
			}

		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketModeSwitchServer message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
	}

	public static PacketModeSwitchServer decode(FriendlyByteBuf buf) {
		return new PacketModeSwitchServer(buf.readUUID());
	}

}
