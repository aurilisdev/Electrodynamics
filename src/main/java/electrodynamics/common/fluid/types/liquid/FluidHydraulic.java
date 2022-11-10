package electrodynamics.common.fluid.types.liquid;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import net.minecraftforge.fluids.FluidType;

public class FluidHydraulic extends FluidNonPlaceable {

	public static final String FORGE_TAG = "hydraulic_fluid";

	private final FluidType type;

	public FluidHydraulic() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "hydraulic");
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
