package electrodynamics.common.recipe.recipeutils;

import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IElectrodynamicsRecipe extends Recipe<RecipeWrapper> {

    /**
     * NEVER USE THIS METHOD!
     */
    @Override
    default boolean matches(RecipeWrapper inv, Level world) {
	return false;
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
	return false;
    }

    @Override
    default boolean isSpecial() {
	return true;
    }

    boolean matchesRecipe(ComponentProcessor pr);

}
