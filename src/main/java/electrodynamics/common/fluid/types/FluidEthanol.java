package electrodynamics.common.fluid.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidEthanol extends FluidNonPlaceable {

	public static final String FORGE_TAG = "ethanol";

	public FluidEthanol() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "ethanol", -428574419);
	}

}
