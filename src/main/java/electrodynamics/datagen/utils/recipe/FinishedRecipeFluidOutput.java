package electrodynamics.datagen.utils.recipe;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FinishedRecipeFluidOutput extends AbstractElectrodynamicsFinishedRecipe {

	private FluidStack output;
	
	private FinishedRecipeFluidOutput(RecipeSerializer<?> serializer, FluidStack stack, double experience, int processTime, double usage) {
		super(serializer, experience, processTime, usage);
		this.output = stack;
	}

	@Override
	public void writeOutput(JsonObject recipeJson) {
		JsonObject output = new JsonObject();
		output.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(this.output.getFluid()).toString());
		output.addProperty("amount", this.output.getAmount());
		recipeJson.add(ElectrodynamicsRecipeSerializer.OUTPUT, output);
	}
	
	@Override
	public FinishedRecipeFluidOutput name(RecipeCategory category, String parent, String name) {
		return (FinishedRecipeFluidOutput) super.name(category, parent, name);
	}
	
	public static FinishedRecipeFluidOutput of(RecipeSerializer<?> serializer, FluidStack output, double experience, int processTime, double usage) {
		return new FinishedRecipeFluidOutput(serializer, output, experience, processTime, usage);
	}

}
