package electrodynamics.compatability.jei.recipeCategories.psuedoRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoSolAndLiqToLiquidRecipe {
	
	public Ingredient ITEM_INPUT;
	public Ingredient FLUID_BUCKET_INPUT;
	public FluidStack FLUID_STACK_INPUT;
	public FluidStack FLUID_BUCKET_OUTPUT;
	public int BAR_NUMBER;
	
	
	
	//BAR NUMBERS
	public final static int SULFURIC_ACID = 1;
	public final static int UHEXFLOUR = 2;
	public final static int ETHANOL = 3;
	
	
	
	
	public PsuedoSolAndLiqToLiquidRecipe(ItemStack itemInput,ItemStack fluidBucketInput,
			FluidStack inputFluid,FluidStack fluidBucketOutput, int barNumber) {
		ITEM_INPUT = Ingredient.fromStacks(itemInput);
		FLUID_BUCKET_INPUT = Ingredient.fromStacks(fluidBucketInput);
		FLUID_STACK_INPUT = inputFluid;
		FLUID_BUCKET_OUTPUT = fluidBucketOutput;
		BAR_NUMBER = barNumber;
	}

}
