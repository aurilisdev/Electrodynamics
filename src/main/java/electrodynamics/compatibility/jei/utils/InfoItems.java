package electrodynamics.compatibility.jei.utils;

import java.util.ArrayList;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.world.item.ItemStack;

public class InfoItems {

    /* Item/Fluid Storage */

    public static ArrayList<ItemStack> ITEMS = new ArrayList<>();

    public static void addInfoItems() {
	// Coal Generator
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgenerator)));
	// Upgrade Transformer
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.upgradetransformer)));
	// Downgrade Transformer
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.downgradetransformer)));
	// Solar Panel
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.solarpanel)));
	// Advanced Solar Panel
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.advancedsolarpanel)));
	// Thermoelectric Generator
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.thermoelectricgenerator)));
	// Combustion Chamber
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber)));
	// Hydroelectric Generator
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.hydroelectricgenerator)));
	// Wind Generator
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.windmill)));
	// Steel Fluid Tank
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tanksteel)));
	// Reinforced Fluid Tank
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tankreinforced)));
	// HSLA Fluid Tank
	ITEMS.add(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tankhsla)));
    }

}
