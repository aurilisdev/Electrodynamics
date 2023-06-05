package electrodynamics.common.packet.types.server;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketModeSwitchServer {

	private final UUID playerId;
	private final Mode mode;

	public PacketModeSwitchServer(UUID uuid, Mode mode) {
		playerId = uuid;
		this.mode = mode;
	}

	public static void handle(PacketModeSwitchServer message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel serverWorld = context.get().getSender().getLevel();
			if (serverWorld != null) {
				ServerPlayer serverPlayer = (ServerPlayer) serverWorld.getPlayerByUUID(message.playerId);
				switch (message.mode) {
				case JETPACK:
					ItemStack chest = serverPlayer.getItemBySlot(EquipmentSlot.CHEST);
					if (ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
						CompoundTag tag = chest.getOrCreateTag();
						int curMode = tag.getInt(NBTUtils.MODE);
						if (curMode < 3) {
							curMode++;
						} else {
							curMode = 0;
						}
						tag.putInt(NBTUtils.MODE, curMode);
						serverPlayer.playNotifySound(ElectrodynamicsSounds.SOUND_JETPACKSWITCHMODE.get(), SoundSource.PLAYERS, 1, 1);
					}
					break;
				case SERVOLEGS:
					ItemStack legs = serverPlayer.getItemBySlot(EquipmentSlot.LEGS);
					if (ItemUtils.testItems(legs.getItem(), ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get()) || ItemUtils.testItems(legs.getItem(), ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get())) {
						CompoundTag tag = legs.getOrCreateTag();
						int curMode = tag.getInt(NBTUtils.MODE);
						if (curMode < 2) {
							curMode++;
						} else {
							curMode = 0;
						}
						tag.putInt(NBTUtils.MODE, curMode);
						serverPlayer.playNotifySound(ElectrodynamicsSounds.SOUND_HYDRAULICBOOTS.get(), SoundSource.PLAYERS, 1, 1);
					}
					break;
				default:
					break;
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketModeSwitchServer message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
		buf.writeEnum(message.mode);
	}

	public static PacketModeSwitchServer decode(FriendlyByteBuf buf) {
		return new PacketModeSwitchServer(buf.readUUID(), buf.readEnum(Mode.class));
	}

	// Mekanism gave me this idea
	public enum Mode {
		JETPACK,
		SERVOLEGS;
	}

}
