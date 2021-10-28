package electrodynamics.common.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ElectrodynamicsRecipeSerializer<T extends ElectrodynamicsRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>>
	implements RecipeSerializer<T> {

    private Class<T> RECIPE_CLASS;

    protected ElectrodynamicsRecipeSerializer(Class<T> recipeClass) {
	this.RECIPE_CLASS = recipeClass;
    }

    public Class<T> getRecipeClass() {
	return this.RECIPE_CLASS;
    }

}
