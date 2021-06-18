package electrodynamics.common.recipe.categories.o2o;


import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class O2ORecipe extends ElectrodynamicsRecipe{
	
	private CountableIngredient INPUT;
	private ItemStack OUTPUT;
	
	
	public O2ORecipe(ResourceLocation id, CountableIngredient input, ItemStack output) {
		super(id);
		INPUT = input;
		OUTPUT = output;
	}

	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {
		if(this.INPUT.testStack(pr.getInput())) {
			return true;
		}
		return false;
	}
	
	public boolean matchesRecipe(ItemStack stack) {
		if(this.INPUT.testStack(stack)) {
			return true;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return this.OUTPUT;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.OUTPUT;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients(){
		return NonNullList.from(null,INPUT);
	}
	
}
