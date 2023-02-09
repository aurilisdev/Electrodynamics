package electrodynamics.common.event.types.living.equipmentchange;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketJetpackEquipedSound;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.network.PacketDistributor;

public class HandlerJetpackEquiped extends AbstractEquipmentChangeHandler {

	@Override
	public void handler(LivingEquipmentChangeEvent event) {
		Entity entity = event.getEntity();
		if (event.getSlot() == EquipmentSlot.CHEST && entity instanceof Player player) {
			ItemStack chest = event.getTo();
			if (event.getFrom().isEmpty() && (ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()))) {
				NetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketJetpackEquipedSound(player.getUUID()));
			}
		}

	}

}
