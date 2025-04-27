package electrodynamics.common.item.gear.armor.types;

import java.util.function.Supplier;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import voltaic.common.item.gear.ItemVoltaicArmor;

public class ItemRubberArmor extends ItemVoltaicArmor {

	public ItemRubberArmor(Type slot, Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(ArmorMaterialRubber.rubber, slot, properties, creativeTab);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return Electrodynamics.ID + ":textures/model/armor/rubberarmor.png";
	}

	public enum ArmorMaterialRubber implements ArmorMaterial {
		rubber;

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

		@Override
		public int getDurabilityForType(Type pType) {
			return 100000;
		}

		@Override
		public int getDefenseForType(Type pType) {
			return 2;
		}

	}
}
