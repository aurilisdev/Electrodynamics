package electrodynamics.common.item.gear.armor;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public interface ICustomArmor extends ArmorMaterial {

	@Override
	default int getEnchantmentValue() {
		return 0;
	}

	@Override
	default Ingredient getRepairIngredient() {
		return Ingredient.of(new ItemStack(Items.BEDROCK));
	}

}
