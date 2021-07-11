package electrodynamics.client.render.armormodel;

import net.minecraft.inventory.EquipmentSlotType;

public interface ISidedReference {

    default <A> A getCustomArmorModel(EquipmentSlotType armorSlot) {
	return null;
    }

}
