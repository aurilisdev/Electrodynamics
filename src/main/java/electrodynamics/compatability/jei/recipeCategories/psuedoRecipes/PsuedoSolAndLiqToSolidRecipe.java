package electrodynamics.compatability.jei.recipeCategories.psuedoRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoSolAndLiqToSolidRecipe {

	public Ingredient ITEM_INPUT;
	public Ingredient FLUID_BUCKET_INPUT;
	public FluidStack FLUID_STACK_INPUT;
	public ItemStack ITEM_OUTPUT;
	
	public PsuedoSolAndLiqToSolidRecipe(ItemStack itemInput,ItemStack fluidBucketInput,FluidStack inputFluid,ItemStack itemOutput) {
		ITEM_INPUT = Ingredient.fromStacks(itemInput);
		FLUID_BUCKET_INPUT = Ingredient.fromStacks(fluidBucketInput);
		FLUID_STACK_INPUT = inputFluid;
		ITEM_OUTPUT = itemOutput;
	}
}
