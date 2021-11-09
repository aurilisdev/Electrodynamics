package electrodynamics.common.recipe.categories.fluid3items2item;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class Fluid3Items2ItemRecipeSerializer<T extends Fluid3Items2ItemRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public Fluid3Items2ItemRecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
	CountableIngredient itemInput1 = CountableIngredient.deserialize(GsonHelper.getAsJsonObject(json, "item_input1"));
	CountableIngredient itemInput2 = CountableIngredient.deserialize(GsonHelper.getAsJsonObject(json, "item_input2"));
	CountableIngredient itemInput3 = CountableIngredient.deserialize(GsonHelper.getAsJsonObject(json, "item_input3"));
	FluidIngredient fluidInput = FluidIngredient.deserialize(GsonHelper.getAsJsonObject(json, "fluid_input"));
	ItemStack itemOutput = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "item_output"), true);

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient.class,
		    CountableIngredient.class, CountableIngredient.class, CountableIngredient.class, ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, fluidInput, itemInput1, itemInput2, itemInput3, itemOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
	CountableIngredient itemInput1 = CountableIngredient.read(buffer);
	CountableIngredient itemInput2 = CountableIngredient.read(buffer);
	CountableIngredient itemInput3 = CountableIngredient.read(buffer);
	FluidIngredient fluidInput = FluidIngredient.read(buffer);
	ItemStack itemOutput = buffer.readItem();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient.class,
		    CountableIngredient.class, CountableIngredient.class, CountableIngredient.class, ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, fluidInput, itemInput1, itemInput2, itemInput3, itemOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
    	CountableIngredient itemInput1 = (CountableIngredient) recipe.getIngredients().get(0);
    	CountableIngredient itemInput2 = (CountableIngredient) recipe.getIngredients().get(1);
    	CountableIngredient itemInput3 = (CountableIngredient) recipe.getIngredients().get(2);
    	FluidIngredient fluidInput = (FluidIngredient) recipe.getIngredients().get(3);
    	itemInput1.writeStack(buffer);
    	itemInput2.writeStack(buffer);
    	itemInput3.writeStack(buffer);
    	fluidInput.writeStack(buffer);
    	buffer.writeItem(recipe.getResultItem());
    }

}
