package electrodynamics.common.event.types.player.starttracking;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketJetpackEquipedSound;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.network.NetworkDirection;

public class HandlerJetpackSound extends AbstractPlayerStartTrackingHandler {

	@Override
	public void handle(StartTracking event) {
		Entity entity = event.getTarget();
		if (entity instanceof Player player) {
			ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
			if (!chest.isEmpty() && (ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()))) {
				ServerPlayer server = (ServerPlayer) event.getEntity();
				NetworkHandler.CHANNEL.sendTo(new PacketJetpackEquipedSound(player.getUUID()), server.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
			}
		}
	}

}
