package electrodynamics.common.recipe;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import electrodynamics.common.recipe.recipeutils.IElectrodynamicsRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class ElectrodynamicsRecipe implements IElectrodynamicsRecipe {

    public ResourceLocation id;
    public static Logger LOGGER = LogManager.getLogger(electrodynamics.api.References.ID);

    protected ElectrodynamicsRecipe(ResourceLocation recipeID) {
	id = recipeID;
    }

    @Override
    public ResourceLocation getId() {
	return id;
    }

    public static Set<Recipe<?>> findRecipesbyType(RecipeType<?> typeIn, Level world) {

	return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet())
		: Collections.emptySet();
    }

}
