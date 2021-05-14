package electrodynamics.compatability.jei.recipecategories.psuedorecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class Psuedo5XRecipe {

    public Ingredient INPUT_ORE;
    public Ingredient MACHINE_1;
    public Ingredient MACHINE_2;
    public FluidStack INPUT_FLUID;
    public FluidStack ORE_FLUID_SUBPART;
    public ItemStack OUTPUT_CRYSTALS;

    public Psuedo5XRecipe(ItemStack inputOre, ItemStack machine1, ItemStack machine2, FluidStack inputFluid, FluidStack oreFluid,
	    ItemStack outputCrystals) {
	INPUT_ORE = Ingredient.fromStacks(inputOre);
	MACHINE_1 = Ingredient.fromStacks(machine1);
	MACHINE_2 = Ingredient.fromStacks(machine2);
	INPUT_FLUID = inputFluid;
	ORE_FLUID_SUBPART = oreFluid;
	OUTPUT_CRYSTALS = outputCrystals;
    }
}
