package electrodynamics.common.item.gear.armor.types;

import electrodynamics.api.References;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemRubberArmor extends ArmorItem {

	public ItemRubberArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	public ItemRubberArmor(EquipmentSlot slot, Properties builderIn) {
		this(ArmorMaterialRubber.rubber, slot, builderIn);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return References.ID + ":textures/model/armor/rubberarmor.png";
	}

	public enum ArmorMaterialRubber implements ArmorMaterial {
		rubber;

		@Override
		public int getDurabilityForSlot(EquipmentSlot slotIn) {
			return 100000;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot slotIn) {
			return 2;
		}

		@Override
		public int getEnchantmentValue() {
			return 0;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_LEATHER;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(ElectrodynamicsItems.ITEM_INSULATION.get());
		}

		@Override
		public String getName() {
			return super.name();
		}

		@Override
		public float getToughness() {
			return 0;
		}

		@Override
		public float getKnockbackResistance() {
			return 0;
		}

	}
}
