package electrodynamics.common.recipe.categories.o2o;

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

public class O2ORecipeSerializer<T extends O2ORecipe> extends ElectrodynamicsRecipeSerializer<T>{
	
	public O2ORecipeSerializer(Class<T> recipeClass) {
		super(recipeClass);
	}
	
	@Nullable
	@Override
	public T read(ResourceLocation recipeId, JsonObject json) {
		CountableIngredient input = CountableIngredient.deserialize(JSONUtils.getJsonObject(json, "input"));
		ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
		try {
			Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(new Class[] {ResourceLocation.class,CountableIngredient.class,ItemStack.class});
			return recipeConstructor.newInstance(new Object[]{recipeId,input,output});
		}
		catch(Exception e){
			ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
			return null;
		}
	}

	@Nullable
	@Override
	public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		CountableIngredient input = CountableIngredient.read(buffer);
		ItemStack output = buffer.readItemStack();
		try {
			Constructor<T> recipeConstructor = getRecipeClass().getDeclaredConstructor(new Class[] {ResourceLocation.class,CountableIngredient.class,ItemStack.class});
			return recipeConstructor.newInstance(new Object[]{recipeId,input,output});
		}
		catch(Exception e){
			ElectrodynamicsRecipe.LOGGER.info("Recipe generation has failed!");
			return null;
		}
	}

	@Override
	public void write(PacketBuffer buffer, T recipe) {
		CountableIngredient input = (CountableIngredient)recipe.getIngredients().get(0);
		input.writeStack(buffer);
		buffer.writeItemStack(recipe.getRecipeOutput(),false);
	}


}
