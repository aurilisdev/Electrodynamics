package electrodynamics.common.item.gear.armor.types.composite;

import electrodynamics.SoundRegister;
import electrodynamics.api.References;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;

public enum CompositeArmor implements ICustomArmor {
    COMPOSITE_ARMOR(References.ID + ":composite", new int[] { 3, 6, 8, 3 }, 2.0f);

    private final String name;
    private final int[] damageReductionAmountArray;
    private final float toughness;

    // Constructor
    CompositeArmor(String name, int[] damageReductionAmountArray, float toughness) {
	this.name = name;
	this.damageReductionAmountArray = damageReductionAmountArray;
	this.toughness = toughness;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slotIn) {
	return 1600;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slotIn) {
	return damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public SoundEvent getEquipSound() {
	return SoundRegister.SOUND_EQUIPHEAVYARMOR.get();
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
