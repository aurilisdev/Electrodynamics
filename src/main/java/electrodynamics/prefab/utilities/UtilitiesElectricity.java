package electrodynamics.prefab.utilities;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.damage.DamageSources;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class UtilitiesElectricity {
	public static void electrecuteEntity(Entity entityIn, TransferPack transfer) {
		if (transfer.getVoltage() <= 960.0) {
			for (ItemStack armor : entityIn.getArmorSlots()) {
				if (armor.getItem() == DeferredRegisters.ITEM_RUBBERBOOTS.get()) {
					float damage = (float) transfer.getAmps() / 10.0f;
					if (Math.random() < damage) {
						int integerDamage = (int) Math.max(1, damage);
						if (armor.getDamageValue() > armor.getMaxDamage() || armor.hurt(integerDamage, entityIn.level.random, null)) {
							armor.setCount(0);
						}
					}
					return;
				}
			}
		}
		entityIn.hurt(DamageSources.ELECTRICITY, (float) Math.min(9999, Math.max(0, transfer.getAmps())));
	}
}
