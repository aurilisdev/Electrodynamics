package electrodynamics.common.recipe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ElectrodynamicsRecipeSerializer<T extends ElectrodynamicsRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

	public static final String COUNT = "count";
	public static final String ITEM_INPUTS = "iteminputs";
	public static final String FLUID_INPUTS = "fluidinputs";
	public static final String ITEM_BIPRODUCTS = "itembi";
	public static final String FLUID_BIPRODUCTS = "fluidbi";
	public static final String OUTPUT = "output";
	public static final String EXPERIENCE = "experience";
	public static final String TICKS = "ticks";
	public static final String USAGE_PER_TICK = "usagepertick";

	private Class<T> RECIPE_CLASS;

	protected ElectrodynamicsRecipeSerializer(Class<T> recipeClass) {
		this.RECIPE_CLASS = recipeClass;
	}

	public Class<T> getRecipeClass() {
		return this.RECIPE_CLASS;
	}

	public static CountableIngredient[] getItemIngredients(ResourceLocation recipeId, JsonObject json) {
		if (!json.has(ITEM_INPUTS)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": There are no Item Inputs!");
		}
		JsonObject itemInputs = JSONUtils.getAsJsonObject(json, ITEM_INPUTS);
		if (!itemInputs.has(COUNT)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": You must include a count field");
		}
		int count = itemInputs.get(COUNT).getAsInt();
		List<CountableIngredient> itemIngredients = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (!itemInputs.has(i + "")) {
				throw new UnsupportedOperationException(recipeId.toString() + ": The count field does not match the input count");
			}
			itemIngredients.add(CountableIngredient.deserialize(itemInputs.get(i + "")));
		}
		CountableIngredient[] ings = new CountableIngredient[itemIngredients.size()];
		for (int i = 0; i < ings.length; i++) {
			ings[i] = itemIngredients.get(i);
		}
		return ings;
	}

	public static FluidIngredient[] getFluidIngredients(ResourceLocation recipeId, JsonObject json) {
		if (!json.has(FLUID_INPUTS)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": There are no Fluid Inputs!");
		}
		JsonObject fluidInputs = JSONUtils.getAsJsonObject(json, FLUID_INPUTS);
		if (!fluidInputs.has(COUNT)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": You must include a count field");
		}
		int count = fluidInputs.get(COUNT).getAsInt();
		List<FluidIngredient> fluidIngredients = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (!fluidInputs.has(i + "")) {
				throw new UnsupportedOperationException(recipeId.toString() + ": The count field does not match the input count");
			}
			fluidIngredients.add(FluidIngredient.deserialize(fluidInputs.get(i + "").getAsJsonObject()));
		}
		FluidIngredient[] ings = new FluidIngredient[fluidIngredients.size()];
		for (int i = 0; i < ings.length; i++) {
			ings[i] = fluidIngredients.get(i);
		}
		return ings;
	}

	@Nullable
	public static ProbableItem[] getItemBiproducts(ResourceLocation recipeId, JsonObject json) {
		if(!json.has(ITEM_BIPRODUCTS)) {
			return null;
		}
		JsonObject itemBiproducts = JSONUtils.getAsJsonObject(json, ITEM_BIPRODUCTS);
		
		if (!itemBiproducts.has(COUNT)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": You must include a count field");
		}
		int count = itemBiproducts.get(COUNT).getAsInt();
		List<ProbableItem> items = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (!itemBiproducts.has(i + "")) {
				throw new UnsupportedOperationException(recipeId.toString() + ": The count field does not match the input count");
			}
			items.add(ProbableItem.deserialize(itemBiproducts.get("" + i).getAsJsonObject()));
		}
		ProbableItem[] stacks = new ProbableItem[items.size()];
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = items.get(i);
		}
		return stacks;
	}

	@Nullable
	public static ProbableFluid[] getFluidBiproducts(ResourceLocation recipeId, JsonObject json) {
		if(!json.has(FLUID_BIPRODUCTS)) {
			return null;
		}
		JsonObject fluidBiproducts = JSONUtils.getAsJsonObject(json, FLUID_BIPRODUCTS);
		if (!fluidBiproducts.has(COUNT)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": You must include a count field");
		}
		int count = fluidBiproducts.get(COUNT).getAsInt();
		List<ProbableFluid> fluids = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (!fluidBiproducts.has(i + "")) {
				throw new UnsupportedOperationException(recipeId.toString() + ": The count field does not match the input count");
			}
			fluids.add(ProbableFluid.deserialize(fluidBiproducts.get(i + "").getAsJsonObject()));
		}
		ProbableFluid[] stacks = new ProbableFluid[fluids.size()];
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = fluids.get(i);
		}
		return stacks;
	}

	public static ItemStack getItemOutput(ResourceLocation recipeId, JsonObject json) {
		if (!json.has(OUTPUT)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": You must include an Item output!");
		}
		return CraftingHelper.getItemStack(json.get(OUTPUT).getAsJsonObject(), false);
	}

	public static FluidStack getFluidOutput(ResourceLocation recipeId, JsonObject json) {
		if (!json.has(OUTPUT)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": You must include a Fluid output!");
		}
		JsonObject fluid = json.get(OUTPUT).getAsJsonObject();
		ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getAsString(fluid, "fluid"));
		int amount = JSONUtils.getAsInt(fluid, "amount");
		return new FluidStack(ForgeRegistries.FLUIDS.getValue(resourceLocation), amount);
	}

	public static double getExperience(JsonObject json) {
		return json.has(EXPERIENCE) ? json.get(EXPERIENCE).getAsDouble() : 0;
	}

	public static int getTicks(ResourceLocation recipeId, JsonObject json) {
		if (!json.has(TICKS)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": You must include an operating tick time!");
		}
		return json.get(TICKS).getAsInt();
	}

	public static double getUsagePerTick(ResourceLocation recipeId, JsonObject json) {
		if (!json.has(USAGE_PER_TICK)) {
			throw new UnsupportedOperationException(recipeId.toString() + ": You must include a usage per tick!");
		}
		return json.get(USAGE_PER_TICK).getAsDouble();
	}

}
