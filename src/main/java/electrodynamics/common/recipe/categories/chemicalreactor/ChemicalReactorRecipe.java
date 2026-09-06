package electrodynamics.common.recipe.categories.chemicalreactor;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import voltaic.api.gas.GasStack;
import voltaic.common.recipe.recipeutils.AbstractMaterialRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.GasIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableGas;
import voltaic.common.recipe.recipeutils.ProbableItem;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentProcessor;

public class ChemicalReactorRecipe extends AbstractMaterialRecipe {

    public static final String RECIPE_GROUP = "chemical_reactor_recipe";
    public static final ResourceLocation RECIPE_ID = Electrodynamics.rl(RECIPE_GROUP);

    private final List<CountableIngredient> itemIngredients;
    private final List<FluidIngredient> fluidIngredients;
    private final List<GasIngredient> gasIngredients;
    private final ItemStack itemOutput;
    private final FluidStack fluidOutput;
    private final GasStack gasOutput;

    public ChemicalReactorRecipe(String recipeGroup, List<CountableIngredient> inputItems,
	    List<FluidIngredient> inputFluids, List<GasIngredient> inputGases, ItemStack itemOutput,
	    FluidStack fluidOutput, GasStack gasOutput, double experience, int ticks, double usagePerTick,
	    List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
	super(recipeGroup, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
	if (inputItems.size() == 0 && inputGases.size() == 0 && inputFluids.size() == 0)
	    throw new RuntimeException("Yoou have created a chemical reactor recipe with no inputs");
	if (itemOutput.isEmpty() && fluidOutput.isEmpty() && gasOutput.isEmpty())
	    throw new RuntimeException("You have created a chemical reactor recipe with no outputs");
	itemIngredients = inputItems;
	fluidIngredients = inputFluids;
	gasIngredients = inputGases;
	this.itemOutput = itemOutput;
	this.fluidOutput = fluidOutput;
	this.gasOutput = gasOutput;

    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr, int index) {
	boolean hasInputs = false;
	if (hasItemInputs()) {
	    hasInputs = true;
	    ComponentInventory inventory = pr.getHolder()
		    .<ComponentInventory>requireComponent(IComponentType.Inventory);
	    Pair<List<Integer>, Boolean> itemPair = areItemsValid(getCountedIngredients(),
		    inventory.getInputsForProcessor(index));
	    if (!itemPair.getSecond())
		return false;
	    setItemArrangement(index, itemPair.getFirst());
	}
	if (hasFluidInputs()) {
	    hasInputs = true;
	    ComponentFluidHandlerMulti fluidHandler = pr.getHolder()
		    .<ComponentFluidHandlerMulti>requireComponent(IComponentType.FluidHandler);
	    Pair<List<Integer>, Boolean> fluidPair = areFluidsValid(getFluidIngredients(),
		    fluidHandler.getInputTanks());
	    if (!fluidPair.getSecond())
		return false;
	    setFluidArrangement(fluidPair.getFirst());
	}
	if (hasGasInputs()) {
	    hasInputs = true;
	    ComponentGasHandlerMulti gasHandler = pr.getHolder()
		    .<ComponentGasHandlerMulti>requireComponent(IComponentType.GasHandler);
	    Pair<List<Integer>, Boolean> gasPair = areGasesValid(getGasIngredients(), gasHandler.getInputTanks());
	    if (!gasPair.getSecond())
		return false;
	    setGasArrangement(gasPair.getFirst());
	}
	return hasInputs;
    }

    public boolean hasItemInputs() {
	return getCountedIngredients().size() > 0;
    }

    public boolean hasFluidInputs() {
	return getFluidIngredients().size() > 0;
    }

    public boolean hasGasInputs() {
	return getGasIngredients().size() > 0;
    }

    @Override
    public List<CountableIngredient> getCountedIngredients() {
	return itemIngredients;
    }

    @Override
    public List<FluidIngredient> getFluidIngredients() {
	return fluidIngredients;
    }

    @Override
    public List<GasIngredient> getGasIngredients() {
	return gasIngredients;
    }

    public boolean hasItemOutput() {
	return !getItemRecipeOutput().isEmpty();
    }

    public boolean hasFluidOutput() {
	return !getFluidRecipeOutput().isEmpty();
    }

    public boolean hasGasOutput() {
	return !getGasRecipeOutput().isEmpty();
    }

    @Override
    public ItemStack getItemRecipeOutput() {
	return itemOutput;
    }

    @Override
    public FluidStack getFluidRecipeOutput() {
	return fluidOutput;
    }

    @Override
    public GasStack getGasRecipeOutput() {
	return gasOutput;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return ElectrodynamicsRecipies.CHEMICAL_REACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return ElectrodynamicsRecipies.CHEMICAL_REACTOR_TYPE.get();
    }
}
