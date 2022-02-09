package electrodynamics.common.item.gear.armor.types;

import electrodynamics.api.References;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.prefab.item.ElectricItemProperties;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public class ItemServoLeggings extends ArmorItem implements IItemElectric {

	//TODO WIP
	
	final ElectricItemProperties properties;
	
	public ItemServoLeggings(ElectricItemProperties pProperties) {
		super(ServoLeggings.SERVOLEGGINGS, EquipmentSlot.LEGS, pProperties);
		properties = pProperties;
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}
	
	public enum ServoLeggings implements ICustomArmor {
		SERVOLEGGINGS;

		@Override
		public int getDurabilityForSlot(EquipmentSlot slotIn) {
			return 100;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot slotIn) {
			return 1;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_IRON;
		}

		@Override
		public String getName() {
			return References.ID + ":servoleggings";
		}

		@Override
		public float getToughness() {
			return 0.0F;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.0F;
		}

	}

}
