package electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes;

import java.util.Arrays;
import java.util.HashSet;

import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoFluid2GasRecipe;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoGas2FluidRecipe;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;
import voltaic.registers.VoltaicGases;
import voltaic.registers.VoltaicRegistries;

public class ElectrodynamicsPsuedoRecipes {

	public static final HashSet<PsuedoFluid2GasRecipe> EVAPORATION_RECIPES = new HashSet<>();
	public static final HashSet<PsuedoGas2FluidRecipe> CONDENSATION_RECIPES = new HashSet<>();

	public static void initRecipes() {

		EVAPORATION_RECIPES.clear();
		CONDENSATION_RECIPES.clear();

		for (Gas gas : VoltaicRegistries.gasRegistry().getValues()) {

			if (gas.isEmpty()) {
				continue;
			}

			if (gas.getCondensedFluid().isSame(Fluids.EMPTY)) {
				continue;
			}

			ItemStack inputCylinder = new ItemStack(ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get());
			
			IGasHandlerItem inputHandler = inputCylinder.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM).orElse(CapabilityUtils.EMPTY_GAS_ITEM);
			
			inputHandler.fill(new GasStack(gas, 1000, gas.getCondensationTemp() + 1, Gas.PRESSURE_AT_SEA_LEVEL), GasAction.EXECUTE);

			ItemStack outputBucket = new ItemStack(gas.getCondensedFluid().getBucket());
			
			IFluidHandlerItem outputHandler = outputBucket.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(CapabilityUtils.EMPTY_FLUID_ITEM);
			
			if(outputHandler == CapabilityUtils.EMPTY_FLUID_ITEM) {
			    continue;
			}
			
			outputHandler.fill(new FluidStack(gas.getCondensedFluid(), 1000), FluidAction.EXECUTE);
            
            outputBucket = outputHandler.getContainer();

			CONDENSATION_RECIPES.add(new PsuedoGas2FluidRecipe(Arrays.asList(new GasStack(gas, 1000, gas.getCondensationTemp(), 1)), new FluidStack(gas.getCondensedFluid(), 1000), inputCylinder, outputBucket));

		}

		VoltaicGases.MAPPED_GASSES.forEach((fluid, gas) -> {

			if (fluid.isSame(Fluids.EMPTY)) {
				return;
			}

			ItemStack inputBucket = new ItemStack(fluid.getBucket());
			
			IFluidHandlerItem inputHandler = inputBucket.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(CapabilityUtils.EMPTY_FLUID_ITEM);
			
			if(inputHandler != CapabilityUtils.EMPTY_FLUID_ITEM) {
				
				inputHandler.fill(new FluidStack(fluid, 1000), FluidAction.EXECUTE);
				
				inputBucket = inputHandler.getContainer();
				
			}

			ItemStack outputCylinder = new ItemStack(ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get());
			
			IGasHandlerItem outputHandler = outputCylinder.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM).orElse(CapabilityUtils.EMPTY_GAS_ITEM);
			
			outputHandler.fill(new GasStack(gas, 1000, gas.getCondensationTemp() + 1, Gas.PRESSURE_AT_SEA_LEVEL), GasAction.EXECUTE);

			outputCylinder = outputHandler.getContainer();

			EVAPORATION_RECIPES.add(new PsuedoFluid2GasRecipe(Arrays.asList(new FluidStack(fluid, 1000)), new GasStack(gas, 1000, gas.getCondensationTemp(), 1), inputBucket, outputCylinder));

		});

	}

}
