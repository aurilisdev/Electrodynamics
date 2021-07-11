package electrodynamics.common.player.armor.types.composite;

import electrodynamics.api.References;
import electrodynamics.common.player.armor.ICustomArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum CompositeArmor implements ICustomArmor {
    COMPOSITE_ARMOR(References.ID + ":composite", new int[] { 2, 6, 8, 2 }, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0f);

    private final String name;
    private final int[] damageReductionAmountArray;
    private final SoundEvent soundEvent;
    private final float toughness;

    // Constructor
    private CompositeArmor(String name, int[] damageReductionAmountArray, SoundEvent soundEvent, float toughness) {
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
	return damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public SoundEvent getSoundEvent() {
	return soundEvent;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public float getToughness() {
	return toughness;
    }

    @Override
    public float getKnockbackResistance() {
	return 4;
    }

}
