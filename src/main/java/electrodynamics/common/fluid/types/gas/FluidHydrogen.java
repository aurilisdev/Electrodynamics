package electrodynamics.common.fluid.types.gas;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import net.minecraftforge.fluids.FluidType;

public class FluidHydrogen extends FluidNonPlaceable {

	public static final String FORGE_TAG = "hydrogen";

	private final FluidType type;

	public FluidHydrogen() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "hydrogen");
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
