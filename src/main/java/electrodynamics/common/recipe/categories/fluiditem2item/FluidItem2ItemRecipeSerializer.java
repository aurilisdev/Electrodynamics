package electrodynamics.common.recipe.categories.fluiditem2item;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class FluidItem2ItemRecipeSerializer<T extends FluidItem2ItemRecipe> extends ElectrodynamicsRecipeSerializer<T> {

	public FluidItem2ItemRecipeSerializer(Class<T> recipeClass) {
		super(recipeClass);
	}

	@Override
	public T fromJson(ResourceLocation recipeId, JsonObject recipeJson) {
		CountableIngredient[] inputs = getItemIngredients(recipeId, recipeJson);
		FluidIngredient[] fluidInputs = getFluidIngredients(recipeId, recipeJson);
		ItemStack output = getItemOutput(recipeId, recipeJson);
		double experience = getExperience(recipeJson);
		int ticks = getTicks(recipeId, recipeJson);
		double usagePerTick = getTicks(recipeId, recipeJson);
		ProbableItem[] itemBi = getItemBiproducts(recipeId, recipeJson);
		ProbableFluid[] fluidBi = getFluidBiproducts(recipeId, recipeJson);
		try {
			Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, double.class, int.class, double.class, ProbableItem[].class, ProbableFluid[].class);
			return recipeConstructor.newInstance(recipeId, inputs, fluidInputs, output, experience, ticks, usagePerTick, itemBi, fluidBi);
		} catch (Exception e) {
			Electrodynamics.LOGGER.info(e.getMessage());
		}
		Electrodynamics.LOGGER.info("returning null at " + recipeId);
		return null;
	}

	@Override
	public T fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
		boolean hasItemBi = buffer.readBoolean();
		boolean hasFluidBi = buffer.readBoolean();
		CountableIngredient[] inputs = CountableIngredient.readList(buffer);
		FluidIngredient[] fluidInputs = FluidIngredient.readList(buffer);
		ItemStack output = buffer.readItem();
		double experience = buffer.readDouble();
		int ticks = buffer.readInt();
		double usagePerTick = buffer.readDouble();
		ProbableItem[] itemBi = null;
		ProbableFluid[] fluidBi = null;
		if (hasItemBi) {
			itemBi = ProbableItem.readList(buffer);
		} 
		if (hasFluidBi) {
			fluidBi = ProbableFluid.readList(buffer);
			
		}
		try {
			Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient[].class, FluidIngredient[].class, ItemStack.class, double.class, int.class, double.class, ProbableItem[].class, ProbableFluid[].class);
			return recipeConstructor.newInstance(recipeId, inputs, fluidInputs, output, experience, ticks, usagePerTick, itemBi, fluidBi);
		} catch (Exception e) {
			Electrodynamics.LOGGER.info(e.getMessage());
		}
		Electrodynamics.LOGGER.info("returning null at " + recipeId);
		return null;
	}

	@Override
	public void toNetwork(PacketBuffer buffer, T recipe) {
		buffer.writeBoolean(recipe.hasItemBiproducts());
		buffer.writeBoolean(recipe.hasFluidBiproducts());
		CountableIngredient.writeList(buffer, recipe.getCountedIngredients());
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
	}

}
