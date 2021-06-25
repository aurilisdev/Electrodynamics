package electrodynamics.compatability.jei.recipecategories.psuedorecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;

public class Psuedo5XRecipe extends O2ORecipe{

    public Ingredient INPUT_ORE;
    public Ingredient MACHINE_1;
    public Ingredient MACHINE_2;
    public FluidStack INPUT_FLUID;
    public FluidStack ORE_FLUID_SUBPART;
    public ItemStack OUTPUT_CRYSTALS;

    public Psuedo5XRecipe(ItemStack inputOre, ItemStack machine1, ItemStack machine2, FluidStack inputFluid, FluidStack oreFluid,
	    ItemStack outputCrystals) {
    	super(null,null,null);
		INPUT_ORE = Ingredient.fromStacks(inputOre);
		MACHINE_1 = Ingredient.fromStacks(machine1);
		MACHINE_2 = Ingredient.fromStacks(machine2);
		INPUT_FLUID = inputFluid;
		ORE_FLUID_SUBPART = oreFluid;
		OUTPUT_CRYSTALS = outputCrystals;
    }

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return null;
	}

	@Override
	public IRecipeType<?> getType() {
		return null;
	}
}
