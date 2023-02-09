package electrodynamics.common.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
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

	/*
	 * Need to know: > does it have fluid and item biproducts ; store as booleans > the number of fluid and item biproducts ; store as ints > the arrangement of the items in the machine's inventory ; store as int list
	 * 
	 * store item and fluid biproducts here as well
	 */

	private ResourceLocation id;

	private boolean hasItemBi;
	private boolean hasFluidBi;
	// not initialized unless in use; no need to create a bunch of Objects if there
	// never even used!
	private int itemBiCount;
	private int fluidBiCount;

	private double xp;
	
	private int ticks;
	private double usagePerTick;
	

	private ProbableItem[] itemBiProducts;
	private ProbableFluid[] fluidBiProducts;

	private HashMap<Integer, List<Integer>> itemArrangements = new HashMap<>();
	private List<Integer> fluidArrangement;

	protected ElectrodynamicsRecipe(ResourceLocation recipeID, double experience, int ticks, double usagePerTick) {
		id = recipeID;
		hasItemBi = false;
		hasFluidBi = false;
		xp = experience;
		this.ticks = ticks;
		this.usagePerTick = usagePerTick;
	}

	protected ElectrodynamicsRecipe(ResourceLocation recipeID, ProbableItem[] itemBiproducts, double experience, int ticks, double usagePerTick) {
		id = recipeID;
		hasItemBi = true;
		itemBiProducts = itemBiproducts;
		itemBiCount = itemBiproducts.length;
		hasFluidBi = false;
		xp = experience;
		this.ticks = ticks;
		this.usagePerTick = usagePerTick;
	}

	protected ElectrodynamicsRecipe(ProbableFluid[] fluidBiproducts, ResourceLocation recipeID, double experience, int ticks, double usagePerTick) {
		id = recipeID;
		hasItemBi = false;
		hasFluidBi = true;
		fluidBiProducts = fluidBiproducts;
		fluidBiCount = fluidBiproducts.length;
		xp = experience;
		this.ticks = ticks;
		this.usagePerTick = usagePerTick;
	}

	protected ElectrodynamicsRecipe(ResourceLocation recipeID, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts, double experience, int ticks, double usagePerTick) {
		id = recipeID;
		hasItemBi = true;
		itemBiProducts = itemBiproducts;
		itemBiCount = itemBiproducts.length;
		hasFluidBi = true;
		fluidBiProducts = fluidBiproducts;
		fluidBiCount = fluidBiproducts.length;
		xp = experience;
		this.ticks = ticks;
		this.usagePerTick = usagePerTick;
	}

	/**
	 * NEVER USE THIS METHOD!
	 */
	@Override
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

	// ALWAYS call these methods before trying to get any other info on biproducts
	public boolean hasItemBiproducts() {
		return hasItemBi;
	}

	public boolean hasFluidBiproducts() {
		return hasFluidBi;
	}

	public ProbableItem[] getItemBiproducts() {
		if (!hasItemBiproducts()) {
			throw new UnsupportedOperationException("This recipe has no Item Biproducts. Do not get the list!");
		}
		return itemBiProducts;
	}

	public ProbableFluid[] getFluidBiproducts() {
		if (!hasFluidBiproducts()) {
			throw new UnsupportedOperationException("This recipe has no Fluid Biproducts. Do not get the list!");
		}
		return fluidBiProducts;
	}

	public ItemStack[] getFullItemBiStacks() {
		ItemStack[] items = new ItemStack[itemBiCount];
		for (int i = 0; i < itemBiCount; i++) {
			items[i] = itemBiProducts[i].getFullStack();
		}
		return items;
	}

	public FluidStack[] getFullFluidBiStacks() {
		FluidStack[] fluids = new FluidStack[fluidBiCount];
		for (int i = 0; i < fluidBiCount; i++) {
			fluids[i] = fluidBiProducts[i].getFullStack();
		}
		return fluids;
	}

	public int getItemBiproductCount() {
		if (!hasItemBiproducts()) {
			throw new UnsupportedOperationException("This recipe has no Item Biproducts. Do not get the count");
		}
		return itemBiCount;
	}

	public int getFluidBiproductCount() {
		if (!hasFluidBiproducts()) {
			throw new UnsupportedOperationException("This recipe has no Fluid Biproducts. Do not get the count!");
		}
		return fluidBiCount;
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

	public double getXp() {
		return xp;
	}
	
	public int getTicks() {
		return ticks;
	}
	
	public double getUsagePerTick() {
		return usagePerTick;
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

	public abstract boolean matchesRecipe(ComponentProcessor pr);

}
