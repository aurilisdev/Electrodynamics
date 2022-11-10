package electrodynamics.common.fluid.types.liquid;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import net.minecraftforge.fluids.FluidType;

public class FluidClay extends FluidNonPlaceable {

	public static final String FORGE_TAG = "clay";

	private final FluidType type;

	public FluidClay() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "clay");
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
