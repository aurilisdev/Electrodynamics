package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.item.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketModeSwitch {
	
	private final UUID playerId;
	
	public PacketModeSwitch(UUID uuid) {
		this.playerId = uuid;
	}
	
	public static void handle(PacketModeSwitch message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if(world != null) {
					Player player = world.getPlayerByUUID(message.playerId);
					ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
					if(ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get())) {
						boolean sucessful = chest.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m ->{
							int curMode = m.getInt(0);
							if(curMode < 2) {
								curMode ++;
							} else {
								curMode = 0;
							}
							m.setInt(0, curMode);
							return true;
						}).orElse(false);
						if(sucessful) {
							player.playNotifySound(SoundRegister.SOUND_JETPACKSWITCHMODE.get(), SoundSource.PLAYERS, 1, 1);
						}
					}
			}
		});
	}
	
	public static void encode(PacketModeSwitch message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
	}
	
	public static PacketModeSwitch decode(FriendlyByteBuf buf) {
		return new PacketModeSwitch(buf.readUUID());
	}

}
