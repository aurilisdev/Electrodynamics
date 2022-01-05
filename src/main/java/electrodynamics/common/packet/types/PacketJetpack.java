package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketJetpack {
	
	private final int scenario;
	private final UUID playerId;
	
	public PacketJetpack(int scenario, UUID uuid) {
		this.scenario = scenario;
		this.playerId = uuid;
	}
	
	public static void handle(PacketJetpack message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if(world != null) {
				switch(message.scenario) {
				case 1:
					Player player = world.getPlayerByUUID(message.playerId);
					ItemStack playerChest = player.getItemBySlot(EquipmentSlot.CHEST);
					if(ItemUtils.testItems(playerChest.getItem(), DeferredRegisters.ITEM_JETPACK.get())) {
						boolean enoughFuel = playerChest.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> m.getFluidInTank(0).getAmount() >= ItemJetpack.USAGE_PER_TICK).orElse(false);
						if(enoughFuel) {
							
							//player.sendMessage(new TextComponent("Recieved Packet!"), Util.NIL_UUID);
						}
					}
				default:
				}
			}
		});
	}
	
	public static void encode(PacketJetpack message, FriendlyByteBuf buf) {
		buf.writeInt(message.scenario);
		buf.writeUUID(message.playerId);
	}
	
	public static PacketJetpack decode(FriendlyByteBuf buf) {
		return new PacketJetpack(buf.readInt(), buf.readUUID());
	}

}
