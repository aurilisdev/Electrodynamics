package electrodynamics.common.fluid.types.gas;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import net.minecraftforge.fluids.FluidType;

public class FluidOxygen extends FluidNonPlaceable {

	public static final String FORGE_TAG = "oxygen";

	private final FluidType type;

	public FluidOxygen() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "oxygen");
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
