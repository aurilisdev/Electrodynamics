package electrodynamics.common.recipe.categories.fluid2fluid;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class Fluid2FluidRecipeSerializer<T extends Fluid2FluidRecipe> extends ElectrodynamicsRecipeSerializer<T> {

	public Fluid2FluidRecipeSerializer(Class<T> recipeClass) {
		super(recipeClass);
	}

	@Override
	public T fromJson(ResourceLocation recipeId, JsonObject recipeJson) {
		FluidIngredient[] inputs = getFluidIngredients(recipeJson);
		FluidStack output = getFluidOutput(recipeJson);
		double experience = getExperience(recipeJson);
		if (recipeJson.has(ITEM_BIPRODUCTS)) {
			ProbableItem[] itemBi = getItemBiproducts(recipeJson);
			if (recipeJson.has(FLUID_BIPRODUCTS)) {
				ProbableFluid[] fluidBi = getFluidBiproducts(recipeJson);
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class, FluidStack.class, ProbableItem[].class, ProbableFluid[].class, double.class);
					return recipeConstructor.newInstance(recipeId, inputs, output, itemBi, fluidBi, experience);
				} catch (Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			} else {
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class, FluidStack.class, ProbableItem[].class, double.class);
					return recipeConstructor.newInstance(recipeId, inputs, output, itemBi, experience);
				} catch (Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			}
		} else if (recipeJson.has(FLUID_BIPRODUCTS)) {
			ProbableFluid[] fluidBi = getFluidBiproducts(recipeJson);
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(FluidIngredient[].class, FluidStack.class, ProbableFluid[].class, ResourceLocation.class, double.class);
				return recipeConstructor.newInstance(inputs, output, fluidBi, recipeId, experience);
			} catch (Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		} else {
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class, FluidStack.class, double.class);
				return recipeConstructor.newInstance(recipeId, inputs, output, experience);
			} catch (Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		}
		Electrodynamics.LOGGER.info("returning null at " + recipeId);
		return null;
	}

	@Override
	public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
		boolean hasItemBi = buffer.readBoolean();
		boolean hasFluidBi = buffer.readBoolean();
		FluidIngredient[] inputs = FluidIngredient.readList(buffer);
		FluidStack output = buffer.readFluidStack();
		double experience = buffer.readDouble();
		if (hasItemBi) {
			ProbableItem[] itemBi = ProbableItem.readList(buffer);
			if (hasFluidBi) {
				ProbableFluid[] fluidBi = ProbableFluid.readList(buffer);
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class, FluidStack.class, ProbableItem[].class, ProbableFluid[].class, double.class);
					return recipeConstructor.newInstance(recipeId, inputs, output, itemBi, fluidBi, experience);
				} catch (Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			} else {
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class, FluidStack.class, ProbableItem[].class, double.class);
					return recipeConstructor.newInstance(recipeId, inputs, output, itemBi, experience);
				} catch (Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			}
		} else if (hasFluidBi) {
			ProbableFluid[] fluidBi = ProbableFluid.readList(buffer);
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(FluidIngredient[].class, FluidStack.class, ProbableFluid[].class, ResourceLocation.class, double.class);
				return recipeConstructor.newInstance(inputs, output, fluidBi, recipeId, experience);
			} catch (Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		} else {
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class, FluidStack.class, double.class);
				return recipeConstructor.newInstance(recipeId, inputs, output, experience);
			} catch (Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		}
		Electrodynamics.LOGGER.info("returning null at " + recipeId);
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, T recipe) {
		buffer.writeBoolean(recipe.hasItemBiproducts());
		buffer.writeBoolean(recipe.hasFluidBiproducts());
		FluidIngredient.writeList(buffer, recipe.getFluidIngredients());
		buffer.writeFluidStack(recipe.getFluidRecipeOutput());
		buffer.writeDouble(recipe.getXp());
		if (recipe.hasItemBiproducts()) {
			ProbableItem.writeList(buffer, recipe.getItemBiproducts());
		}
		if (recipe.hasFluidBiproducts()) {
			ProbableFluid.writeList(buffer, recipe.getFluidBiproducts());
		}
	}
}
