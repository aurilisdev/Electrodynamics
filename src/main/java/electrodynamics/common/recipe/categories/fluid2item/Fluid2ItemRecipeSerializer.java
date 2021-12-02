package electrodynamics.common.recipe.categories.fluid2item;

import java.lang.reflect.Constructor;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class Fluid2ItemRecipeSerializer<T extends Fluid2ItemRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    public Fluid2ItemRecipeSerializer(Class<T> recipeClass) {
	super(recipeClass);
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject recipeJson) {
	FluidIngredient[] inputs = getFluidIngredients(recipeJson);
	ItemStack output = getItemOutput(recipeJson);
	if (recipeJson.has(ITEM_BIPRODUCTS)) {
	    ProbableItem[] itemBi = getItemBiproducts(recipeJson);
	    if (recipeJson.has(FLUID_BIPRODUCTS)) {
		ProbableFluid[] fluidBi = getFluidBiproducts(recipeJson);
		try {
		    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class,
			    ItemStack.class, ProbableItem[].class, ProbableFluid[].class);
		    return recipeConstructor.newInstance(recipeId, inputs, output, itemBi, fluidBi);
		} catch (Exception e) {
		    Electrodynamics.LOGGER.info(e.getMessage());
		}
	    } else {
		try {
		    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class,
			    ItemStack.class, ProbableItem[].class);
		    return recipeConstructor.newInstance(recipeId, inputs, output, itemBi);
		} catch (Exception e) {
		    Electrodynamics.LOGGER.info(e.getMessage());
		}
	    }
	} else if (recipeJson.has(FLUID_BIPRODUCTS)) {
	    ProbableFluid[] fluidBi = getFluidBiproducts(recipeJson);
	    try {
		Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(FluidIngredient[].class, ItemStack.class,
			ProbableFluid[].class, ResourceLocation.class);
		return recipeConstructor.newInstance(inputs, output, fluidBi, recipeId);
	    } catch (Exception e) {
		Electrodynamics.LOGGER.info(e.getMessage());
	    }
	} else {
	    try {
		Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class,
			ItemStack.class);
		return recipeConstructor.newInstance(recipeId, inputs, output);
	    } catch (Exception e) {
		Electrodynamics.LOGGER.info(e.getMessage());
	    }
	}
	Electrodynamics.LOGGER.info("returning null");
	return null;
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
	boolean hasItemBi = buffer.readBoolean();
	boolean hasFluidBi = buffer.readBoolean();
	FluidIngredient[] inputs = FluidIngredient.readList(buffer);
	ItemStack output = buffer.readItem();
	if (hasItemBi) {
	    ProbableItem[] itemBi = ProbableItem.readList(buffer);
	    if (hasFluidBi) {
		ProbableFluid[] fluidBi = ProbableFluid.readList(buffer);
		try {
		    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class,
			    ItemStack.class, ProbableItem[].class, ProbableFluid[].class);
		    return recipeConstructor.newInstance(recipeId, inputs, output, itemBi, fluidBi);
		} catch (Exception e) {
		    Electrodynamics.LOGGER.info(e.getMessage());
		}
	    } else {
		try {
		    Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class,
			    ItemStack.class, ProbableItem[].class);
		    return recipeConstructor.newInstance(recipeId, inputs, output, itemBi);
		} catch (Exception e) {
		    Electrodynamics.LOGGER.info(e.getMessage());
		}
	    }
	} else if (hasFluidBi) {
	    ProbableFluid[] fluidBi = ProbableFluid.readList(buffer);
	    try {
		Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(FluidIngredient[].class, ItemStack.class,
			ProbableFluid[].class, ResourceLocation.class);
		return recipeConstructor.newInstance(inputs, output, fluidBi, recipeId);
	    } catch (Exception e) {
		Electrodynamics.LOGGER.info(e.getMessage());
	    }
	} else {
	    try {
		Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(ResourceLocation.class, FluidIngredient[].class,
			ItemStack.class);
		return recipeConstructor.newInstance(recipeId, inputs, output);
	    } catch (Exception e) {
		Electrodynamics.LOGGER.info(e.getMessage());
	    }
	}
	Electrodynamics.LOGGER.info("returning null");
	return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
	buffer.writeBoolean(recipe.hasItemBiproducts());
	buffer.writeBoolean(recipe.hasFluidBiproducts());
	FluidIngredient.writeList(buffer, recipe.getFluidIngredients());
	buffer.writeItem(recipe.getResultItem());
	if (recipe.hasItemBiproducts()) {
	    ProbableItem.writeList(buffer, recipe.getItemBiproducts());
	}
	if (recipe.hasFluidBiproducts()) {
	    ProbableFluid.writeList(buffer, recipe.getFluidBiproducts());
	}
    }

}
