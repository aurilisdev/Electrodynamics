package electrodynamics.common.recipe.categories.fluid2fluid;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fluids.FluidStack;

public class Fluid2FluidRecipeSerializer<T extends Fluid2FluidRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public Fluid2FluidRecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
	FluidIngredient fluidInput = FluidIngredient.deserialize(GsonHelper.getAsJsonObject(json, "fluid_input"));
	FluidStack fluidOutput = FluidIngredient.deserialize(GsonHelper.getAsJsonObject(json, "fluid_output")).getFluidStack();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient.class,
		    FluidStack.class);
	    return recipeConstructor.newInstance(recipeId, fluidInput, fluidOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
	FluidIngredient fluidInput = FluidIngredient.read(buffer);
	FluidStack fluidOutput = FluidIngredient.read(buffer).getFluidStack();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient.class,
		    FluidStack.class);
	    return recipeConstructor.newInstance(recipeId, fluidInput, fluidOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
	FluidIngredient fluidInput = (FluidIngredient) recipe.getIngredients().get(0);
	FluidIngredient fluidOutput = new FluidIngredient(recipe.getFluidRecipeOutput());
	fluidInput.writeStack(buffer);
	fluidOutput.writeStack(buffer);
    }
}
