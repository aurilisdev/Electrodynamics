package electrodynamics.client.render.armormodel.armorcomposite;

import electrodynamics.client.render.armormodel.ISidedReference;
import electrodynamics.common.item.armor.types.composite.CompositeArmorModel;
import net.minecraft.inventory.EquipmentSlotType;

public class SidedCompositeArmorModel implements ISidedReference {

    private final CompositeArmorModel regularModel = new CompositeArmorModel(1f);
    private final CompositeArmorModel legsModel = new CompositeArmorModel(0.5f);

    @SuppressWarnings("unchecked")
    @Override
    public <A> A getCustomArmorModel(EquipmentSlotType armorSlot) {
	return (A) (armorSlot == EquipmentSlotType.LEGS ? legsModel : regularModel);
    }
}
