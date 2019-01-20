package physica.nuclear.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import physica.api.core.IBaseUtilities;
import physica.api.core.IContent;
import physica.core.common.CoreItemRegister;
import physica.nuclear.common.recipe.NuclearCustomRecipeHelper;

public class NuclearRecipeRegister implements IContent, IBaseUtilities {

	@Override
	public void loadComplete() {
		// 1 Gram Antimatter cell
		addShapeless(NuclearItemRegister.itemAntimatterCell1Gram, NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram,
				NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram,
				NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram);

		//Hazmat Suit
		addRecipe(NuclearItemRegister.itemHazmatHelmet, "SSS", "BAB", "SCS", 'A', Items.leather_helmet, 'C', "circuitBasic", 'S', Blocks.wool);
		addRecipe(NuclearItemRegister.itemHazmatPlate, "SSS", "BAB", "SCS", 'A', Items.leather_chestplate, 'C', "circuitBasic", 'S', Blocks.wool);
		addRecipe(NuclearItemRegister.itemHazmatLegs, "SSS", "BAB", "SCS", 'A', Items.leather_leggings, 'C', "circuitBasic", 'S', Blocks.wool);
		addRecipe(NuclearItemRegister.itemHazmatBoots, "SSS", "BAB", "SCS", 'A', Items.leather_boots, 'C', "circuitBasic", 'S', Blocks.wool);

		// Fissile Fuel cell
		addRecipe(NuclearItemRegister.itemHighEnrichedFuelCell, "CUC", "CUC", "CUC", 'U', NuclearItemRegister.itemUranium235, 'C', CoreItemRegister.itemEmptyContainer);

		// Breeder Fuel cell
		addRecipe(NuclearItemRegister.itemLowEnrichedFuelCell, "CUC", "CIC", "CUC", 'I', NuclearItemRegister.itemUranium235, 'U', NuclearItemRegister.itemUranium238, 'C',
				CoreItemRegister.itemEmptyContainer);

		// Empty Electromagnetic cell
		addRecipe(new ItemStack(NuclearItemRegister.itemEmptyElectromagneticCell, 1), " T ", "TGT", " T ", 'G', new ItemStack(NuclearBlockRegister.blockElectromagnet, 1, 1), 'T', "ingotTin");

		// Empty Quantum cell
		addRecipe(new ItemStack(NuclearItemRegister.itemEmptyQuantumCell, 1), " T ", "TGT", " T ", 'G', NuclearItemRegister.itemAntimatterCell125Milligram, 'T', "ingotTin");

		// Boiler Recipes
		NuclearCustomRecipeHelper.addBoilerRecipe(800, NuclearItemRegister.itemUranium238, 1750);
		NuclearCustomRecipeHelper.addBoilerRecipe(1600, NuclearItemRegister.itemYellowcake, 2000);
		NuclearCustomRecipeHelper.addBoilerRecipe(2400, "oreUranium", 1250);

		// Extractor Recipes
		NuclearCustomRecipeHelper.addExtractorRecipe(4800, CoreItemRegister.itemEmptyContainer, new ItemStack(NuclearItemRegister.itemHeavyWaterCell));
		NuclearCustomRecipeHelper.addExtractorRecipe(4800, NuclearItemRegister.itemHeavyWaterCell, new ItemStack(NuclearItemRegister.itemDeuteriumCell));
		NuclearCustomRecipeHelper.addExtractorRecipe(1600, "oreUranium", new ItemStack(NuclearItemRegister.itemYellowcake, 1));
	}

}
