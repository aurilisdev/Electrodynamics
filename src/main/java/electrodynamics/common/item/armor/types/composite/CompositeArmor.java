package electrodynamics.common.item.armor.types.composite;

import electrodynamics.api.References;
import electrodynamics.common.item.armor.ICustomArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum CompositeArmor implements ICustomArmor{
	COMPOSITE_ARMOR(References.ID + ":composite",new int[] { 3, 6, 8, 3 },SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,2.0f);

	
	private final String name;
	private final int[] damageReductionAmountArray;
	private final SoundEvent soundEvent;
	private final float toughness;
	
	// Constructor
    private CompositeArmor(String name, int[] damageReductionAmountArray, SoundEvent soundEvent, float toughness){
        this.name = name;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
    }
	
	@Override
	public int getDurability(EquipmentSlotType slotIn) {
		return 1600;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return 4;
	}

}
