package electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

public class MineralWasherRecipe extends FluidItem2FluidRecipe {

	public static final String RECIPE_GROUP = "mineral_washer_recipe";
	public static final String MOD_ID = electrodynamics.api.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public MineralWasherRecipe(ResourceLocation recipeID, CountableIngredient[] inputItem, FluidIngredient[] inputFluid, 
			FluidStack outputFluid, double experience) {
		super(recipeID, inputItem, inputFluid, outputFluid, experience);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipeInit.MINERAL_WASHER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return Registry.RECIPE_TYPE.get(RECIPE_ID);
	}

}
