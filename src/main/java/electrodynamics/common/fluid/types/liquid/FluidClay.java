package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidClay extends FluidNonPlaceable {

	public static final String FORGE_TAG = "clay";

	public FluidClay() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "clay");
	}

}
