package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.api.item.nbtutils.BooleanStorage;
import electrodynamics.common.item.gear.armor.types.ItemNightVisionGoggles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketNightVisionGoggles {

	private final UUID playerId;

	public PacketNightVisionGoggles(UUID uuid) {
		playerId = uuid;
	}

	public static void handle(PacketNightVisionGoggles message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if (world != null) {
				Player player = world.getPlayerByUUID(message.playerId);
				ItemStack playerHead = player.getItemBySlot(EquipmentSlot.HEAD);
				if (ItemUtils.testItems(playerHead.getItem(), DeferredRegisters.ITEM_NIGHTVISIONGOGGLES.get())) {
					BooleanStorage.addBoolean(0, !BooleanStorage.getBoolean(0, playerHead), playerHead);
					if (((ItemNightVisionGoggles) playerHead.getItem()).getJoulesStored(playerHead) >= ItemNightVisionGoggles.JOULES_PER_TICK) {
						if (BooleanStorage.getBoolean(0, playerHead)) {
							player.playNotifySound(SoundRegister.SOUND_NIGHTVISIONGOGGLESON.get(), SoundSource.PLAYERS, 1, 1);
						} else {
							player.playNotifySound(SoundRegister.SOUND_NIGHTVISIONGOGGLESOFF.get(), SoundSource.PLAYERS, 1, 1);
						}
					}

				}
			}
		});
	}

	public static void encode(PacketNightVisionGoggles message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
	}

	public static PacketNightVisionGoggles decode(FriendlyByteBuf buf) {
		return new PacketNightVisionGoggles(buf.readUUID());
	}
}
