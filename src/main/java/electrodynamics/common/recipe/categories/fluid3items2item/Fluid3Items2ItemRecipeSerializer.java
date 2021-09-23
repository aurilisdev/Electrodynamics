package electrodynamics.common.recipe.categories.fluid3items2item;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

public class Fluid3Items2ItemRecipeSerializer<T extends Fluid3Items2ItemRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public Fluid3Items2ItemRecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {
	CountableIngredient itemInput1 = CountableIngredient.deserialize(JSONUtils.getJsonObject(json, "item_input1"));
	CountableIngredient itemInput2 = CountableIngredient.deserialize(JSONUtils.getJsonObject(json, "item_input2"));
	CountableIngredient itemInput3 = CountableIngredient.deserialize(JSONUtils.getJsonObject(json, "item_input3"));
	FluidIngredient fluidInput = FluidIngredient.deserialize(JSONUtils.getJsonObject(json, "fluid_input"));
	ItemStack itemOutput = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "item_output"), true);

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(FluidIngredient.class, CountableIngredient.class,
		    CountableIngredient.class, CountableIngredient.class, ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, fluidInput, itemInput1, itemInput2, itemInput3, itemOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
	CountableIngredient itemInput1 = CountableIngredient.read(buffer);
	CountableIngredient itemInput2 = CountableIngredient.read(buffer);
	CountableIngredient itemInput3 = CountableIngredient.read(buffer);
	FluidIngredient fluidInput = FluidIngredient.read(buffer);
	ItemStack itemOutput = buffer.readItemStack();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(FluidIngredient.class, CountableIngredient.class,
		    CountableIngredient.class, CountableIngredient.class, ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, fluidInput, itemInput1, itemInput2, itemInput3, itemOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
	CountableIngredient itemInput = (CountableIngredient) recipe.getIngredients().get(0);
	FluidIngredient fluidInput = (FluidIngredient) recipe.getIngredients().get(1);
	itemInput.writeStack(buffer);
	fluidInput.writeStack(buffer);
	buffer.writeItemStack(recipe.getRecipeOutput());
    }

}
