package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.api.item.nbtutils.IntegerStorage;
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
					int curMode = IntegerStorage.getInteger(0, chest);
					if (curMode < 2) {
						curMode++;
					} else {
						curMode = 0;
					}
					IntegerStorage.addInteger(0, curMode, chest);
					serverPlayer.playNotifySound(SoundRegister.SOUND_JETPACKSWITCHMODE.get(), SoundSource.PLAYERS, 1, 1);
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
