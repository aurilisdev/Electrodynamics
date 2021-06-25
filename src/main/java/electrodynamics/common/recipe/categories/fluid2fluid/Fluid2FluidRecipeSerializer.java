package electrodynamics.common.recipe.categories.fluid2fluid;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class Fluid2FluidRecipeSerializer<T extends Fluid2FluidRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public Fluid2FluidRecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {
	FluidIngredient fluidInput = FluidIngredient.deserialize(JSONUtils.getJsonObject(json, "fluid_input"));
	FluidStack fluidOutput = FluidIngredient.deserialize(JSONUtils.getJsonObject(json, "fluid_output")).getFluidStack();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass()
		    .getDeclaredConstructor(new Class[] { ResourceLocation.class, FluidIngredient.class, FluidStack.class });
	    return recipeConstructor.newInstance(new Object[] { recipeId, fluidInput, fluidOutput });
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
	FluidIngredient fluidInput = FluidIngredient.read(buffer);
	FluidStack fluidOutput = FluidIngredient.read(buffer).getFluidStack();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass()
		    .getDeclaredConstructor(new Class[] { ResourceLocation.class, FluidIngredient.class, FluidStack.class });
	    return recipeConstructor.newInstance(new Object[] { recipeId, fluidInput, fluidOutput });
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
	FluidIngredient fluidInput = (FluidIngredient) recipe.getIngredients().get(0);
	FluidIngredient fluidOutput = new FluidIngredient(recipe.getFluidRecipeOutput());
	fluidInput.writeStack(buffer);
	fluidOutput.writeStack(buffer);
    }
}
