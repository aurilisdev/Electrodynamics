package electrodynamics.common.recipe.categories.o2o;

import java.lang.reflect.Constructor;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class O2ORecipeSerializer<T extends O2ORecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public O2ORecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Nullable
    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
	CountableIngredient input = CountableIngredient.deserialize(GsonHelper.getAsJsonObject(json, "input"));
	ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient.class,
		    ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, input, output);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
	CountableIngredient input = CountableIngredient.read(buffer);
	ItemStack output = buffer.readItem();
	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient.class,
		    ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, input, output);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
	CountableIngredient input = (CountableIngredient) recipe.getIngredients().get(0);
	input.writeStack(buffer);
	buffer.writeItemStack(recipe.getResultItem(), false);
    }

}
