package electrodynamics.common.recipe.categories.fluiditem2item;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class FluidItem2ItemRecipeSerializer<T extends FluidItem2ItemRecipe> extends ElectrodynamicsRecipeSerializer<T> {

	public FluidItem2ItemRecipeSerializer(Class<T> recipeClass) {
		super(recipeClass);
	}

	@Override
	public T fromJson(ResourceLocation recipeId, JsonObject recipeJson) {
		CountableIngredient[] inputs = getItemIngredients(recipeJson);
		FluidIngredient[] fluidInputs = getFluidIngredients(recipeJson);
		ItemStack output = getItemOutput(recipeJson);
		double experience = getExperience(recipeJson);
		if (recipeJson.has(ITEM_BIPRODUCTS)) {
			ProbableItem[] itemBi = getItemBiproducts(recipeJson);
			if (recipeJson.has(FLUID_BIPRODUCTS)) {
				ProbableFluid[] fluidBi = getFluidBiproducts(recipeJson);
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, ProbableItem[].class, ProbableFluid[].class, double.class);
					return recipeConstructor.newInstance(recipeId, inputs, fluidInputs, output, itemBi, fluidBi, experience);
				} catch (Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			} else {
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, ProbableItem[].class, double.class);
					return recipeConstructor.newInstance(recipeId, inputs, fluidInputs, output, itemBi, experience);
				} catch (Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			}
		} else if (recipeJson.has(FLUID_BIPRODUCTS)) {
			ProbableFluid[] fluidBi = getFluidBiproducts(recipeJson);
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, ProbableFluid[].class, ResourceLocation.class, double.class);
				return recipeConstructor.newInstance(inputs, fluidInputs, output, fluidBi, recipeId, experience);
			} catch (Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		} else {
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, double.class);
				return recipeConstructor.newInstance(recipeId, inputs, fluidInputs, output, experience);
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
		CountableIngredient[] inputs = CountableIngredient.readList(buffer);
		FluidIngredient[] fluidInputs = FluidIngredient.readList(buffer);
		ItemStack output = buffer.readItem();
		double experience = buffer.readDouble();
		if (hasItemBi) {
			ProbableItem[] itemBi = ProbableItem.readList(buffer);
			if (hasFluidBi) {
				ProbableFluid[] fluidBi = ProbableFluid.readList(buffer);
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, ProbableItem[].class, ProbableFluid[].class, double.class);
					return recipeConstructor.newInstance(recipeId, inputs, fluidInputs, output, itemBi, fluidBi, experience);
				} catch (Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			} else {
				try {
					Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, ProbableItem[].class, double.class);
					return recipeConstructor.newInstance(recipeId, inputs, fluidInputs, output, itemBi, experience);
				} catch (Exception e) {
					Electrodynamics.LOGGER.info(e.getMessage());
				}
			}
		} else if (hasFluidBi) {
			ProbableFluid[] fluidBi = ProbableFluid.readList(buffer);
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, ProbableFluid[].class, ResourceLocation.class, double.class);
				return recipeConstructor.newInstance(inputs, fluidInputs, output, fluidBi, recipeId, experience);
			} catch (Exception e) {
				Electrodynamics.LOGGER.info(e.getMessage());
			}
		} else {
			try {
				Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, double.class);
				return recipeConstructor.newInstance(recipeId, inputs, fluidInputs, output, experience);
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
		CountableIngredient.writeList(buffer, recipe.getCountedIngredients());
		FluidIngredient.writeList(buffer, recipe.getFluidIngredients());
		buffer.writeItem(recipe.getResultItem());
		buffer.writeDouble(recipe.getXp());
		if (recipe.hasItemBiproducts()) {
			ProbableItem.writeList(buffer, recipe.getItemBiproducts());
		}
		if (recipe.hasFluidBiproducts()) {
			ProbableFluid.writeList(buffer, recipe.getFluidBiproducts());
		}
	}

}
