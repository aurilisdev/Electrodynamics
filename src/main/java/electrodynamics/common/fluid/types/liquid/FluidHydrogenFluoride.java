package electrodynamics.common.fluid.types.liquid;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import net.minecraftforge.fluids.FluidType;

public class FluidHydrogenFluoride extends FluidNonPlaceable {

	// Tags typically have underscores inbetween names!
	public static final String FORGE_TAG = "hydrofluoric_acid";

	private final FluidType type;

	public FluidHydrogenFluoride() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "hydrogenfluoride", -375879936);
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
