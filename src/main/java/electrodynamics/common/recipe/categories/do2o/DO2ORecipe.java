package electrodynamics.common.recipe.categories.do2o;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class DO2ORecipe extends ElectrodynamicsRecipe {

    private CountableIngredient INPUT_1;
    private CountableIngredient INPUT_2;
    private Ingredient[] INPUTS;
    private ItemStack OUTPUT;

    public DO2ORecipe(ResourceLocation recipeID, CountableIngredient input1, CountableIngredient input2, ItemStack output) {
	super(recipeID);
	INPUT_1 = input1;
	INPUT_2 = input2;
	INPUTS = new CountableIngredient[2];
	INPUTS[0] = INPUT_1;
	INPUTS[1] = INPUT_2;
	OUTPUT = output;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	if (INPUT_1.testStack(pr.getInput())) {
	    if (INPUT_2.testStack(pr.getSecondInput())) {
		return true;
	    }
	}
	if (INPUT_2.testStack(pr.getInput())) {
	    if (INPUT_1.testStack(pr.getSecondInput())) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
	return OUTPUT;
    }

    @Override
    public ItemStack getResultItem() {
	return OUTPUT;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.of(null, INPUT_1, INPUT_2);
    }

}
