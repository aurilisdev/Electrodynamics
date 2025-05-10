package electrodynamics.common.recipe.categories.fluid2item.specificmachines;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import voltaic.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class ChemicalCrystalizerRecipe extends Fluid2ItemRecipe {

	public static final String RECIPE_GROUP = "chemical_crystallizer_recipe";
	public static final ResourceLocation RECIPE_ID = Electrodynamics.rl(RECIPE_GROUP);

	public ChemicalCrystalizerRecipe(ResourceLocation group, List<FluidIngredient> fluidInputs, ItemStack itemOutput, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts) {
		super(group, fluidInputs, itemOutput, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts);

	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipies.CHEMICAL_CRYSTALIZER_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return ElectrodynamicsRecipies.CHEMICAL_CRYSTALIZER_TYPE;
	}

}
