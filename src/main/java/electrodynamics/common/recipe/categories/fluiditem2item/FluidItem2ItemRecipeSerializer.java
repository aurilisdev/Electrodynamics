package electrodynamics.common.recipe.categories.fluiditem2item;

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

public class FluidItem2ItemRecipeSerializer<T extends FluidItem2ItemRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public FluidItem2ItemRecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
	CountableIngredient itemInput = CountableIngredient.deserialize(GsonHelper.getAsJsonObject(json, "item_input"));
	FluidIngredient fluidInput = FluidIngredient.deserialize(GsonHelper.getAsJsonObject(json, "fluid_input"));
	ItemStack itemOutput = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "item_output"), true);

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient.class,
		    FluidIngredient.class, ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, itemInput, fluidInput, itemOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
	CountableIngredient itemInput = CountableIngredient.read(buffer);
	FluidIngredient fluidInput = FluidIngredient.read(buffer);
	ItemStack itemOutput = buffer.readItem();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, CountableIngredient.class,
		    FluidIngredient.class, ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, itemInput, fluidInput, itemOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
	CountableIngredient itemInput = (CountableIngredient) recipe.getIngredients().get(0);
	FluidIngredient fluidInput = (FluidIngredient) recipe.getIngredients().get(1);
	itemInput.writeStack(buffer);
	fluidInput.writeStack(buffer);
	buffer.writeItem(recipe.getResultItem());
    }

}
