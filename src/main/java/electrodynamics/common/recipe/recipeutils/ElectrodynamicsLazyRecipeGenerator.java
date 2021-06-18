package electrodynamics.common.recipe.recipeutils;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.recipe.categories.do2o.specificmachines.OxidationFurnaceRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.FermentationPlantRecipe;
import electrodynamics.common.recipe.categories.o2o.specificmachines.MineralCrusherRecipe;
import electrodynamics.common.recipe.categories.o2o.specificmachines.MineralGrinderRecipe;
import electrodynamics.common.recipe.categories.o2o.specificmachines.WireMillRecipe;

public class ElectrodynamicsLazyRecipeGenerator {
	
	private static final String RECIPE_DIRECTORY = "src/main/resources/data/electrodynamics/recipes";
	
	private static final String O2O_DIRECTORY = "/o2o/";
	private static final String DO2O_DIRECTORY = "/do2o/";
	private static final String FLUID_2_FLUID_DIRECTORY = "/fluid2fluid/";
	private static final String FLUID_2_ITEM_DIRECTORY = "/fluid2item/";
	private static final String ITEM_2_FLUID_DIRECTORY = "/item2fluid/";
	private static final String FLUID_ITEM_2_ITEM_DIRECTORY = "/fluiditem2item/";
	private static final String FLUID_ITEM_2_FLUID_DIRECTORY = "/itemfluid2fluid/";
	
	//O2O Machines
	private static final String MINERAL_CRUSHER_DIRECTORY = "mineralcrusher/";
	private static final String MINERAL_GRINDER_DIRECTORY = "mineralgrinder/";
	private static final String WIRE_MILL_DIRECTORY = "wiremill/";
	
	//DO2O Machines
	private static final String OXIDATION_FURNACE_DIRECTORY = "oxidationfurnace/";
	
	//FLUID2ITEM
	private static final String CHEMICAL_CRYSTALIZER_DIRECTORY = "chemicalcryst/";
	
	//FLUIDITEM2FLUID
	private static final String CHEMICAL_MIXER_DIRECTORY = "chemicalmixer/";
	private static final String FERMENTATION_PLANT_DIRECTORY = "fermentplant/";
	private static final String MINERAL_WASHER_DIRECTORY = "mineralwasher/";
	
	public static void main(String[] args) {
		o2ORecipes();
		dO2ORecipes();
		fluidItem2FluidRecipes();
	}
	
	
	public static void o2ORecipes() {
		
		/*WIRE MILL*/
		String wireMillRecipeID = WireMillRecipe.RECIPE_ID.toString();
		ArrayList<String[]> WIRE_MILL_RECIPES = new ArrayList<>();
		
		/**
		 * Ingots -> Wires
		 */
		
		
		
		/*MINERAL CRUSHER*/
		String mineralCrusherRecipeID = MineralCrusherRecipe.RECIPE_ID.toString();
		ArrayList<String[]> MINERAL_CRUSHER_RECIPES = new ArrayList<>();
		
		/**
		 * Ingots -> Plates
		 * Ores -> Impure Dust
		 * Crystals -> Impure Dust
		 * Obsidian -> Obsidian Dust
		 * Gravel -> Flint
		 */
		
		/*MINERAL_GRINDER*/
		String mineralGrinderRecipeID = MineralGrinderRecipe.RECIPE_ID.toString();
		ArrayList<String[]> MINERAL_GRINDER_RECIPES = new ArrayList<>();
		
		/**
		 * Ingots -> Dust
		 * Ores -> Dust
		 * Impure Dust -> Dust
		 * Vanilla Gem Ores -> Gems
		 * Stone -> Cobblestone
		 * Cobblestone -> Gravel
		 * Gravel -> Sand
		 */
		
		
		for (SubtypeIngot from : SubtypeIngot.values()) {
		    //Ingot -> Wires
			for (SubtypeWire to : SubtypeWire.values()) {
				if (from.name().equals(to.name())) { 
					WIRE_MILL_RECIPES.add(new String[] {
							to.name() + "_wire_from_" + from.name() + "_ingot",
							getO2ORecipe(wireMillRecipeID,"forge:"+from.forgeTag(),true,1,"electrodynamics:wire"+to.name(),1)
					});
				}
		    }
			//Ingot -> Dust
		    for (SubtypeDust to : SubtypeDust.values()) {
				if (from.name().equals(to.name())) {
				    MINERAL_GRINDER_RECIPES.add(new String[] {
				    		to.name() + "_dust_from_" + from.name()+ "_ingot",
				    		getO2ORecipe(mineralGrinderRecipeID,"forge:"+from.forgeTag(),true,1,"electrodynamics:dust"+to.name(),1)
				    });
				}
		    }
		    //Ingot -> Plate
		    for (SubtypePlate to : SubtypePlate.values()) {
				if (from.name().equals(to.name())) {
				    MINERAL_CRUSHER_RECIPES.add(new String[] {
				    		to.name() + "_plate_from_" + from.name()+"_ingot",
				    		getO2ORecipe(mineralCrusherRecipeID,"forge:"+from.forgeTag(),true,1,"electrodynamics:plate"+to.name(),1)
				    });
				}
		    }
		}
		
		//Ingot -> Wire
		WIRE_MILL_RECIPES.add(new String[] {
				SubtypeWire.gold.name() + "_wire_from_gold_ingot",
				getO2ORecipe(wireMillRecipeID,"forge:ingots/gold",true,1,"electrodynamics:wire"+SubtypeWire.gold.name(),1)
		});
		
		WIRE_MILL_RECIPES.add(new String[] {
				SubtypeWire.iron.name() + "_wire_from_iron_ingot",
				getO2ORecipe(wireMillRecipeID,"forge:ingots/iron",true,1,"electrodynamics:wire"+SubtypeWire.iron.name(),1)
		});
		
		//Ingot -> Dust
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		SubtypeDust.gold.name() + "_dust_from_gold_ingot",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:ingots/gold",true,1,"electrodynamics:dust"+SubtypeDust.gold.name(),1)
	    });
		
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		SubtypeDust.iron.name() + "_dust_from_iron_ingot",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:ingots/iron",true,1,"electrodynamics:dust"+SubtypeDust.iron.name(),1)
	    });
		
		//Ingot -> Plate
	    MINERAL_CRUSHER_RECIPES.add(new String[] {
	    		SubtypePlate.iron + "_plate_from_iron_ingot",
	    		getO2ORecipe(mineralCrusherRecipeID,"forge:ingots/iron",true,1,"electrodynamics:plate"+SubtypePlate.iron.name(),1)
	    });
	    
		
		for (SubtypeDust to : SubtypeDust.values()) {
			//Ore -> Dust
		    for (SubtypeOre from : SubtypeOre.values()) {
				if (to.name().equals(from.name().replace("vanadinite", "vanadium"))) {
				    MINERAL_GRINDER_RECIPES.add(new String[] {
				    		to.name() + "_dust_from_" + from.name() + "_ore",
				    		getO2ORecipe(mineralGrinderRecipeID,"forge:"+(from.forgeTag().replace("vanadinite", "vanadium")),true,1,"electrodynamics:dust"+to.name(),2)
				    });
				}
		    }
		    
		    //Impure Dust -> Dust
		    for (SubtypeImpureDust from : SubtypeImpureDust.values()) {
				if (to.name().equals(from.name().replace("vanadinite", "vanadium"))) {
				    MINERAL_GRINDER_RECIPES.add(new String[] {
				    		to.name() + "_dust_from_imp" + from.name()+ "_dust",
				    		getO2ORecipe(mineralGrinderRecipeID,"electrodynamics:impuredust"+from.name(),false,1,"electrodynamics:dust"+to.name(),1)
				    });
				}
		    }
		}
		
		//Ore -> Dust
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		SubtypeDust.sulfur.name() + "_dust_from_" + SubtypeOre.sulfur.name() + "_ore",
	    		getO2ORecipe(mineralGrinderRecipeID,SubtypeOre.sulfur.forgeTag(),true,1,"electrodynamics:dust"+SubtypeDust.sulfur.name(),1)
	    });
	    
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		SubtypeDust.gold.name() + "_dust_from_gold_ore",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:ores/gold",true,1,"electrodynamics:dust"+SubtypeDust.gold.name(),2)
	    });
	    
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		SubtypeDust.iron.name() + "_dust_from_iron_ore",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:ores/iron",true,1,"electrodynamics:dust"+SubtypeDust.iron.name(),2)
	    });
	    
		//Ore -> Gem
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		"coal_gem_from_coal_ore",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:ores/coal",true,1,"minecraft:coal",2)
	    });
	    
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		"lapis_gem_from_lapis_ore",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:ores/lapis",true,1,"minecraft:lapis_lazuli",9)
	    });
	    
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		"diamond_gem_from_diamond_ore",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:ores/diamond",true,1,"minecraft:diamond",2)
	    });
	    
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		"redstone_gem_from_redstone_ore",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:ores/redstone",true,1,"minecraft:redstone",6)
	    });

	    
		for (SubtypeImpureDust to : SubtypeImpureDust.values()) {
			//Ore -> Impure Dust
		    for (SubtypeOre from : SubtypeOre.values()) {
				if (to.name().equals(from.name().replace("vanadinite", "vanadium"))) {
				    MINERAL_CRUSHER_RECIPES.add(new String[] {
				    		"imp_" +to.name() + "_dust_from_" + from.name() + "_ore",
				    		getO2ORecipe(mineralCrusherRecipeID,"forge:"+(from.forgeTag().replace("vanadinite", "vanadium")),true,1,"electrodynamics:impuredust"+to.name(),3)
				    });
				}
		    }
		}
		
		//Ore -> Impure Dust
	    MINERAL_CRUSHER_RECIPES.add(new String[] {
	    		"imp_" + SubtypeImpureDust.gold.name() + "_dust_from_gold_ore",
	    		getO2ORecipe(mineralCrusherRecipeID,"forge:ores/gold",true,1,"electrodynamics:impuredust"+SubtypeImpureDust.gold.name(),3)
	    });
	    
	    MINERAL_CRUSHER_RECIPES.add(new String[] {
	    		"imp_" + SubtypeImpureDust.iron.name() + "_dust_from_iron_ore",
	    		getO2ORecipe(mineralCrusherRecipeID,"forge:ores/iron",true,1,"electrodynamics:impuredust"+SubtypeImpureDust.iron.name(),3)
	    });
	    
		for (SubtypeCrystal from : SubtypeCrystal.values()) {
			//Ore Crystal -> Impure Dust
		    for (SubtypeImpureDust to : SubtypeImpureDust.values()) {
				if (to.name().equals(from.name().replace("vanadinite", "vanadium"))) {
				    MINERAL_CRUSHER_RECIPES.add(new String[] {
				    		"imp_" + to.name() + "_dust_from_" + from.name() + "_cryst",
				    		getO2ORecipe(mineralCrusherRecipeID,"electrodynamics:crystal"+from.name(),false,1,"electrodynamics:impuredust"+to.name(),1)
				    });
				}
		    }
		}
		
		//Misc Mineral Crusher
	    MINERAL_CRUSHER_RECIPES.add(new String[] {
	    		"flint_from_gravel",
	    		getO2ORecipe(mineralCrusherRecipeID,"forge:gravel",true,1,"minecraft:flint",1)
	    });
	    
	    MINERAL_CRUSHER_RECIPES.add(new String[] {
	    		SubtypeDust.obsidian.name() + "_dust_from_obsidian",
	    		getO2ORecipe(mineralCrusherRecipeID,"forge:obsidian",true,1,"electrodynamics:dust" + SubtypeDust.obsidian.name(),2)
	    });
		
		//Misc Mineral Grinder
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		SubtypeDust.endereye.name() + "_from_ender_eye",
	    		getO2ORecipe(mineralGrinderRecipeID,"minecraft:ender_eye",false,1,"electrodynamics:dust" + SubtypeDust.endereye.name(),1)
	    });
	    
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		"sand_from_gravel",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:gravel",true,1,"minecraft:sand",1)
	    });
	    
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		"gravel_from_cobblestone",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:cobblestone",true,1,"minecraft:gravel",1)
	    });
	    
	    MINERAL_GRINDER_RECIPES.add(new String[] {
	    		"cobblestone_from_stone",
	    		getO2ORecipe(mineralGrinderRecipeID,"forge:stone",true,1,"minecraft:cobblestone",1)
	    });
		
	

	    /*WIRE MILL FILE GENERATION*/
	    
	    try {
	    	File file;
	    	BufferedWriter fileWriter;
	    	
	    	for(String[] wmRecipe:WIRE_MILL_RECIPES) {
	    		file = new File(RECIPE_DIRECTORY + O2O_DIRECTORY + WIRE_MILL_DIRECTORY + wmRecipe[0] + ".json");
	    		if(!file.exists()) {
					fileWriter = Files.newBufferedWriter(file.toPath());
					fileWriter.write(wmRecipe[1]);
					fileWriter.close();
	    		}
	    	}
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    /*MINERAL CRUSHER FILE GENERATION*/
	    
	    try {
	    	File file;
	    	BufferedWriter fileWriter;
	    	
	    	for(String[] mcRecipe:MINERAL_CRUSHER_RECIPES) {
	    		file = new File(RECIPE_DIRECTORY + O2O_DIRECTORY + MINERAL_CRUSHER_DIRECTORY + mcRecipe[0] + ".json");
	    		if(!file.exists()) {
					fileWriter = Files.newBufferedWriter(file.toPath());
					fileWriter.write(mcRecipe[1]);
					fileWriter.close();
	    		}
	    	}
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    /*MINERAL GRINDER FILE GENERATION*/
	    
	    try {
	    	File file;
	    	BufferedWriter fileWriter;
	    	
	    	for(String[] mgRecipe:MINERAL_GRINDER_RECIPES) {
	    		file = new File(RECIPE_DIRECTORY + O2O_DIRECTORY + MINERAL_GRINDER_DIRECTORY + mgRecipe[0] + ".json");
	    		if(!file.exists()) {
					fileWriter = Files.newBufferedWriter(file.toPath());
					fileWriter.write(mgRecipe[1]);
					fileWriter.close();
	    		}
	    	}
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		
	}
	
	public static void dO2ORecipes(){
		
		/*OXIDATION FURNACE*/
		String oxidationFurnaceRecipeID = OxidationFurnaceRecipe.RECIPE_ID.toString();
		ArrayList<String[]> OXIDATION_FURNACE_RECIPES = new ArrayList<>();
		
		/**
		 * Sulfur + Coal/Charcoal -> Sufur Dioxide
		 * Vanadium Dust + Coal/Charcoal -> Vanadium Dioxide
		 * Vanadium Dioxide + Sulfur Dioxide -> Sulfur Trioxide
		 */
		
		OXIDATION_FURNACE_RECIPES.add(new String[] {
				SubtypeOxide.disulfur.name() + "_oxide",
				getDO2ORecipe(oxidationFurnaceRecipeID, "forge:"+ SubtypeDust.sulfur.forgeTag(), true,1, "minecraft:coals", true,1, "electrodynamics:oxide" + SubtypeOxide.disulfur.name(), 1)
		});
		
		OXIDATION_FURNACE_RECIPES.add(new String[] {
				SubtypeOxide.vanadium.name() + "_oxide",
				getDO2ORecipe(oxidationFurnaceRecipeID, "forge:"+ SubtypeDust.vanadium.forgeTag(), true,1, "minecraft:coals", true,1, "electrodynamics:oxide" + SubtypeOxide.vanadium.name(), 1)
		});
		
		OXIDATION_FURNACE_RECIPES.add(new String[] {
				SubtypeOxide.trisulfur.name() + "_oxide",
				getDO2ORecipe(oxidationFurnaceRecipeID, "forge:"+ SubtypeOxide.vanadium.forgeTag(), true,1, "forge:" + SubtypeOxide.disulfur.forgeTag(), true,1, "electrodynamics:oxide" + SubtypeOxide.trisulfur.name(), 1)
		});
		
		
		/*OXIDATION FURNACE FILE GENERATION*/
	    try {
	    	File file;
	    	BufferedWriter fileWriter;
	    	
	    	for(String[] ofRecipe:OXIDATION_FURNACE_RECIPES) {
	    		file = new File(RECIPE_DIRECTORY + DO2O_DIRECTORY + OXIDATION_FURNACE_DIRECTORY + ofRecipe[0] + ".json");
	    		if(!file.exists()) {
					fileWriter = Files.newBufferedWriter(file.toPath());
					fileWriter.write(ofRecipe[1]);
					fileWriter.close();
	    		}
	    	}
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }

	}
	
	public static void fluid2FluidRecipes(){
		
	}
	
	public static void fluid2ItemRecipes(){
		
	}
	
	public static void item2FluidRecipes(){
		
	}
	
	public static void fluidItem2ItemRecipes(){
		
	}
	
	public static void fluidItem2FluidRecipes(){
		
		/*CHEMICAL MIXER*/
		String chemicalMixerRecipeID = ChemicalMixerRecipe.RECIPE_ID.toString();
		ArrayList<String[]> CHEMICAL_MIXER_RECIPES = new ArrayList<>();
		
		/**
		 * Sulfur Trioxide + Water -> Sulfuric Acid
		 */
		
		
		String fermentationPlantRecipeId = FermentationPlantRecipe.RECIPE_ID.toString();
		ArrayList<String[]> FERMENTATION_PLANT_RECIPES = new ArrayList<>();
		
		/**
		 * Any Seeds + Water -> Ethanol
		 * Carrots + Water -> Ethanol
		 * Any Mushroom + Water -> Ethanol
		 * Potatos + Water -> Ethanol
		 * Wheat + Water -> Ethanol
		 * Pumpkin(Block) + Water -> Ethanol
		 * Melon Slice + Water -> Ethanol
		 * Sugar Cane + Water -> Ethanol
		 */
		
		
		CHEMICAL_MIXER_RECIPES.add(new String[] {
				"sulfuric_acid",
				getFluidItem2FluidRecipe(chemicalMixerRecipeID, "forge:oxide/trisulfur", true,1, "minecraft:water", 1000, "electrodynamics:fluidsulfuricacid", 2500)
		});
		
		
		
		FERMENTATION_PLANT_RECIPES.add(new String[] {
				"ethanol_from_seeds",
				getFluidItem2FluidRecipe(fermentationPlantRecipeId, "forge:seeds", true,9, "minecraft:water", 1000, "electrodynamics:fluidethanol", 100)
		});
		
		FERMENTATION_PLANT_RECIPES.add(new String[] {
				"ethanol_from_carrots",
				getFluidItem2FluidRecipe(fermentationPlantRecipeId, "forge:crops/carrot", true,12, "minecraft:water", 1000, "electrodynamics:fluidethanol", 100)
		});
		
		FERMENTATION_PLANT_RECIPES.add(new String[] {
				"ethanol_from_mushrooms",
				getFluidItem2FluidRecipe(fermentationPlantRecipeId, "forge:mushrooms", true,11, "minecraft:water", 1000, "electrodynamics:fluidethanol", 100)
		});
		
		FERMENTATION_PLANT_RECIPES.add(new String[] {
				"ethanol_from_potatos",
				getFluidItem2FluidRecipe(fermentationPlantRecipeId, "forge:crops/potato", true,13, "minecraft:water", 1000, "electrodynamics:fluidethanol", 100)
		});
		
		FERMENTATION_PLANT_RECIPES.add(new String[] {
				"ethanol_from_wheat",
				getFluidItem2FluidRecipe(fermentationPlantRecipeId, "forge:crops/wheat", true,13, "minecraft:water", 1000, "electrodynamics:fluidethanol", 100)
		});
		
		FERMENTATION_PLANT_RECIPES.add(new String[] {
				"ethanol_from_pumpkins",
				getFluidItem2FluidRecipe(fermentationPlantRecipeId, "minecraft:pumpkin", false,25, "minecraft:water", 1000, "electrodynamics:fluidethanol", 100)
		});
		
		FERMENTATION_PLANT_RECIPES.add(new String[] {
				"ethanol_from_melon_slices",
				getFluidItem2FluidRecipe(fermentationPlantRecipeId, "minecraft:melon_slice", false,3, "minecraft:water", 1000, "electrodynamics:fluidethanol", 100)
		});
		
		FERMENTATION_PLANT_RECIPES.add(new String[] {
				"ethanol_from_sugar_cane",
				getFluidItem2FluidRecipe(fermentationPlantRecipeId, "minecraft:sugar_cane", false,15, "minecraft:water", 1000, "electrodynamics:fluidethanol", 100)
		});
		
		
		/*CHEMICAL MIXER FILE GENERATION*/
	    try {
	    	File file;
	    	BufferedWriter fileWriter;
	    	
	    	for(String[] cmRecipe:CHEMICAL_MIXER_RECIPES) {
	    		file = new File(RECIPE_DIRECTORY + FLUID_ITEM_2_FLUID_DIRECTORY + CHEMICAL_MIXER_DIRECTORY+ cmRecipe[0] + ".json");
	    		if(!file.exists()) {
					fileWriter = Files.newBufferedWriter(file.toPath());
					fileWriter.write(cmRecipe[1]);
					fileWriter.close();
	    		}
	    	}
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    /*FERMENTATION PLANT FILE GENERATION*/
	    try {
	    	File file;
	    	BufferedWriter fileWriter;
	    	
	    	for(String[] fpRecipe:FERMENTATION_PLANT_RECIPES) {
	    		file = new File(RECIPE_DIRECTORY + FLUID_ITEM_2_FLUID_DIRECTORY + FERMENTATION_PLANT_DIRECTORY + fpRecipe[0] + ".json");
	    		if(!file.exists()) {
					fileWriter = Files.newBufferedWriter(file.toPath());
					fileWriter.write(fpRecipe[1]);
					fileWriter.close();
	    		}
	    	}
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String getO2ORecipe(String resourceName, String itemInput, boolean isTag, int inputCount,
			String itemOutput, int count) {
		
		String o2ORecipe = "{\n"
				+ "    \"type\":\"" + resourceName + "\",\n"
				+ "\n"
				+ "    \"input\":{\n"
				+ "        " + (isTag?"\"tag\":\"" : "\"item\":\"") + itemInput + "\",\n"
				+ "        \"count\":" + inputCount + "\n"
				+ "    },\n"
				+ "    \"output\":{\n"
				+ "        \"item\":\"" + itemOutput + "\",\n"
				+ "        \"count\":" + count + "\n"
				+ "    }\n"
				+ "}";
		
		return o2ORecipe;
	}
	
	public static String getDO2ORecipe(String resourceName, String itemInput1, boolean is1Tag, int input1Count,
			String itemInput2, boolean is2Tag, int input2Count, String itemOutput, int count) {
		
		String dO2ORecipe = "{\n"
				+ "    \"type\":\"" + resourceName + "\",\n"
				+ "\n"
				+ "    \"input1\":{\n"
				+ "        " + (is1Tag?"\"tag\":\"" : "\"item\":\"") + itemInput1 + "\",\n"
						+ "        \"count\":" + input1Count + "\n"
				+ "    },\n"
				+ "    \"input2\":{\n"
				+ "        " + (is2Tag?"\"tag\":\"" : "\"item\":\"") + itemInput2 + "\",\n"
						+ "        \"count\":" + input2Count + "\n"
				+ "    },\n"
				+ "    \"output\":{\n"
				+ "        \"item\":\"" + itemOutput + "\",\n"
				+ "        \"count\":" + count + "\n"
				+ "    }\n"
				+ "}";
		
		return dO2ORecipe;
		
	}
	
	public static String getFluid2FluidRecipe(String resourceName, String fluidInput, int inputCount, 
			String fluidOutput, int outputCount) {
		
		String fluid2FluidRecipe = "{\n"
				+ "    \"type\":\"" + resourceName + "\",\n"
				+ "\n"
				+ "    \"fluid_input\":{\n"
				+ "        \"fluid\":\"" + fluidInput + "\",\n"
				+ "        \"amount\":" + inputCount + "\n"				
				+ "    },\n"
				+ "    \"fluid_output\":{\n"
				+ "        \"fluid\":\"" + fluidOutput + "\",\n"
				+ "        \"amount\":" + outputCount + "\n"	
				+ "    }\n"
				+ "}";
		
		return fluid2FluidRecipe;
		
	}
	
	public static String getItem2FluidRecipe(String resourceName, String itemInput, boolean isTag, 
			String fluidOutput, int outputCount) {
		
		String fluid2FluidRecipe = "{\n"
				+ "    \"type\":\"" + resourceName + "\",\n"
				+ "\n"
				+ "    \"item_input\":{\n"
				+ "        " + (isTag?"\"tag\":\"" : "\"item\":\"") + itemInput + "\"\n"
				+ "    },\n"
				+ "    \"fluid_output\":{\n"
				+ "        \"fluid\":\"" + fluidOutput + "\",\n"
				+ "        \"amount\":" + outputCount + "\n"	
				+ "    }\n"
				+ "}";
		
		return fluid2FluidRecipe;
		
	}
	
	public static String getFluid2ItemRecipe(String resourceName, String fluidInput, int inputCount, 
			String itemOutput, int outputCount) {
		
		String fluid2ItemRecipe = "{\n"
				+ "    \"type\":\"" + resourceName + "\",\n"
				+ "\n"
				+ "    \"fluid_input\":{\n"
				+ "        \"fluid\":\"" + fluidInput + "\",\n"
				+ "        \"amount\":" + inputCount + "\n"				
				+ "    },\n"
				+ "    \"item_output\":{\n"
				+ "        \"item\":\"" + itemOutput + "\",\n"
				+ "        \"count\":" + outputCount + "\n"	
				+ "    }\n"
				+ "}";
		
		return fluid2ItemRecipe;
		
	}
	
	public static String getFluidItem2ItemRecipe(String resourceName, String itemInput, boolean isItemTag, int inputItemCount,
			String fluidInput, int inputFluidCount, String itemOutput, int outputCount) {
		
		String fluidItem2ItemRecipe = "{\n"
				+ "    \"type\":\"" + resourceName + "\",\n"
				+ "\n"
				+ "    \"item_input\":{\n"
				+ "        " + (isItemTag?"\"tag\":\"" : "\"item\":\"") + itemInput + "\",\n"
						+ "        \"count\":" + inputItemCount + "\n"
				+ "    },\n"
				+ "    \"fluid_input\":{\n"
				+ "        \"fluid\":\"" + fluidInput + "\",\n"
				+ "        \"amount\":" + inputFluidCount + "\n"				
				+ "    },\n"
				+ "    \"item_output\":{\n"
				+ "        \"item\":\"" + itemOutput + "\",\n"
				+ "        \"count\":" + outputCount + "\n"	
				+ "    }\n"
				+ "}";
		
		return fluidItem2ItemRecipe;
		
	}
	
	public static String getFluidItem2FluidRecipe(String resourceName, String itemInput, boolean isItemTag, int inputItemCount,
			String fluidInput, int inputFluidCount, String fluidOutput, int outputCount) {
		
		String fluidItem2FluidRecipe = "{\n"
				+ "    \"type\":\"" + resourceName + "\",\n"
				+ "\n"
				+ "    \"item_input\":{\n"
				+ "        " + (isItemTag?"\"tag\":\"" : "\"item\":\"") + itemInput + "\",\n"
						+ "        \"count\":" + inputItemCount + "\n"
				+ "    },\n"
				+ "    \"fluid_input\":{\n"
				+ "        \"fluid\":\"" + fluidInput + "\",\n"
				+ "        \"amount\":" + inputFluidCount + "\n"				
				+ "    },\n"
				+ "    \"fluid_output\":{\n"
				+ "        \"fluid\":\"" + fluidOutput + "\",\n"
				+ "        \"amount\":" + outputCount + "\n"	
				+ "    }\n"
				+ "}";
		
		return fluidItem2FluidRecipe;
		
	}
	
	
	
}
