package electrodynamics.common.recipe.categories.item2fluid;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class Item2FluidRecipeSerializer<T extends Item2FluidRecipe> extends ElectrodynamicsRecipeSerializer<T>{

	public Item2FluidRecipeSerializer(Class<T> recipeClass) {
		super(recipeClass);
	}

	@Override
	public T read(ResourceLocation recipeId, JsonObject json) {
		CountableIngredient itemInput = CountableIngredient.deserialize(JSONUtils.getJsonObject(json, "item_input"));
		FluidStack fluidOutput = FluidIngredient.deserialize(JSONUtils.getJsonObject(json, "fluid_output")).getFluidStack();
		
		try {
			Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(new Class[] {ResourceLocation.class,CountableIngredient.class,FluidStack.class});
			return recipeConstructor.newInstance(new Object[]{recipeId,itemInput,fluidOutput});
		}
		catch(Exception e){
			ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
			return null;
		}
	}

	@Override
	public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		CountableIngredient itemInput = CountableIngredient.read(buffer);
		FluidStack fluidOutput = FluidIngredient.read(buffer).getFluidStack();
	
		try {
			Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(new Class[] {ResourceLocation.class,CountableIngredient.class,FluidStack.class});
			return recipeConstructor.newInstance(new Object[]{recipeId,itemInput,fluidOutput});
		}
		catch(Exception e){
			ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
			return null;
		}
	}

	@Override
	public void write(PacketBuffer buffer, T recipe) {
		CountableIngredient itemInput = (CountableIngredient)recipe.getIngredients().get(0);
		FluidIngredient fluidOutput = new FluidIngredient(recipe.getFluidRecipeOutput());
		itemInput.writeStack(buffer);
		fluidOutput.writeStack(buffer);
	}

}
