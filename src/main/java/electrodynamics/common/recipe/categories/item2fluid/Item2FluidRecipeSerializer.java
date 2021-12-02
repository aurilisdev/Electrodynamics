package electrodynamics.common.recipe.categories.item2fluid;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class Item2FluidRecipeSerializer<T extends Item2FluidRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public Item2FluidRecipeSerializer(Class<T> recipeClass) {
    	super(recipeClass);
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject recipeJson) {
    	CountableIngredient[] inputs = getItemIngredients(recipeJson);
		FluidStack output = getFluidOutput(recipeJson);
		if(recipeJson.has(ITEM_BIPRODUCTS)) {
			ProbableItem[] itemBi = getItemBiproducts(recipeJson);
			if(recipeJson.has(FLUID_BIPRODUCTS)) {
				ProbableFluid[] fluidBi = getFluidBiproducts(recipeJson);
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
						ResourceLocation.class,	
						CountableIngredient[].class,
						FluidStack.class,
						ProbableItem[].class,
						ProbableFluid[].class
					);
					return recipeConstructor.newInstance(recipeId, inputs, output, itemBi, fluidBi);
				} catch(Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			} else {
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
						ResourceLocation.class,	
						CountableIngredient[].class,
						FluidStack.class,
						ProbableItem[].class
					);
					return recipeConstructor.newInstance(recipeId, inputs, output, itemBi);
				} catch(Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			}
		} else if (recipeJson.has(FLUID_BIPRODUCTS)){
			ProbableFluid[] fluidBi = getFluidBiproducts(recipeJson);
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
					CountableIngredient[].class,
					FluidStack.class,
					ProbableFluid[].class,
					ResourceLocation.class
				);
				return recipeConstructor.newInstance(inputs, output, fluidBi, recipeId);
			} catch(Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		} else {
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
					ResourceLocation.class,	
					CountableIngredient[].class,
					FluidStack.class
				);
				return recipeConstructor.newInstance(recipeId, inputs, output);
			} catch(Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		}
		Electrodynamics.LOGGER.info("returning null");
		return null;
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
    	boolean hasItemBi = buffer.readBoolean();
		boolean hasFluidBi = buffer.readBoolean();
		CountableIngredient[] inputs = CountableIngredient.readList(buffer);
		FluidStack output = buffer.readFluidStack();
		if(hasItemBi) {
			ProbableItem[] itemBi = ProbableItem.readList(buffer);
			if(hasFluidBi) {
				ProbableFluid[] fluidBi = ProbableFluid.readList(buffer);
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
						ResourceLocation.class,	
						CountableIngredient[].class,
						FluidStack.class,
						ProbableItem[].class,
						ProbableFluid[].class
					);
					return recipeConstructor.newInstance(recipeId, inputs, output, itemBi, fluidBi);
				} catch(Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			} else {
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
						ResourceLocation.class,	
						CountableIngredient[].class,
						FluidStack.class,
						ProbableItem[].class
					);
					return recipeConstructor.newInstance(recipeId, inputs, output, itemBi);
				} catch(Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			}
		} else if (hasFluidBi){
			ProbableFluid[] fluidBi = ProbableFluid.readList(buffer);
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
					CountableIngredient[].class,
					FluidStack.class,
					ProbableFluid[].class,
					ResourceLocation.class
				);
				return recipeConstructor.newInstance(inputs, output, fluidBi, recipeId);
			} catch(Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		} else {
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
					ResourceLocation.class,	
					CountableIngredient[].class,
					FluidStack.class
				);
				return recipeConstructor.newInstance(recipeId, inputs, output);
			} catch(Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		}
		Electrodynamics.LOGGER.info("returning null");
		return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
    	buffer.writeBoolean(recipe.hasItemBiproducts());
		buffer.writeBoolean(recipe.hasFluidBiproducts());
		CountableIngredient.writeList(buffer, recipe.getCountedIngredients());
		buffer.writeFluidStack(recipe.getFluidRecipeOutput());
		if(recipe.hasItemBiproducts()) {
			ProbableItem.writeList(buffer, recipe.getItemBiproducts());
		}
		if(recipe.hasFluidBiproducts()) {
			ProbableFluid.writeList(buffer, recipe.getFluidBiproducts());
		}
    }

}
