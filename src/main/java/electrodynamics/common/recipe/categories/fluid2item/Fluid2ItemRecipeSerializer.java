package electrodynamics.common.recipe.categories.fluid2item;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class Fluid2ItemRecipeSerializer<T extends Fluid2ItemRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public Fluid2ItemRecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
	FluidIngredient fluidInput = FluidIngredient.deserialize(GsonHelper.getAsJsonObject(json, "fluid_input"));
	ItemStack itemOutput = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "item_output"), true);

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient.class,
		    ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, fluidInput, itemOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
	FluidIngredient fluidInput = FluidIngredient.read(buffer);
	ItemStack itemOutput = buffer.readItem();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient.class,
		    ItemStack.class);
	    return recipeConstructor.newInstance(recipeId, fluidInput, itemOutput);
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
	FluidIngredient fluidInput = (FluidIngredient) recipe.getIngredients().get(0);
	fluidInput.write(buffer);
	buffer.writeItem(recipe.getResultItem());
    }

}
