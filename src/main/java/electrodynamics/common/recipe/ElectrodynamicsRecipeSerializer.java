package electrodynamics.common.recipe;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ElectrodynamicsRecipeSerializer<T extends ElectrodynamicsRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

	private Class<T> RECIPE_CLASS;
	
	public ElectrodynamicsRecipeSerializer(Class<T> recipeClass) {
		this.RECIPE_CLASS = recipeClass;
	}
	
	@Override
	public abstract T read(ResourceLocation recipeId, JsonObject json);

	@Override
	public abstract T read(ResourceLocation recipeId, PacketBuffer buffer);

	@Override
	public abstract void write(PacketBuffer buffer, T recipe);
	
	public Class<T> getRecipeClass(){
		return this.RECIPE_CLASS;
	}

}
