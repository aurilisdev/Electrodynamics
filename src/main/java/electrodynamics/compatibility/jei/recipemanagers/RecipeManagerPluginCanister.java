package electrodynamics.compatibility.jei.recipemanagers;

//import java.lang.reflect.Field;
//import java.util.Collections;
import java.util.List;
//import java.util.Optional;

//import electrodynamics.Electrodynamics;
//import electrodynamics.compatibility.jei.ElectrodynamicsJEIPlugin;
//import electrodynamics.prefab.utilities.CapabilityUtils;
//import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.recipe.IFocus;
//import mezz.jei.api.recipe.IRecipeManager;
//import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
//import mezz.jei.common.focus.Focus;
//import mezz.jei.common.recipes.InternalRecipeManagerPlugin;
//import mezz.jei.common.recipes.PluginManager;
//import mezz.jei.common.recipes.RecipeManager;
//import mezz.jei.common.recipes.RecipeManagerInternal;
//import mezz.jei.common.runtime.JeiRuntime;
//import net.minecraft.world.item.ItemStack;
//import net.neoforged.fluids.FluidStack;

public class RecipeManagerPluginCanister implements IRecipeManagerPlugin {

	// private InternalRecipeManagerPlugin jeiDefaultManager = null;

	@Override
	public <V> List<RecipeType<?>> getRecipeTypes(IFocus<V> focus) {

		/*
		 * JeiRuntime runtime = (JeiRuntime) ElectrodynamicsJEIPlugin.getJeiRuntime();
		 * 
		 * if (runtime == null) { return Collections.emptyList(); }
		 * 
		 * if (focus.getRole() == RecipeIngredientRole.RENDER_ONLY) { return Collections.emptyList(); }
		 * 
		 * Optional<ItemStack> optional = focus.getTypedValue().getItemStack();
		 * 
		 * if (optional.isEmpty()) { return Collections.emptyList(); }
		 * 
		 * ItemStack stack = optional.get();
		 * 
		 * if (!CapabilityUtils.hasFluidItemCap(stack)) { return Collections.emptyList(); }
		 * 
		 * FluidStack fluidStack = CapabilityUtils.drainFluidItem(stack, Integer.MAX_VALUE, FluidAction.SIMULATE);
		 * 
		 * if (fluidStack.isEmpty()) { return Collections.emptyList(); }
		 * 
		 * if (jeiDefaultManager == null) { handleRelection(runtime); }
		 * 
		 * if (jeiDefaultManager == null) { return Collections.emptyList(); }
		 * 
		 * Focus<FluidStack> newFocus = new Focus<>(focus.getRole(), runtime.createTypedIngredient(ForgeTypes.FLUID_STACK, fluidStack));
		 * 
		 * return jeiDefaultManager.getRecipeTypes(newFocus);
		 */
		return null;
	}

	@Override
	public <T, V> List<T> getRecipes(IRecipeCategory<T> recipeCategory, IFocus<V> focus) {
		/*
		 * JeiRuntime runtime = (JeiRuntime) ElectrodynamicsJEIPlugin.getJeiRuntime();
		 * 
		 * if (runtime == null) { return Collections.emptyList(); }
		 * 
		 * if (focus.getRole() == RecipeIngredientRole.RENDER_ONLY) { return Collections.emptyList(); }
		 * 
		 * Optional<ItemStack> optional = focus.getTypedValue().getItemStack();
		 * 
		 * if (optional.isEmpty()) { return Collections.emptyList(); }
		 * 
		 * ItemStack stack = optional.get();
		 * 
		 * if (!CapabilityUtils.hasFluidItemCap(stack)) { return Collections.emptyList(); }
		 * 
		 * FluidStack fluidStack = CapabilityUtils.drainFluidItem(stack, Integer.MAX_VALUE, FluidAction.SIMULATE);
		 * 
		 * if (fluidStack.isEmpty()) { return Collections.emptyList(); }
		 * 
		 * if (jeiDefaultManager == null) { handleRelection(runtime); }
		 * 
		 * if (jeiDefaultManager == null) { return Collections.emptyList(); }
		 * 
		 * Focus<FluidStack> newFocus = new Focus<>(focus.getRole(), runtime.createTypedIngredient(ForgeTypes.FLUID_STACK, fluidStack));
		 * 
		 * return jeiDefaultManager.getRecipes(recipeCategory, newFocus);
		 */
		return null;
	}

	@Override
	public <T> List<T> getRecipes(IRecipeCategory<T> recipeCategory) {
		/*
		 * JeiRuntime runtime = (JeiRuntime) ElectrodynamicsJEIPlugin.getJeiRuntime();
		 * 
		 * if (runtime == null) { return Collections.emptyList(); }
		 * 
		 * if (jeiDefaultManager == null) { handleRelection(runtime); }
		 * 
		 * if (jeiDefaultManager == null) { return Collections.emptyList(); }
		 * 
		 * return jeiDefaultManager.getRecipes(recipeCategory);
		 */
		return null;
	}

	/*
	 * // Lord forgive me for what I must do private void handleRelection(JeiRuntime runtime) { IRecipeManager generic = runtime.getRecipeManager();
	 * 
	 * if (!(generic instanceof RecipeManager)) { return; }
	 * 
	 * RecipeManager manger = (RecipeManager) generic;
	 * 
	 * try {
	 * 
	 * Field internalField = RecipeManager.class.getDeclaredField("internal");
	 * 
	 * internalField.setAccessible(true);
	 * 
	 * RecipeManagerInternal internal = (RecipeManagerInternal) internalField.get(manger);
	 * 
	 * try {
	 * 
	 * Field managerField = RecipeManagerInternal.class.getDeclaredField("pluginManager");
	 * 
	 * managerField.setAccessible(true);
	 * 
	 * PluginManager manager = (PluginManager) managerField.get(internal);
	 * 
	 * try {
	 * 
	 * Field listField = PluginManager.class.getDeclaredField("plugins");
	 * 
	 * listField.setAccessible(true);
	 * 
	 * List<IRecipeManagerPlugin> plugins = (List<IRecipeManagerPlugin>) listField.get(manager);
	 * 
	 * for (IRecipeManagerPlugin plugin : plugins) { if (plugin instanceof InternalRecipeManagerPlugin) { jeiDefaultManager = (InternalRecipeManagerPlugin) plugin; break; } }
	 * 
	 * } catch (Exception e) { Electrodynamics.LOGGER.info("Failed to get List<IRecipeManagerPlugin> field from PluginManager"); }
	 * 
	 * } catch (Exception e) { Electrodynamics.LOGGER.info("Failed to get PluginManager field from RecipeManagerInternal"); }
	 * 
	 * } catch (Exception e) { Electrodynamics.LOGGER.info("Failed to get RecipeManagerInternal field from JEI RecipeManager"); } }
	 */

}
