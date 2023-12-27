package electrodynamics.common.item.gear.tools.electric.utils;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum ElectricItemTier implements IItemTier {

	DRILL(3, 0, ItemTier.GOLD.getSpeed() * 3.5f, ItemTier.IRON.getAttackDamageBonus() * 1.6f, 5);

	private final int harvestLevel;
	private final float efficency;
	private final float attackDammage;
	private final int enchantability;

	ElectricItemTier(int harvestLevel, int maxUses, float efficency, float attackDammage, int enchantability) {
		this.harvestLevel = harvestLevel;
		this.efficency = efficency;
		this.attackDammage = attackDammage;
		this.enchantability = enchantability;
	}

	@Override
	public int getUses() {
		return 0;
	}

	@Override
	public float getSpeed() {
		return efficency;
	}

	@Override
	public float getAttackDamageBonus() {
		return attackDammage;
	}

	@Override
	public int getLevel() {
		return harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.EMPTY;
	}

	public String tag() {
		return name().toLowerCase();
	}

}
