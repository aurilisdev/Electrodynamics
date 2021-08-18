package electrodynamics.common.recipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import electrodynamics.common.recipe.recipeutils.IElectrodynamicsRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

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

    @OnlyIn(Dist.CLIENT)
    public static Set<IRecipe<?>> findRecipesbyType(IRecipeType<?> typeIn) {
	ClientWorld world = Minecraft.getInstance().world;
	return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet())
		: Collections.emptySet();
    }

    /**
     * For O2O and DO2O Only!
     * 
     * @param typeIn
     * @param world
     * @return
     */
    public static Set<ItemStack> getAllRecipeInputs(IRecipeType<?> typeIn, World world) {
	Set<ItemStack> inputs = new HashSet<>();
	Set<IRecipe<?>> recipes = findRecipesbyType(typeIn, world);

	for (IRecipe<?> recipe : recipes) {
	    NonNullList<Ingredient> ingredients = recipe.getIngredients();

	    ingredients.forEach(ingredient -> inputs.addAll(Arrays.asList(ingredient.getMatchingStacks())));

	}
	return inputs;
    }

}
