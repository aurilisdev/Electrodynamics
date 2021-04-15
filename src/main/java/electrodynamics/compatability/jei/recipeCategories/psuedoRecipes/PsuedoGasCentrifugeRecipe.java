package electrodynamics.compatability.jei.recipeCategories.psuedoRecipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoGasCentrifugeRecipe {
	
	public FluidStack INPUT_FLUID_STACK;
	public ItemStack OUTPUT_1_ITEM;
	public ItemStack OUTPUT_2_ITEM;
	
	public PsuedoGasCentrifugeRecipe(FluidStack inputFluid, ItemStack output1, ItemStack output2) {
		INPUT_FLUID_STACK = inputFluid;
		OUTPUT_1_ITEM = output1;
		OUTPUT_2_ITEM = output2;
	}

}
