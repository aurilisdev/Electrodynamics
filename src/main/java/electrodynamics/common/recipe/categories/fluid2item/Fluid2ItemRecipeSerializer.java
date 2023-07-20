package electrodynamics.common.recipe.categories.fluid2item;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class Fluid2ItemRecipeSerializer<T extends Fluid2ItemRecipe> extends ElectrodynamicsRecipeSerializer<T> {

	public Fluid2ItemRecipeSerializer(Class<T> recipeClass) {
		super(recipeClass);
	}

	@Override
	public T fromJson(ResourceLocation recipeId, JsonObject recipeJson) {
		FluidIngredient[] inputs = getFluidIngredients(recipeId, recipeJson);
		ItemStack output = getItemOutput(recipeId, recipeJson);
		double experience = getExperience(recipeJson);
		int ticks = getTicks(recipeId, recipeJson);
		double usagePerTick = getTicks(recipeId, recipeJson);
		ProbableItem[] itemBi = getItemBiproducts(recipeId, recipeJson);
		ProbableFluid[] fluidBi = getFluidBiproducts(recipeId, recipeJson);
		ProbableGas[] gasBi = getGasBiproducts(recipeId, recipeJson);
		try {
			Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class, ItemStack.class, double.class, int.class, double.class, ProbableItem[].class, ProbableFluid[].class, ProbableGas[].class);
			return recipeConstructor.newInstance(recipeId, inputs, output, experience, ticks, usagePerTick, itemBi, fluidBi, gasBi);
		} catch (Exception e) {
			Electrodynamics.LOGGER.info(e.getMessage());
		}
		Electrodynamics.LOGGER.info("returning null at " + recipeId);
		return null;
	}

	@Override
	public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
		boolean hasItemBi = buffer.readBoolean();
		boolean hasFluidBi = buffer.readBoolean();
		boolean hasGasBi = buffer.readBoolean();
		FluidIngredient[] inputs = FluidIngredient.readList(buffer);
		ItemStack output = buffer.readItem();
		double experience = buffer.readDouble();
		int ticks = buffer.readInt();
		double usagePerTick = buffer.readDouble();
		ProbableItem[] itemBi = null;
		ProbableFluid[] fluidBi = null;
		ProbableGas[] gasBi = null;
		if (hasItemBi) {
			itemBi = ProbableItem.readList(buffer);
		}
		if (hasFluidBi) {
			fluidBi = ProbableFluid.readList(buffer);

		}
		if (hasGasBi) {
			gasBi = ProbableGas.readList(buffer);
		}
		try {
			Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class, ItemStack.class, double.class, int.class, double.class, ProbableItem[].class, ProbableFluid[].class, ProbableGas[].class);
			return recipeConstructor.newInstance(recipeId, inputs, output, experience, ticks, usagePerTick, itemBi, fluidBi, gasBi);
		} catch (Exception e) {
			Electrodynamics.LOGGER.info(e.getMessage());
		}
		Electrodynamics.LOGGER.info("returning null at " + recipeId);
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, T recipe) {
		buffer.writeBoolean(recipe.hasItemBiproducts());
		buffer.writeBoolean(recipe.hasFluidBiproducts());
		buffer.writeBoolean(recipe.hasGasBiproducts());
		FluidIngredient.writeList(buffer, recipe.getFluidIngredients());
		buffer.writeItem(recipe.getResultItem());
		buffer.writeDouble(recipe.getXp());
		buffer.writeInt(recipe.getTicks());
		buffer.writeDouble(recipe.getUsagePerTick());
		if (recipe.hasItemBiproducts()) {
			ProbableItem.writeList(buffer, recipe.getItemBiproducts());
		}
		if (recipe.hasFluidBiproducts()) {
			ProbableFluid.writeList(buffer, recipe.getFluidBiproducts());
		}
		if (recipe.hasGasBiproducts()) {
			ProbableGas.writeList(buffer, recipe.getGasBiproducts());
		}
	}

}
