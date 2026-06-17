package electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import voltaic.api.gas.GasStack;
import voltaic.common.recipe.recipeutils.GasIngredient;

public class PsuedoGas2FluidRecipe {

    public List<GasIngredient> inputs;
    public FluidStack output;

    public ItemStack inputCylinder;
    public ItemStack outputBucket;

    public PsuedoGas2FluidRecipe(List<GasStack> inputs, FluidStack output, ItemStack inputCylinder,
	    ItemStack outputBucket) {
	this.inputs = new ArrayList<>();
	for (GasStack stack : inputs) {
	    this.inputs.add(new GasIngredient(stack));
	}

	this.output = output;

	this.inputCylinder = inputCylinder;
	this.outputBucket = outputBucket;
    }

}
