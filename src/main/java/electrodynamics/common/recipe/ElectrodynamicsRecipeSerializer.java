package electrodynamics.common.recipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ElectrodynamicsRecipeSerializer<T extends ElectrodynamicsRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>>
	implements IRecipeSerializer<T> {

    private Class<T> RECIPE_CLASS;

    protected ElectrodynamicsRecipeSerializer(Class<T> recipeClass) {
	this.RECIPE_CLASS = recipeClass;
    }

    public Class<T> getRecipeClass() {
	return this.RECIPE_CLASS;
    }

}
