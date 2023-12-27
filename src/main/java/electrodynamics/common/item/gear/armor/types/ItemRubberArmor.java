package electrodynamics.common.item.gear.armor.types;

import electrodynamics.api.References;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;;

public class ItemRubberArmor extends ArmorItem {

	public ItemRubberArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	public ItemRubberArmor(EquipmentSlotType slot, Properties builderIn) {
		this(ArmorMaterialRubber.rubber, slot, builderIn);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return References.ID + ":textures/model/armor/rubberarmor.png";
	}

	public enum ArmorMaterialRubber implements IArmorMaterial {
		rubber;

		@Override
		public int getDurabilityForSlot(EquipmentSlotType slotIn) {
			return 100000;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slotIn) {
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
