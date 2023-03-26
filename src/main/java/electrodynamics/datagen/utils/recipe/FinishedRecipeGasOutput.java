package electrodynamics.datagen.utils.recipe;

import com.google.gson.JsonObject;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FinishedRecipeGasOutput extends AbstractElectrodynamicsFinishedRecipe {

	private GasStack output;
	
	private FinishedRecipeGasOutput(RecipeSerializer<?> serializer, GasStack output, double experience, int processTime, double usagePerTick) {
		super(serializer, experience, processTime, usagePerTick);
		this.output = output;
	}

	@Override
	public void writeOutput(JsonObject recipeJson) {
		JsonObject output = new JsonObject();
		output.addProperty("gas", ElectrodynamicsRegistries.gasRegistry().getKey(this.output.getGas()).toString());
		output.addProperty("amount", this.output.getAmount());
		output.addProperty("temp", this.output.getTemperature());
		output.addProperty("pressure", this.output.getPressure());
		recipeJson.add(ElectrodynamicsRecipeSerializer.OUTPUT, output);
	}
	
	@Override
	public FinishedRecipeGasOutput name(RecipeCategory category, String parent, String name) {
		return (FinishedRecipeGasOutput) super.name(category, parent, name);
	}
	
	public static FinishedRecipeGasOutput of(RecipeSerializer<?> serializer, GasStack output, double experience, int processTime, double usage) {
		return new FinishedRecipeGasOutput(serializer, output, experience, processTime, usage);
	}

}
