package electrodynamics.common.recipe.categories.fluid2item;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

public class Fluid2ItemRecipeSerializer<T extends Fluid2ItemRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public Fluid2ItemRecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {
	FluidIngredient fluidInput = FluidIngredient.deserialize(JSONUtils.getJsonObject(json, "fluid_input"));
	ItemStack itemOutput = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "item_output"), true);

	try {
	    Constructor<T> recipeConstructor = getRecipeClass()
		    .getDeclaredConstructor(new Class[] { ResourceLocation.class, FluidIngredient.class, ItemStack.class });
	    return recipeConstructor.newInstance(new Object[] { recipeId, fluidInput, itemOutput });
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
	FluidIngredient fluidInput = FluidIngredient.read(buffer);
	ItemStack itemOutput = buffer.readItemStack();

	try {
	    Constructor<T> recipeConstructor = getRecipeClass()
		    .getDeclaredConstructor(new Class[] { ResourceLocation.class, FluidIngredient.class, ItemStack.class });
	    return recipeConstructor.newInstance(new Object[] { recipeId, fluidInput, itemOutput });
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
	    return null;
	}
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
	FluidIngredient fluidInput = (FluidIngredient) recipe.getIngredients().get(0);
	fluidInput.writeStack(buffer);
	buffer.writeItemStack(recipe.getRecipeOutput());
    }

}
