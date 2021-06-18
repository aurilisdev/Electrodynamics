package electrodynamics.common.recipe.categories.do2o;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class DO2ORecipe extends ElectrodynamicsRecipe {

	private CountableIngredient INPUT_1;
	private CountableIngredient INPUT_2;
	private Ingredient[] INPUTS;
	private ItemStack OUTPUT;
	
	public DO2ORecipe(ResourceLocation recipeID, CountableIngredient input1, CountableIngredient input2, ItemStack output) {
		super(recipeID);
		this.INPUT_1 = input1;
		this.INPUT_2 = input2;
		this.INPUTS  = new CountableIngredient[2];
		this.INPUTS[0] = INPUT_1;
		this.INPUTS[1] = INPUT_2;
		this.OUTPUT = output;
	}

	/*
	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		if(this.INPUT_1.testStack(inv.getStackInSlot(0))) {
			if(this.INPUT_2.testStack(inv.getStackInSlot(1))) {
				return true;
			}
		}
		if(this.INPUT_2.testStack(inv.getStackInSlot(0))) {
			if(this.INPUT_1.testStack(inv.getStackInSlot(1))) {
				return true;
			}
		}
		return false;
	}
	*/
	
	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {
		if(this.INPUT_1.testStack(pr.getInput())) {
			if(this.INPUT_2.testStack(pr.getSecondInput())) {
				return true;
			}
		}
		if(this.INPUT_2.testStack(pr.getInput())) {
			if(this.INPUT_1.testStack(pr.getSecondInput())) {
				return true;
			}
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
		return NonNullList.from(null, INPUT_1,INPUT_2);
	}

}
