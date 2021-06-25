/*
 * This is mainly here in case we need to add some custom methods or what not
 */
package electrodynamics.common.recipe.recipeutils;

import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IElectrodynamicsRecipe extends IRecipe<RecipeWrapper> {

    /**
     * NEVER USE THIS METHOD!
     */
    @Override
    default boolean matches(RecipeWrapper inv, World world) {
	return false;
    }

    @Override
    default boolean canFit(int width, int height) {
	return false;
    }

    boolean matchesRecipe(ComponentProcessor pr);

}
