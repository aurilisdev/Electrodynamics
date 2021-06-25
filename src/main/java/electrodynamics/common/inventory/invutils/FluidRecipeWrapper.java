package electrodynamics.common.inventory.invutils;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class FluidRecipeWrapper extends RecipeWrapper {

    private ArrayList<FluidTank> INPUT_TANKS;
    private ArrayList<FluidTank> OUTPUT_TANKS;

    public FluidRecipeWrapper(IItemHandlerModifiable inv, ArrayList<FluidTank> inputTanks, ArrayList<FluidTank> outputTanks) {
	super(inv);
	INPUT_TANKS = inputTanks;
	OUTPUT_TANKS = outputTanks;
    }

    @Nullable
    public FluidTank getInputTankInSlot(int tankSlot) {
	return INPUT_TANKS.get(tankSlot);
    }

    @Nullable
    public FluidTank getOutputTankInSlot(int tankSlot) {
	return OUTPUT_TANKS.get(tankSlot);
    }

}
