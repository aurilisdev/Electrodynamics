package physica.nuclear.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.api.core.utilities.IBaseUtilities;
import physica.core.common.CoreItemRegister;
import physica.library.recipe.RecipeSystem;
import physica.nuclear.common.recipe.type.ChemicalBoilerRecipe;
import physica.nuclear.common.recipe.type.ChemicalExtractorRecipe;
import physica.nuclear.common.tile.TileChemicalBoiler;
import physica.nuclear.common.tile.TileChemicalExtractor;

public class NuclearRecipeRegister implements IContent, IBaseUtilities {

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.OnStartup)
		{
			// 1 Gram Antimatter cell
			addShapeless(NuclearItemRegister.itemAntimatterCell1Gram, NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram,
					NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram, NuclearItemRegister.itemAntimatterCell125Milligram,
					NuclearItemRegister.itemAntimatterCell125Milligram);

			// Hazmat Suit
			addRecipe(NuclearItemRegister.itemHazmatHelmet, "SSS", "BAB", "SCS", 'A', Items.leather_helmet, 'C', "circuitBasic", 'S', Blocks.wool, 'B', "plateSteel");
			addRecipe(NuclearItemRegister.itemHazmatPlate, "SSS", "BAB", "SCS", 'A', Items.leather_chestplate, 'C', "circuitBasic", 'S', Blocks.wool, 'B', "plateSteel");
			addRecipe(NuclearItemRegister.itemHazmatLegs, "SSS", "BAB", "SCS", 'A', Items.leather_leggings, 'C', "circuitBasic", 'S', Blocks.wool, 'B', "plateSteel");
			addRecipe(NuclearItemRegister.itemHazmatBoots, "SSS", "BAB", "SCS", 'A', Items.leather_boots, 'C', "circuitBasic", 'S', Blocks.wool, 'B', "plateSteel");

			// Reinforced Hazmat Suit
			addRecipe(NuclearItemRegister.itemReinforcedHazmatHelmet, "ILI", "LAL", "ILI", 'A', NuclearItemRegister.itemHazmatHelmet, 'I', "ingotLead", 'L', "plateLead");
			addRecipe(NuclearItemRegister.itemReinforcedHazmatLegs, "ILI", "LAL", "ILI", 'A', NuclearItemRegister.itemHazmatLegs, 'I', "ingotLead", 'L', "plateLead");
			addRecipe(NuclearItemRegister.itemReinforcedHazmatPlate, "ILI", "LAL", "ILI", 'A', NuclearItemRegister.itemHazmatPlate, 'I', "ingotLead", 'L', "plateLead");
			addRecipe(NuclearItemRegister.itemReinforcedHazmatBoots, "ILI", "LAL", "ILI", 'A', NuclearItemRegister.itemHazmatBoots, 'I', "ingotLead", 'L', "plateLead");

			// Fissile Fuel cell
			addRecipe(NuclearItemRegister.itemHighEnrichedFuelCell, "CUC", "CUC", "CUC", 'U', NuclearItemRegister.itemUranium235, 'C', CoreItemRegister.itemEmptyCell);

			// Breeder Fuel cell
			addRecipe(NuclearItemRegister.itemLowEnrichedFuelCell, "CUC", "CIC", "CUC", 'I', NuclearItemRegister.itemUranium235, 'U', NuclearItemRegister.itemUranium238, 'C', CoreItemRegister.itemEmptyCell);

			// Empty Electromagnetic cell
			addRecipe(new ItemStack(NuclearItemRegister.itemEmptyElectromagneticCell, 1), " T ", "TGT", " T ", 'G', new ItemStack(NuclearBlockRegister.blockElectromagnet, 1, 1), 'T', "ingotTin");

			// Empty Quantum cell
			addRecipe(new ItemStack(NuclearItemRegister.itemEmptyQuantumCell, 1), " T ", "TGT", " T ", 'G', NuclearItemRegister.itemAntimatterCell125Milligram, 'T', "ingotLead");

			// Geiger Counter
			addRecipe(NuclearItemRegister.itemGeigerCounter, "SAS", "SBS", "SSS", 'S', "plateSteel", 'A', "circuitAdvanced", 'B', "phyBattery");

			// Boiler Recipes
			RecipeSystem.registerRecipe(TileChemicalBoiler.class, new ChemicalBoilerRecipe(800, new ItemStack(NuclearItemRegister.itemUranium238), 1750));
			RecipeSystem.registerRecipe(TileChemicalBoiler.class, new ChemicalBoilerRecipe(1600, new ItemStack(NuclearItemRegister.itemYellowcake), 2000));
			RecipeSystem.registerRecipe(TileChemicalBoiler.class, new ChemicalBoilerRecipe(2400, "oreUraniumPhysica", 1250));
			if (OreDictionary.doesOreNameExist("oreUranium"))
			{
				RecipeSystem.registerRecipe(TileChemicalBoiler.class, new ChemicalBoilerRecipe(2400, "oreUranium", 1250));
			}

			// Extractor Recipes

			RecipeSystem.registerRecipe(TileChemicalExtractor.class, new ChemicalExtractorRecipe(4800, new ItemStack(CoreItemRegister.itemEmptyCell), new ItemStack(NuclearItemRegister.itemHeavyWaterCell)));
			RecipeSystem.registerRecipe(TileChemicalExtractor.class, new ChemicalExtractorRecipe(4800, new ItemStack(NuclearItemRegister.itemHeavyWaterCell), new ItemStack(NuclearItemRegister.itemDeuteriumCell)));
			RecipeSystem.registerRecipe(TileChemicalExtractor.class, new ChemicalExtractorRecipe(1600, "oreUraniumPhysica", new ItemStack(NuclearItemRegister.itemYellowcake, 1)));
			if (OreDictionary.doesOreNameExist("oreUranium"))
			{
				RecipeSystem.registerRecipe(TileChemicalExtractor.class, new ChemicalExtractorRecipe(1600, "oreUranium", new ItemStack(NuclearItemRegister.itemYellowcake, 1)));
			}
		}
	}

}
