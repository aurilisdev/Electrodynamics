package physica.core.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.api.core.utilities.IBaseUtilities;
import physica.core.common.recipe.CircuitPressRecipeHandler;
import physica.core.common.tile.TileCircuitPress;
import physica.library.recipe.RecipeSystem;

public class CoreRecipeRegister implements IContent, IBaseUtilities {

	@Override
	public void register(LoadPhase phase) {
		if (phase == LoadPhase.OnStartup) {
			// Motor
			addRecipe(CoreItemRegister.itemMotor, "RSR", "SIS", "RSR", 'R', Items.redstone, 'S', "ingotSteel", 'I', "ingotCopper");

			// Empty Cell
			addRecipe(new ItemStack(CoreItemRegister.itemEmptyCell, 4), "GTG", "T T", "GTG", 'G', Blocks.glass, 'T', "ingotTin");

			// Plates
			addShapeless(new ItemStack(CoreItemRegister.itemMetaPlate, 1, 0), Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot);
			addShapeless(new ItemStack(CoreItemRegister.itemMetaPlate, 1, 1), "ingotSteel", "ingotSteel", "ingotSteel", "ingotSteel");
			addShapeless(new ItemStack(CoreItemRegister.itemMetaPlate, 1, 2), "ingotLead", "ingotLead", "ingotLead", "ingotLead");
			// Wrench
			addRecipe(CoreItemRegister.itemWrench, "SS ", "SS ", "  S", 'S', "ingotSteel");

			// Battery
			addRecipe(CoreItemRegister.itemBattery, " T ", "TRT", "TCT", 'T', "ingotTin", 'R', Items.redstone, 'C', Items.coal);

			// Superconductor Blend
			addRecipe(new ItemStack(CoreItemRegister.itemMetaBlend, 1, 0), "SGS", "GEG", "SGS", 'S', "ingotSilver", 'G', "ingotGold", 'E', Items.ender_pearl);

			// Multimeter
			addRecipe(CoreItemRegister.itemMultimeter, " B ", "SCS", "SSS", 'B', CoreBlockRegister.blockCable, 'S', "ingotSteel", 'C', "circuitBasic");

			// Smeltings
			GameRegistry.addSmelting(CoreBlockRegister.blockTinOre, new ItemStack(CoreItemRegister.itemMetaIngot, 1, 0), 0.7f);
			GameRegistry.addSmelting(CoreBlockRegister.blockCopperOre, new ItemStack(CoreItemRegister.itemMetaIngot, 1, 1), 0.7f);
			GameRegistry.addSmelting(CoreBlockRegister.blockLeadOre, new ItemStack(CoreItemRegister.itemMetaIngot, 1, 3), 1.0f);
			GameRegistry.addSmelting(CoreBlockRegister.blockSilverOre, new ItemStack(CoreItemRegister.itemMetaIngot, 1, 4), 1.2f);
			GameRegistry.addSmelting(new ItemStack(CoreItemRegister.itemMetaBlend, 1, 0), new ItemStack(CoreItemRegister.itemMetaIngot, 1, 5), 1.9f);

			RecipeSystem.registerRecipe(TileCircuitPress.class, new CircuitPressRecipeHandler("plateSteel", "dustRedstone", null, null, new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0)));
			RecipeSystem.registerRecipe(TileCircuitPress.class, new CircuitPressRecipeHandler("circuitBasic", "ingotGold", null, null, new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1)));
			RecipeSystem.registerRecipe(TileCircuitPress.class, new CircuitPressRecipeHandler("circuitAdvanced", "gemDiamond", null, null, new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 2)));
		}
	}
}