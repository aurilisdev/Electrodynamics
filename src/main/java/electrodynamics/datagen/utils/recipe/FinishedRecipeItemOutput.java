package electrodynamics.datagen.utils.recipe;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class FinishedRecipeItemOutput extends AbstractElectrodynamicsFinishedRecipe {

	private ItemStack output;

	private FinishedRecipeItemOutput(IRecipeSerializer<?> serializer, ItemStack stack, double experience, int processTime, double usage) {
		super(serializer, experience, processTime, usage);
		output = stack;
	}

	@Override
	public void writeOutput(JsonObject recipeJson) {
		JsonObject output = new JsonObject();
		output.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output.getItem()).toString());
		output.addProperty(ElectrodynamicsRecipeSerializer.COUNT, this.output.getCount());
		recipeJson.add(ElectrodynamicsRecipeSerializer.OUTPUT, output);
	}

	@Override
	public FinishedRecipeItemOutput name(RecipeCategory category, String parent, String name) {
		return (FinishedRecipeItemOutput) super.name(category, parent, name);
	}

	public static FinishedRecipeItemOutput of(IRecipeSerializer<?> serializer, ItemStack output, double experience, int processTime, double usage) {
		return new FinishedRecipeItemOutput(serializer, output, experience, processTime, usage);
	}

}
