package electrodynamics.common.player.armor;

import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public interface ICustomArmor extends IArmorMaterial{

	@Override
	default int getEnchantability() {
		return 0;
	}
	
	default Ingredient getRepairMaterial() {
		return Ingredient.fromStacks(new ItemStack(Items.BEDROCK));
	}
	
}
