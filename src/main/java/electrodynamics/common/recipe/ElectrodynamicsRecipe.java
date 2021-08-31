package electrodynamics.common.recipe;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import electrodynamics.common.recipe.recipeutils.IElectrodynamicsRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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
   
    public static Set<IRecipe<?>> findRecipesbyType(IRecipeType<?> typeIn, World world) {
    	
	return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet())
		: Collections.emptySet();
    }
    
}
