package electrodynamics.common.recipe;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ElectrodynamicsRecipeSerializer<T extends ElectrodynamicsRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {

	private static final String COUNT = "count";
	private static final String ITEM_INPUTS = "iteminputs";
	private static final String FLUID_INPUTS = "fluidinputs";
	public static final String ITEM_BIPRODUCTS = "itembi";
	public static final String FLUID_BIPRODUCTS = "fluidbi";
	private static final String OUTPUT = "output";
	public static final String EXPERIENCE = "experience";

	private Class<T> RECIPE_CLASS;

	protected ElectrodynamicsRecipeSerializer(Class<T> recipeClass) {
		this.RECIPE_CLASS = recipeClass;
	}

	public Class<T> getRecipeClass() {
		return this.RECIPE_CLASS;
	}

	public static CountableIngredient[] getItemIngredients(JsonObject json) {
		if (!json.has(ITEM_INPUTS)) {
			throw new UnsupportedOperationException("There are no Item Inputs!");
		}
		JsonObject itemInputs = GsonHelper.getAsJsonObject(json, ITEM_INPUTS);
		if (!itemInputs.has(COUNT)) {
			throw new UnsupportedOperationException("You must include a count field");
		}
		int count = itemInputs.get(COUNT).getAsInt();
		List<CountableIngredient> itemIngredients = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (!itemInputs.has(i + "")) {
				throw new UnsupportedOperationException("The count field does not match the input count");
			}
			itemIngredients.add(CountableIngredient.deserialize(itemInputs.get(i + "")));
		}
		CountableIngredient[] ings = new CountableIngredient[itemIngredients.size()];
		for (int i = 0; i < ings.length; i++) {
			ings[i] = itemIngredients.get(i);
		}
		return ings;
	}

	public static FluidIngredient[] getFluidIngredients(JsonObject json) {
		if (!json.has(FLUID_INPUTS)) {
			throw new UnsupportedOperationException("There are no Fluid Inputs!");
		}
		JsonObject fluidInputs = GsonHelper.getAsJsonObject(json, FLUID_INPUTS);
		if (!fluidInputs.has(COUNT)) {
			throw new UnsupportedOperationException("You must include a count field");
		}
		int count = fluidInputs.get(COUNT).getAsInt();
		// Electrodynamics.LOGGER.info(count);
		List<FluidIngredient> fluidIngredients = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (!fluidInputs.has(i + "")) {
				throw new UnsupportedOperationException("The count field does not match the input count");
			}
			fluidIngredients.add(FluidIngredient.deserialize(fluidInputs.get(i + "").getAsJsonObject()));
		}
		FluidIngredient[] ings = new FluidIngredient[fluidIngredients.size()];
		for (int i = 0; i < ings.length; i++) {
			ings[i] = fluidIngredients.get(i);
		}
		return ings;
	}

	/**
	 * It is assumed you checked this field was here before you called!
	 * 
	 * @param json
	 * @return
	 */
	public static ProbableItem[] getItemBiproducts(JsonObject json) {
		JsonObject itemBiproducts = GsonHelper.getAsJsonObject(json, ITEM_BIPRODUCTS);
		if (!itemBiproducts.has(COUNT)) {
			throw new UnsupportedOperationException("You must include a count field");
		}
		int count = itemBiproducts.get(COUNT).getAsInt();
		List<ProbableItem> items = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (!itemBiproducts.has(i + "")) {
				throw new UnsupportedOperationException("The count field does not match the input count");
			}
			items.add(ProbableItem.deserialize(itemBiproducts.get("" + i).getAsJsonObject()));
		}
		ProbableItem[] stacks = new ProbableItem[items.size()];
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = items.get(i);
		}
		return stacks;
	}

	/**
	 * It is assumed you checked this field was here before you called!
	 * 
	 * @param json
	 * @return
	 */
	public static ProbableFluid[] getFluidBiproducts(JsonObject json) {
		JsonObject fluidBiproducts = GsonHelper.getAsJsonObject(json, FLUID_BIPRODUCTS);
		if (!fluidBiproducts.has(COUNT)) {
			throw new UnsupportedOperationException("You must include a count field");
		}
		int count = fluidBiproducts.get(COUNT).getAsInt();
		List<ProbableFluid> fluids = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (!fluidBiproducts.has(i + "")) {
				throw new UnsupportedOperationException("The count field does not match the input count");
			}
			fluids.add(ProbableFluid.deserialize(fluidBiproducts.get(i + "").getAsJsonObject()));
		}
		ProbableFluid[] stacks = new ProbableFluid[fluids.size()];
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = fluids.get(i);
		}
		return stacks;
	}

	public static ItemStack getItemOutput(JsonObject json) {
		if (!json.has(OUTPUT)) {
			throw new UnsupportedOperationException("You must include an Item output!");
		}
		return CraftingHelper.getItemStack(json.get(OUTPUT).getAsJsonObject(), false);
	}

	public static FluidStack getFluidOutput(JsonObject json) {
		if (!json.has(OUTPUT)) {
			throw new UnsupportedOperationException("You must include an Item output!");
		}
		JsonObject fluid = json.get(OUTPUT).getAsJsonObject();
		ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(fluid, "fluid"));
		int amount = GsonHelper.getAsInt(fluid, "amount");
		return new FluidStack(ForgeRegistries.FLUIDS.getValue(resourceLocation), amount);
	}

	public static double getExperience(JsonObject json) {
		return json.has(EXPERIENCE) ? json.get(EXPERIENCE).getAsDouble() : 0;
	}

}
