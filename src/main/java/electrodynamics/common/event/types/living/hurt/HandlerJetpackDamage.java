package electrodynamics.common.event.types.living.hurt;

import java.util.ArrayList;

import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class HandlerJetpackDamage extends AbstractLivingHurtHandler {

	// this way we know the impulse was because of the player being hurt
	@Override
	public void handle(LivingHurtEvent event) {

		Entity entity = event.getEntity();
		ArrayList<ItemStack> armor = new ArrayList<>();
		entity.getArmorSlots().forEach(armor::add);
		if (armor.size() < 3) {
			return;
		}
		ItemStack chestplate = armor.get(3);
		
		if(chestplate.isEmpty() || !ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
			return;
		}

		chestplate.getOrCreateTag().putBoolean(ItemJetpack.WAS_HURT_KEY, true);

	}

}
