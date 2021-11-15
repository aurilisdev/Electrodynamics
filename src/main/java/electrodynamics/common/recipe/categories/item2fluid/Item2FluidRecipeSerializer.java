package electrodynamics.common.recipe.categories.item2fluid;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fluids.FluidStack;

public class Item2FluidRecipeSerializer<T extends Item2FluidRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public Item2FluidRecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
	CountableIngredient itemInput = CountableIngredient.deserialize(GsonHelper.getAsJsonObject(json, "item_input"));
	FluidStack fluidOutput = FluidIngredient.deserialize(GsonHelper.getAsJsonObject(json, "fluid_output")).getFluidStack();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient.class,
		    FluidStack.class);
	    return recipeConstructor.newInstance(recipeId, itemInput, fluidOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
	CountableIngredient itemInput = CountableIngredient.read(buffer);
	FluidStack fluidOutput = FluidIngredient.read(buffer).getFluidStack();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient.class,
		    FluidStack.class);
	    return recipeConstructor.newInstance(recipeId, itemInput, fluidOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
	CountableIngredient itemInput = (CountableIngredient) recipe.getIngredients().get(0);
	FluidIngredient fluidOutput = new FluidIngredient(recipe.getFluidRecipeOutput());
	itemInput.writeStack(buffer);
	fluidOutput.write(buffer);
    }

}
