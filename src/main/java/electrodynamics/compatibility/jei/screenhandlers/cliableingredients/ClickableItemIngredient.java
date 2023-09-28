package electrodynamics.compatibility.jei.screenhandlers.cliableingredients;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;

public class ClickableItemIngredient extends AbstractClickableIngredient<ItemStack> {

	private final ItemIngredientType ingredientType;

	public ClickableItemIngredient(Rect2i rect, ItemStack stack) {
		super(rect);
		ingredientType = new ItemIngredientType(stack);
	}

	@Override
	public ITypedIngredient<ItemStack> getTypedIngredient() {
		return ingredientType;
	}

	private static class ItemIngredientType implements ITypedIngredient<ItemStack> {

		private final ItemStack itemStack;

		public ItemIngredientType(ItemStack itemStack) {
			this.itemStack = itemStack;
		}

		@Override
		public IIngredientType<ItemStack> getType() {
			return VanillaTypes.ITEM_STACK;
		}

		@Override
		public ItemStack getIngredient() {
			return itemStack;
		}

	}

}
