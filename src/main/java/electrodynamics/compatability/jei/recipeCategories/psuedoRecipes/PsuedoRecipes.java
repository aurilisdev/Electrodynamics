package electrodynamics.compatability.jei.recipeCategories.psuedoRecipes;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ballistix.common.block.SubtypeBlast;
import electrodynamics.prefab.tile.processing.DO2OProcessingRecipe;
import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeMineralFluid;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.tile.TileFermentationPlant;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoRecipes {

	/* Item/Fluid Storage */
	
	//Electrodynamics
	private static ArrayList<ArrayList<ItemStack>> ELECTRODYNAMICS_ITEMS = new ArrayList<ArrayList<ItemStack>>();
	private static ArrayList<Fluid> ELECTRODYNAMICS_FLUIDS = new ArrayList<Fluid>();
	public static ArrayList<ItemStack> ELECTRODYNAMICS_MACHINES = new ArrayList<ItemStack>();
	
	//Nuclear Science
	private static ArrayList<ArrayList<ItemStack>> NUCLEAR_SCIENCE_ITEMS = new ArrayList<ArrayList<ItemStack>>();
	private static ArrayList<Fluid> NUCLEAR_SCIENCE_FLUIDS = new ArrayList<Fluid>();
	public static ArrayList<ItemStack> NUCLEAR_SCIENCE_MACHINES = new ArrayList<ItemStack>();
	
	//Ballistix
	public static ArrayList<ArrayList<ItemStack>> BALLISTIX_ITEMS = new ArrayList<ArrayList<ItemStack>>();
	
	/*RECIPE STORAGE*/
	
	//Electrodynamics
	public static ArrayList<Psuedo5XRecipe> X5_ORE_RECIPES = new ArrayList<Psuedo5XRecipe>();
	public static ArrayList<PsuedoSolAndLiqToLiquidRecipe> CHEMICAL_MIXER_RECIPES = new ArrayList<PsuedoSolAndLiqToLiquidRecipe>();
	public static ArrayList<PsuedoSolAndLiqToLiquidRecipe> FERMENTATION_CHAMBER_RECIPES = new ArrayList<PsuedoSolAndLiqToLiquidRecipe>();
	
	//Nuclear Science 
	public static ArrayList<PsuedoSolAndLiqToLiquidRecipe> NUCLEAR_BOILER_RECIPES = new ArrayList<PsuedoSolAndLiqToLiquidRecipe>();
	public static ArrayList<PsuedoSolAndLiqToSolidRecipe> CHEMICAL_EXTRACTOR_RECIPES = new ArrayList<PsuedoSolAndLiqToSolidRecipe>();
	public static ArrayList<PsuedoGasCentrifugeRecipe> GAS_CENTRIFUGE_RECIPES = new ArrayList<PsuedoGasCentrifugeRecipe>();
	public static ArrayList<O2OProcessingRecipe> FISSION_REACTOR_RECIPES = new ArrayList<O2OProcessingRecipe>();
	public static ArrayList<O2OProcessingRecipe> ANTI_MATTER_RECIPES = new ArrayList<O2OProcessingRecipe>();
	public static ArrayList<O2OProcessingRecipe> DARK_MATTER_RECIPES = new ArrayList<O2OProcessingRecipe>();
	
	//Ballistix
	public static ArrayList<DO2OProcessingRecipe> WARHEAD_RECIPES = new ArrayList<DO2OProcessingRecipe>();
	public static ArrayList<O2OProcessingRecipe> SILO_RECIPES = new ArrayList<O2OProcessingRecipe>();
	
	
	
	//private static final Logger logger = LogManager.getLogger(ElectrodynamicsPatches.MOD_ID);

	public static void addElectrodynamicsRecipes() {
		addElectrodynamicsMachines();
		addElectrodynamicsFluids();
		addElectrodynamicsItems();
		
		/* 5x Ore Processing */

		// Iron
		X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(0), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1),1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(2),1000), ELECTRODYNAMICS_ITEMS.get(2).get(0)));
		// Gold
		X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(1), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1),1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(3),1000), ELECTRODYNAMICS_ITEMS.get(2).get(1)));
		// Copper
		X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(2), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1),1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(4),1000), ELECTRODYNAMICS_ITEMS.get(2).get(2)));
		// Tin
		X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(3), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1),1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(5),1000), ELECTRODYNAMICS_ITEMS.get(2).get(3)));
		// Lead
		X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(4), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1),1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(6),1000), ELECTRODYNAMICS_ITEMS.get(2).get(4)));
		// Silver
		X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(5), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1),1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(7),1000), ELECTRODYNAMICS_ITEMS.get(2).get(5)));
		// Vanadium
		X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(6), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1),1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(8),1000), ELECTRODYNAMICS_ITEMS.get(2).get(6)));
		
		
		/*Chemical Mixing Recipes*/
		
		CHEMICAL_MIXER_RECIPES.add(new PsuedoSolAndLiqToLiquidRecipe(
				ELECTRODYNAMICS_ITEMS.get(3).get(0),ELECTRODYNAMICS_ITEMS.get(3).get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(0),1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1),2500),
				PsuedoSolAndLiqToLiquidRecipe.SULFURIC_ACID));
		
		/*Fermenting Recipes*/
		
		ArrayList<Item> fermentItems = new ArrayList<Item>(TileFermentationPlant.RECIPE_MAPPINGS.keySet());
		ArrayList<Integer> fermentItemCount = new ArrayList<Integer>(TileFermentationPlant.RECIPE_MAPPINGS.values());
		ArrayList<ItemStack> fermentItemStacks = new ArrayList<ItemStack>();
		for(int i = 0; i < fermentItems.size(); i++) {
			fermentItemStacks.add(new ItemStack(fermentItems.get(i),fermentItemCount.get(i).intValue()));
		}
		
		for(ItemStack itemStack : fermentItemStacks) {
			FERMENTATION_CHAMBER_RECIPES.add(new PsuedoSolAndLiqToLiquidRecipe(itemStack,ELECTRODYNAMICS_ITEMS.get(3).get(1)
					,new FluidStack(ELECTRODYNAMICS_FLUIDS.get(0),1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(9),100),
					PsuedoSolAndLiqToLiquidRecipe.ETHANOL));
		}

	}
	
	
	public static void addBallistixRecipes() {
		addElectrodynamicsMachines();
		addElectrodynamicsFluids();
		addElectrodynamicsItems();
		
		addBallistixMachines();
		addBallistixFluids();
		addBallistixItems();
		
		/*SILO RECIPES*/
		SILO_RECIPES.add(new O2OProcessingRecipe(BALLISTIX_ITEMS.get(1).get(0),BALLISTIX_ITEMS.get(2).get(0)));
		
		/*WARHEAD RECIPES*/
		WARHEAD_RECIPES.add(new DO2OProcessingRecipe(BALLISTIX_ITEMS.get(0).get(0),BALLISTIX_ITEMS.get(1).get(0),BALLISTIX_ITEMS.get(1).get(0)));
		
	}
	
	public static void addBlastCraftRecipes() {
		addElectrodynamicsMachines();
		addElectrodynamicsFluids();
		addElectrodynamicsItems();
		
		addBlastCraftMachines();
		addBlastCraftFluids();
		addBlastCraftItems();
	}
	
	public static void addNuclearScienceRecipes() {
		addElectrodynamicsMachines();
		addElectrodynamicsFluids();
		addElectrodynamicsItems();
		
		addNuclearScienceMachines();
		addNuclearScienceFluids();
		addNuclearScienceItems();
		
		/*Nuclear Boiler*/
		
		//UF6 from Yellowcake
		NUCLEAR_BOILER_RECIPES.add(new PsuedoSolAndLiqToLiquidRecipe(NUCLEAR_SCIENCE_ITEMS.get(0).get(0),ELECTRODYNAMICS_ITEMS.get(3).get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(0),800), new FluidStack(NUCLEAR_SCIENCE_FLUIDS.get(0),2500),
				PsuedoSolAndLiqToLiquidRecipe.UHEXFLOUR));
		
		//UF6 from U238
		NUCLEAR_BOILER_RECIPES.add(new PsuedoSolAndLiqToLiquidRecipe(NUCLEAR_SCIENCE_ITEMS.get(0).get(1),ELECTRODYNAMICS_ITEMS.get(3).get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(0),1600), new FluidStack(NUCLEAR_SCIENCE_FLUIDS.get(0),2000),
				PsuedoSolAndLiqToLiquidRecipe.UHEXFLOUR));
		
		/*Chemical Extractor*/
		
		CHEMICAL_EXTRACTOR_RECIPES.add(new PsuedoSolAndLiqToSolidRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(7), ELECTRODYNAMICS_ITEMS.get(3).get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(0),1600), NUCLEAR_SCIENCE_ITEMS.get(0).get(0)));
		
		CHEMICAL_EXTRACTOR_RECIPES.add(new PsuedoSolAndLiqToSolidRecipe(NUCLEAR_SCIENCE_ITEMS.get(1).get(0), ELECTRODYNAMICS_ITEMS.get(3).get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(0),4800), NUCLEAR_SCIENCE_ITEMS.get(1).get(1)));
		
		CHEMICAL_EXTRACTOR_RECIPES.add(new PsuedoSolAndLiqToSolidRecipe(NUCLEAR_SCIENCE_ITEMS.get(1).get(1), ELECTRODYNAMICS_ITEMS.get(3).get(1),
				new FluidStack(ELECTRODYNAMICS_FLUIDS.get(0),4800), NUCLEAR_SCIENCE_ITEMS.get(1).get(2)));
		
		/*Gas Centrifuge*/
		
		GAS_CENTRIFUGE_RECIPES.add(new PsuedoGasCentrifugeRecipe(new FluidStack(NUCLEAR_SCIENCE_FLUIDS.get(0),1000),
				NUCLEAR_SCIENCE_ITEMS.get(0).get(2), NUCLEAR_SCIENCE_ITEMS.get(0).get(1)));
		
		/*Fision Reactor Enrichment*/
		
		FISSION_REACTOR_RECIPES.add(new O2OProcessingRecipe(NUCLEAR_SCIENCE_ITEMS.get(1).get(2), NUCLEAR_SCIENCE_ITEMS.get(1).get(3)));
		
		/*Anit Matter Production*/
		
		ANTI_MATTER_RECIPES.add(new O2OProcessingRecipe(NUCLEAR_SCIENCE_ITEMS.get(1).get(4),NUCLEAR_SCIENCE_ITEMS.get(1).get(5)));
		
		DARK_MATTER_RECIPES.add(new O2OProcessingRecipe(NUCLEAR_SCIENCE_ITEMS.get(1).get(4),NUCLEAR_SCIENCE_ITEMS.get(1).get(7)));
	}
	
	
	
	private static void addElectrodynamicsMachines() {
		//Mineral Washer
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralwasher)));
		//Chemical Crystallizer 
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer)));
		//Chemical Mixer
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalmixer)));
		//Coal Generator
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgenerator)));
		//Upgrade Transformer
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.upgradetransformer)));
		//Downgrade Transformer
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.downgradetransformer)));
		//Solar Panel
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.solarpanel)));
		//Advanced Solar Panel
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.advancedsolarpanel)));
		//Thermoelectric Generator
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.thermoelectricgenerator)));
		//Combustion Chamber
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber)));
		//Hydroelectric Generator
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.hydroelectricgenerator)));
		//Wind Generator
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.windmill)));
		//Fermentation Chamber
		ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fermentationplant)));
		
	}
	
	
	private static void addBallistixMachines() {
		
	}
	
	
	private static void addBlastCraftMachines() {
		 
	}
	
	
	private static void addNuclearScienceMachines() {
		
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockReactorCore));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockFusionReactorCore));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockChemicalBoiler));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockChemicalExtractor));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockElectromagnet));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockElectromagneticBooster));
		//NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(Items.ACACIA_BOAT));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockElectromagneticSwitch));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockGasCentrifuge));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockQuantumCapacitor));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockRadioisotopeGenerator));
		NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockTurbine));
		//NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(Items.ACACIA_BOAT));
		
	}
	

	private static void addElectrodynamicsFluids() {
		ELECTRODYNAMICS_FLUIDS.add(Fluids.WATER.getFluid());
		ELECTRODYNAMICS_FLUIDS.add(electrodynamics.DeferredRegisters.fluidSulfuricAcid);
		ELECTRODYNAMICS_FLUIDS.add(electrodynamics.DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.get(SubtypeMineralFluid.iron));
		ELECTRODYNAMICS_FLUIDS.add(electrodynamics.DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.get(SubtypeMineralFluid.gold));
		ELECTRODYNAMICS_FLUIDS.add(electrodynamics.DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.get(SubtypeMineralFluid.copper));
		ELECTRODYNAMICS_FLUIDS.add(electrodynamics.DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.get(SubtypeMineralFluid.tin));
		ELECTRODYNAMICS_FLUIDS.add(electrodynamics.DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.get(SubtypeMineralFluid.lead));
		ELECTRODYNAMICS_FLUIDS.add(electrodynamics.DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.get(SubtypeMineralFluid.silver));
		ELECTRODYNAMICS_FLUIDS.add(electrodynamics.DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.get(SubtypeMineralFluid.vanadium));
		ELECTRODYNAMICS_FLUIDS.add(electrodynamics.DeferredRegisters.fluidEthanol);

	}
	
	
	private static void addBallistixFluids() {
		
	}
	
	
	private static void addBlastCraftFluids() {
		
	}
	
	
	private static void addNuclearScienceFluids() {
		NUCLEAR_SCIENCE_FLUIDS.add(nuclearscience.DeferredRegisters.fluidUraniumHexafluoride);
	}

	private static void addElectrodynamicsItems() {
		/*ORES : 0*/
		Item[] ores = {Blocks.IRON_ORE.getBlock().asItem(),
						Blocks.GOLD_ORE.getBlock().asItem(),
						electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeOre.copper).asItem(),
						electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeOre.tin).asItem(),
						electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeOre.lead).asItem(),
						electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeOre.silver).asItem(),
						electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeOre.vanadinite).asItem(),
						electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeOre.uraninite).asItem(),
						electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeOre.sulfur).asItem(),
						electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeOre.thorianite).asItem()
							
						};
		
		ELECTRODYNAMICS_ITEMS.add(formItemStacks(ores, 1));
		
		/*Ore Crystals : 1 and 2*/
		Item[] oreCrystals = {electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.iron),
							  electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.gold),
							  electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.copper),
							  electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.tin),
							  electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.lead),
							  electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.silver),
							  electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.vanadium)
				
							  };
		
		//Adds individual items for reference
		ELECTRODYNAMICS_ITEMS.add(formItemStacks(oreCrystals, 1));
		
		//Adds Crystalyzation output stack size of crystals
		ELECTRODYNAMICS_ITEMS.add(formItemStacks(oreCrystals, 5));
		
		/*MISC ITEMS : 3*/
		Item[] miscIngredients = {electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeOxide.trisulfur),
								  Items.WATER_BUCKET
				
								 };
		ELECTRODYNAMICS_ITEMS.add(formItemStacks(miscIngredients, 1));
		
	}
	
	
	private static void addBallistixItems() {
		
		/*EXPLOSIVES*/
		SubtypeBlast[] explosives = SubtypeBlast.values();
		int explosivesLength = explosives.length;
		Item[] explosiveItems = new Item[explosivesLength];
		for(int i = 0;i < explosivesLength;i++) {
			explosiveItems[i] = ballistix.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(explosives[i]).asItem();
		}
		BALLISTIX_ITEMS.add(formItemStacks(explosiveItems, 1));
		
		/*MISSILES*/
		Item[] missiles = {
				ballistix.DeferredRegisters.ITEM_MISSILECLOSERANGE.get(),
				ballistix.DeferredRegisters.ITEM_MISSILEMEDIUMRANGE.get(),
				ballistix.DeferredRegisters.ITEM_MISSILELONGRANGE.get()
		};
		BALLISTIX_ITEMS.add(formItemStacks(missiles, 1));
		
		/*MISC*/
		Item[] misc = {ballistix.DeferredRegisters.blockMissileSilo.asItem(),
					   ballistix.DeferredRegisters.ITEM_ROCKETLAUNCHER.get()};
		BALLISTIX_ITEMS.add(formItemStacks(misc, 1));
	}
	
	
	private static void addBlastCraftItems() {
		
	}
	
	
	private static void addNuclearScienceItems() {
		
		//Uranium and Derivatives : 0
		Item[] uraniumMisc = {nuclearscience.DeferredRegisters.ITEM_YELLOWCAKE.get(),
							  nuclearscience.DeferredRegisters.ITEM_URANIUM238.get(),
							  nuclearscience.DeferredRegisters.ITEM_URANIUM235.get()
		};
		
		NUCLEAR_SCIENCE_ITEMS.add(formItemStacks(uraniumMisc, 1));
		
		//Cells : 1
		Item[] cells = {nuclearscience.DeferredRegisters.ITEM_CELLEMPTY.get(),
						nuclearscience.DeferredRegisters.ITEM_CELLHEAVYWATER.get(),
						nuclearscience.DeferredRegisters.ITEM_CELLDEUTERIUM.get(),
						nuclearscience.DeferredRegisters.ITEM_CELLTRITIUM.get(),
						nuclearscience.DeferredRegisters.ITEM_CELLELECTROMAGNETIC.get(),
						nuclearscience.DeferredRegisters.ITEM_CELLANTIMATTERSMALL.get(),
						nuclearscience.DeferredRegisters.ITEM_CELLANTIMATTERLARGE.get(),
						nuclearscience.DeferredRegisters.ITEM_CELLDARKMATTER.get()
		};
		
		NUCLEAR_SCIENCE_ITEMS.add(formItemStacks(cells, 1));
		
	}
	
	
	private static ArrayList<ItemStack> formItemStacks(Item[] items, int countPerItemStack){
		ArrayList<ItemStack> inputItems = new ArrayList<ItemStack>();
		
		for(int i = 0; i < items.length;i++) {
			inputItems.add(new ItemStack(items[i]));
			inputItems.get(i).setCount(countPerItemStack);
		}
		return inputItems;
	}
	

	

}
