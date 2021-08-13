package electrodynamics.common.recipe.categories.do2o;

import java.lang.reflect.Constructor;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

public class DO2ORecipeSerializer<T extends DO2ORecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public DO2ORecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {
	CountableIngredient input1 = CountableIngredient.deserialize(JSONUtils.getJsonObject(json, "input1"));
	CountableIngredient input2 = CountableIngredient.deserialize(JSONUtils.getJsonObject(json, "input2"));
	ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
		    new Class[] { ResourceLocation.class, CountableIngredient.class, CountableIngredient.class, ItemStack.class });
	    return recipeConstructor.newInstance(new Object[] { recipeId, input1, input2, output });
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
	CountableIngredient input1 = CountableIngredient.read(buffer);
	CountableIngredient input2 = CountableIngredient.read(buffer);
	ItemStack output = buffer.readItemStack();
	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(
		    new Class[] { ResourceLocation.class, CountableIngredient.class, CountableIngredient.class, ItemStack.class });
	    return recipeConstructor.newInstance(new Object[] { recipeId, input1, input2, output });
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
	CountableIngredient input1 = (CountableIngredient) recipe.getIngredients().get(0);
	CountableIngredient input2 = (CountableIngredient) recipe.getIngredients().get(1);
	input1.writeStack(buffer);
	input2.writeStack(buffer);
	buffer.writeItemStack(recipe.getRecipeOutput(), false);
    }
}
