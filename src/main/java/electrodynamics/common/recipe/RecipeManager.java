package electrodynamics.common.recipe;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.prefab.tile.processing.DO2OProcessingRecipe;
import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class RecipeManager {
    @SubscribeEvent
    public static void onLoadEvent(FMLLoadCompleteEvent event) {
	for (SubtypeIngot from : SubtypeIngot.values()) {
	    for (SubtypeWire to : SubtypeWire.values()) {
		if (from.name().equals(to.name())) {
		    MachineRecipes.registerRecipe(DeferredRegisters.TILE_WIREMILL.get(), new O2OProcessingRecipe(from, to));
		}
	    }
	    for (SubtypeDust to : SubtypeDust.values()) {
		if (from.name().equals(to.name())) {
		    MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(from, to));
		}
	    }
	    for (SubtypePlate to : SubtypePlate.values()) {
		if (from.name().equals(to.name())) {
		    MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALCRUSHER.get(), new O2OProcessingRecipe(from, to));
		}
	    }
	}
	for (SubtypeDust to : SubtypeDust.values()) {
	    for (SubtypeOre from : SubtypeOre.values()) {
		if (to.name().equals(from.name().replace("vanadinite", "vanadium"))) {
		    MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(from, to, 2));
		}
	    }
	    for (SubtypeImpureDust from : SubtypeImpureDust.values()) {
		if (to.name().equals(from.name().replace("vanadinite", "vanadium"))) {
		    MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(from, to, 1));
		}
	    }
	}
	for (SubtypeCrystal from : SubtypeCrystal.values()) {
	    for (SubtypeImpureDust to : SubtypeImpureDust.values()) {
		if (to.name().equals(from.name().replace("vanadinite", "vanadium"))) {
		    MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALCRUSHER.get(), new O2OProcessingRecipe(from, to, 1));
		}
	    }
	}
	for (SubtypeImpureDust to : SubtypeImpureDust.values()) {
	    for (SubtypeOre from : SubtypeOre.values()) {
		if (to.name().equals(from.name().replace("vanadinite", "vanadium"))) {
		    MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALCRUSHER.get(), new O2OProcessingRecipe(from, to, 3));
		}
	    }
	}
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_WIREMILL.get(), new O2OProcessingRecipe(Items.GOLD_INGOT, SubtypeWire.gold));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_WIREMILL.get(), new O2OProcessingRecipe(Items.IRON_INGOT, SubtypeWire.iron));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(SubtypeOre.sulfur, SubtypeDust.sulfur, 1));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Blocks.GOLD_ORE, SubtypeDust.gold, 2));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(),
		new O2OProcessingRecipe(Blocks.NETHER_GOLD_ORE, SubtypeDust.gold, 2));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Blocks.IRON_ORE, SubtypeDust.iron, 2));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Items.ENDER_EYE, SubtypeDust.endereye));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Items.IRON_INGOT, SubtypeDust.iron));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Items.GOLD_INGOT, SubtypeDust.gold));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALCRUSHER.get(), new O2OProcessingRecipe(Items.IRON_INGOT, SubtypePlate.iron));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Items.FLINT, Items.GUNPOWDER));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALCRUSHER.get(), new O2OProcessingRecipe(Blocks.GRAVEL, Items.FLINT));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Blocks.GRAVEL, Blocks.SAND));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Blocks.COBBLESTONE, Blocks.GRAVEL));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Blocks.STONE, Blocks.COBBLESTONE));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Blocks.REDSTONE_ORE, Items.REDSTONE, 6));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Blocks.LAPIS_ORE, Items.LAPIS_LAZULI, 9));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Blocks.DIAMOND_ORE, Items.DIAMOND, 2));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALGRINDER.get(), new O2OProcessingRecipe(Blocks.COAL_ORE, Items.COAL, 2));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALCRUSHER.get(),
		new O2OProcessingRecipe(Blocks.GOLD_ORE, SubtypeImpureDust.gold, 3));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALCRUSHER.get(),
		new O2OProcessingRecipe(Blocks.NETHER_GOLD_ORE, SubtypeImpureDust.gold, 3));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALCRUSHER.get(),
		new O2OProcessingRecipe(Blocks.IRON_ORE, SubtypeImpureDust.iron, 3));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_MINERALCRUSHER.get(), new O2OProcessingRecipe(Blocks.OBSIDIAN, SubtypeDust.obsidian, 2));

	MachineRecipes.registerRecipe(DeferredRegisters.TILE_OXIDATIONFURNACE.get(),
		new DO2OProcessingRecipe(Items.COAL, SubtypeDust.sulfur, SubtypeOxide.disulfur));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_OXIDATIONFURNACE.get(),
		new DO2OProcessingRecipe(Items.CHARCOAL, SubtypeDust.sulfur, SubtypeOxide.disulfur));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_OXIDATIONFURNACE.get(),
		new DO2OProcessingRecipe(Items.COAL, SubtypeDust.vanadium, SubtypeOxide.vanadium));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_OXIDATIONFURNACE.get(),
		new DO2OProcessingRecipe(Items.CHARCOAL, SubtypeDust.vanadium, SubtypeOxide.vanadium));
	MachineRecipes.registerRecipe(DeferredRegisters.TILE_OXIDATIONFURNACE.get(),
		new DO2OProcessingRecipe(SubtypeOxide.vanadium, SubtypeOxide.disulfur, SubtypeOxide.trisulfur));
    }
}
