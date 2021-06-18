package electrodynamics.compatability.jei.recipecategories.psuedorecipes;

import java.util.ArrayList;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeMineralFluid;
import electrodynamics.common.item.subtype.SubtypeOxide;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoRecipes {

    /* Item/Fluid Storage */

    public static ArrayList<ArrayList<ItemStack>> ELECTRODYNAMICS_ITEMS = new ArrayList<>();
    public static ArrayList<Fluid> ELECTRODYNAMICS_FLUIDS = new ArrayList<>();
    public static ArrayList<ItemStack> ELECTRODYNAMICS_MACHINES = new ArrayList<>();

    public static ArrayList<Psuedo5XRecipe> X5_ORE_RECIPES = new ArrayList<>();

    // private static final Logger logger =
    // LogManager.getLogger(ElectrodynamicsPatches.MOD_ID);

    public static void addElectrodynamicsRecipes() {
	addElectrodynamicsMachines();
	addElectrodynamicsFluids();
	addElectrodynamicsItems();

	/* 5x Ore Processing */

	// Iron
	X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(0), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
		new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1), 1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(2), 1000),
		ELECTRODYNAMICS_ITEMS.get(2).get(0)));
	// Gold
	X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(1), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
		new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1), 1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(3), 1000),
		ELECTRODYNAMICS_ITEMS.get(2).get(1)));
	// Copper
	X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(2), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
		new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1), 1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(4), 1000),
		ELECTRODYNAMICS_ITEMS.get(2).get(2)));
	// Tin
	X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(3), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
		new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1), 1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(5), 1000),
		ELECTRODYNAMICS_ITEMS.get(2).get(3)));
	// Lead
	X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(4), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
		new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1), 1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(6), 1000),
		ELECTRODYNAMICS_ITEMS.get(2).get(4)));
	// Silver
	X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(5), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
		new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1), 1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(7), 1000),
		ELECTRODYNAMICS_ITEMS.get(2).get(5)));
	// Vanadium
	X5_ORE_RECIPES.add(new Psuedo5XRecipe(ELECTRODYNAMICS_ITEMS.get(0).get(6), ELECTRODYNAMICS_MACHINES.get(0), ELECTRODYNAMICS_MACHINES.get(1),
		new FluidStack(ELECTRODYNAMICS_FLUIDS.get(1), 1000), new FluidStack(ELECTRODYNAMICS_FLUIDS.get(8), 1000),
		ELECTRODYNAMICS_ITEMS.get(2).get(6)));

    }

    public static void addElectrodynamicsMachines() {
	// Mineral Washer
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralwasher)));
	// Chemical Crystallizer
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer)));
	// Chemical Mixer
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalmixer)));
	// Coal Generator
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgenerator)));
	// Upgrade Transformer
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.upgradetransformer)));
	// Downgrade Transformer
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.downgradetransformer)));
	// Solar Panel
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.solarpanel)));
	// Advanced Solar Panel
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.advancedsolarpanel)));
	// Thermoelectric Generator
	ELECTRODYNAMICS_MACHINES
		.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.thermoelectricgenerator)));
	// Combustion Chamber
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber)));
	// Hydroelectric Generator
	ELECTRODYNAMICS_MACHINES
		.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.hydroelectricgenerator)));
	// Wind Generator
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.windmill)));
	// Fermentation Chamber
	ELECTRODYNAMICS_MACHINES.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fermentationplant)));

    }

    public static void addElectrodynamicsFluids() {
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

    public static void addElectrodynamicsItems() {
	/* ORES : 0 */
	Item[] ores = { Blocks.IRON_ORE.getBlock().asItem(), Blocks.GOLD_ORE.getBlock().asItem(),
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

	/* Ore Crystals : 1 and 2 */
	Item[] oreCrystals = { electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.iron),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.gold),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.copper),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.tin),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.lead),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.silver),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.vanadium)

	};

	// Adds individual items for reference
	ELECTRODYNAMICS_ITEMS.add(formItemStacks(oreCrystals, 1));

	// Adds Crystalyzation output stack size of crystals
	ELECTRODYNAMICS_ITEMS.add(formItemStacks(oreCrystals, 5));

	/* MISC ITEMS : 3 */
	Item[] miscIngredients = { electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeOxide.trisulfur), Items.WATER_BUCKET

	};
	ELECTRODYNAMICS_ITEMS.add(formItemStacks(miscIngredients, 1));

    }

    private static ArrayList<ItemStack> formItemStacks(Item[] items, int countPerItemStack) {
	ArrayList<ItemStack> inputItems = new ArrayList<>();

	for (int i = 0; i < items.length; i++) {
	    inputItems.add(new ItemStack(items[i]));
	    inputItems.get(i).setCount(countPerItemStack);
	}
	return inputItems;
    }

}
