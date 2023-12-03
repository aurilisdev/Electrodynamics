package electrodynamics.common.fluid.types.gas;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidHydrogen extends FluidNonPlaceable {

	public static final String FORGE_TAG = "hydrogen";

	public FluidHydrogen() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "hydrogen");
	}

}
