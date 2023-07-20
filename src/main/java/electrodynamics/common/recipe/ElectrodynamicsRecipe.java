package electrodynamics.common.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.GasIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class ElectrodynamicsRecipe implements Recipe<RecipeWrapper> {

	private ResourceLocation id;

	private double xp;
	private int ticks;
	private double usagePerTick;

	@Nullable
	private ProbableItem[] itemBiproducts;
	@Nullable
	private ProbableFluid[] fluidBiproducts;
	@Nullable
	private ProbableGas[] gasBiproducts;

	private HashMap<Integer, List<Integer>> itemArrangements = new HashMap<>();
	@Nullable
	private List<Integer> fluidArrangement;
	@Nullable
	private List<Integer> gasArrangement;

	public ElectrodynamicsRecipe(ResourceLocation recipeID, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts, ProbableGas[] gasBiproducts) {
		id = recipeID;
		xp = experience;
		this.ticks = ticks;
		this.usagePerTick = usagePerTick;
		this.itemBiproducts = itemBiproducts;
		this.fluidBiproducts = fluidBiproducts;
		this.gasBiproducts = gasBiproducts;
	}

	/**
	 * NEVER USE THIS METHOD!
	 */
	@Override
	@Deprecated
	public boolean matches(RecipeWrapper inv, Level world) {
		return false;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	public boolean hasItemBiproducts() {
		return itemBiproducts != null;
	}

	public boolean hasFluidBiproducts() {
		return fluidBiproducts != null;
	}

	public boolean hasGasBiproducts() {
		return gasBiproducts != null;
	}

	@Nullable
	public ProbableItem[] getItemBiproducts() {
		return itemBiproducts;
	}

	@Nullable
	public ProbableFluid[] getFluidBiproducts() {
		return fluidBiproducts;
	}

	@Nullable
	public ProbableGas[] getGasBiproducts() {
		return gasBiproducts;
	}

	public ItemStack[] getFullItemBiStacks() {
		ItemStack[] items = new ItemStack[getItemBiproductCount()];
		for (int i = 0; i < getItemBiproductCount(); i++) {
			items[i] = itemBiproducts[i].getFullStack();
		}
		return items;
	}

	public FluidStack[] getFullFluidBiStacks() {
		FluidStack[] fluids = new FluidStack[getFluidBiproductCount()];
		for (int i = 0; i < getFluidBiproductCount(); i++) {
			fluids[i] = fluidBiproducts[i].getFullStack();
		}
		return fluids;
	}

	public GasStack[] getFullGasBiStacks() {
		GasStack[] gases = new GasStack[getGasBiproductCount()];
		for (int i = 0; i < getGasBiproductCount(); i++) {
			gases[i] = gasBiproducts[i].getFullStack();
		}
		return gases;
	}

	public int getItemBiproductCount() {
		return itemBiproducts.length;
	}

	public int getFluidBiproductCount() {
		return fluidBiproducts.length;
	}

	public int getGasBiproductCount() {
		return gasBiproducts.length;
	}

	public double getXp() {
		return xp;
	}

	public int getTicks() {
		return ticks;
	}

	public double getUsagePerTick() {
		return usagePerTick;
	}

	public void setItemArrangement(Integer procNumber, List<Integer> arrangement) {
		itemArrangements.put(procNumber, arrangement);
	}

	public List<Integer> getItemArrangment(Integer procNumber) {
		return itemArrangements.get(procNumber);
	}

	public void setFluidArrangement(List<Integer> arrangement) {
		fluidArrangement = arrangement;
	}

	public List<Integer> getFluidArrangement() {
		return fluidArrangement;
	}

	public void setGasArrangement(List<Integer> arrangement) {
		gasArrangement = arrangement;
	}

	public List<Integer> getGasArrangement() {
		return gasArrangement;
	}

	public static List<ElectrodynamicsRecipe> findRecipesbyType(RecipeType<? extends ElectrodynamicsRecipe> typeIn, Level world) {
		return world != null ? world.getRecipeManager().getAllRecipesFor((RecipeType<ElectrodynamicsRecipe>) typeIn) : Collections.emptyList();
	}

	@Nullable
	public static ElectrodynamicsRecipe getRecipe(ComponentProcessor pr, List<ElectrodynamicsRecipe> recipes) {
		for (ElectrodynamicsRecipe recipe : recipes) {
			if (recipe.matchesRecipe(pr)) {
				return recipe;
			}
		}
		return null;
	}

	public static Pair<List<Integer>, Boolean> areItemsValid(List<CountableIngredient> ingredients, List<ItemStack> stacks) {
		Boolean valid = true;
		List<Integer> slotOreintation = new ArrayList<>();
		for (int i = 0; i < ingredients.size(); i++) {
			CountableIngredient ing = ingredients.get(i);
			int slotNum = -1;
			for (int j = 0; j < stacks.size(); j++) {
				if (ing.testStack(stacks.get(j))) {
					slotNum = j;
					break;
				}
			}
			if (slotNum > -1 && !slotOreintation.contains(slotNum)) {
				slotOreintation.add(slotNum);
			}
		}
		if (slotOreintation.size() < ingredients.size()) {
			valid = false;
		}
		return Pair.of(slotOreintation, valid);
	}

	public static Pair<List<Integer>, Boolean> areFluidsValid(List<FluidIngredient> ingredients, FluidTank[] fluidTanks) {
		Boolean valid = true;
		List<Integer> tankOrientation = new ArrayList<>();
		for (int i = 0; i < ingredients.size(); i++) {
			FluidIngredient ing = ingredients.get(i);
			int tankNum = -1;
			for (int j = 0; j < fluidTanks.length; j++) {
				if (ing.testFluid(fluidTanks[i].getFluid())) {
					tankNum = j;
					break;
				}
			}
			if (tankNum > -1 && !tankOrientation.contains(tankNum)) {
				tankOrientation.add(tankNum);
			}
		}
		if (tankOrientation.size() < ingredients.size()) {
			valid = false;
		}
		return Pair.of(tankOrientation, valid);
	}

	public static Pair<List<Integer>, Boolean> areGasesValid(List<GasIngredient> ingredients, GasTank[] gasTanks) {
		Boolean valid = true;
		List<Integer> tankOrientation = new ArrayList<>();
		for (int i = 0; i < ingredients.size(); i++) {
			GasIngredient ing = ingredients.get(i);
			int tankNum = -1;
			for (int j = 0; j < gasTanks.length; j++) {
				if (ing.testGas(gasTanks[i].getGas(), true, true)) {
					tankNum = j;
					break;
				}
			}
			if (tankNum > -1 && !tankOrientation.contains(tankNum)) {
				tankOrientation.add(tankNum);
			}
		}
		if (tankOrientation.size() < ingredients.size()) {
			valid = false;
		}
		return Pair.of(tankOrientation, valid);
	}

	public abstract boolean matchesRecipe(ComponentProcessor pr);

}
