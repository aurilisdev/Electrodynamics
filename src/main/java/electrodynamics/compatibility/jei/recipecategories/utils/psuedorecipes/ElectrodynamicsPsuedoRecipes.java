package electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes;

import java.util.Arrays;
import java.util.HashSet;

import electrodynamics.common.reloadlistener.GasCollectorChromoCardsRegister;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoFluid2GasRecipe;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoGas2FluidRecipe;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoGasCollectorRecipe;
import electrodynamics.registers.ElectrodynamicsGases;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.registers.VoltaicCapabilities;
import voltaic.registers.VoltaicGases;

public class ElectrodynamicsPsuedoRecipes {

	public static final HashSet<PsuedoFluid2GasRecipe> EVAPORATION_RECIPES = new HashSet<>();
	public static final HashSet<PsuedoGas2FluidRecipe> CONDENSATION_RECIPES = new HashSet<>();

	public static final HashSet<PsuedoGasCollectorRecipe> GAS_COLLECTOR_RECIPES = new HashSet<>();

	public static void initRecipes() {

		EVAPORATION_RECIPES.clear();
		CONDENSATION_RECIPES.clear();

		for (Gas gas : VoltaicGases.GAS_REGISTRY.stream().toList()) {

			if (gas.isEmpty()) {
				continue;
			}

			if (gas.getCondensedFluid().isSame(Fluids.EMPTY)) {
				continue;
			}

			ItemStack inputCylinder = new ItemStack(ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get());
			
			IGasHandlerItem inputHandler = inputCylinder.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM);
			
			inputHandler.fill(new GasStack(gas, 1000, gas.getCondensationTemp() + 1, Gas.PRESSURE_AT_SEA_LEVEL), GasAction.EXECUTE);

			ItemStack outputBucket = new ItemStack(gas.getCondensedFluid().getBucket());
			
			IFluidHandlerItem outputHandler = outputBucket.getCapability(Capabilities.FluidHandler.ITEM);
			
			if(outputHandler == null) {
			    continue;
			}
			
			outputHandler.fill(new FluidStack(gas.getCondensedFluid(), 1000), FluidAction.EXECUTE);
            
            outputBucket = outputHandler.getContainer();

			CONDENSATION_RECIPES.add(new PsuedoGas2FluidRecipe(Arrays.asList(new GasStack(gas, 1000, gas.getCondensationTemp(), 1)), new FluidStack(gas.getCondensedFluid(), 1000), inputCylinder, outputBucket));

		}

		VoltaicGases.MAPPED_GASSES.forEach((fluid, gas) -> {

			if (fluid.value().isSame(Fluids.EMPTY)) {
				return;
			}

			ItemStack inputBucket = new ItemStack(fluid.value().getBucket());
			
			IFluidHandlerItem inputHandler = inputBucket.getCapability(Capabilities.FluidHandler.ITEM);
			
			if(inputHandler != null) {
				
				inputHandler.fill(new FluidStack(fluid, 1000), FluidAction.EXECUTE);
				
				inputBucket = inputHandler.getContainer();
				
			}

			ItemStack outputCylinder = new ItemStack(ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get());
			
			IGasHandlerItem outputHandler = outputCylinder.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM);
			
			outputHandler.fill(new GasStack(gas, 1000, gas.getCondensationTemp() + 1, Gas.PRESSURE_AT_SEA_LEVEL), GasAction.EXECUTE);

			outputCylinder = outputHandler.getContainer();

			EVAPORATION_RECIPES.add(new PsuedoFluid2GasRecipe(Arrays.asList(new FluidStack(fluid, 1000)), new GasStack(gas, 1000, gas.getCondensationTemp(), 1), inputBucket, outputCylinder));

		});

		GasCollectorChromoCardsRegister.INSTANCE.getEntries().forEach((item, result) -> {
			GAS_COLLECTOR_RECIPES.add(new PsuedoGasCollectorRecipe(new ItemStack(item), result));
		});

	}

}
