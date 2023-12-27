package electrodynamics.common.fluid.types;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidPolyethylene extends FluidNonPlaceable {

	public static final String FORGE_TAG = "polyethylene";

	public FluidPolyethylene() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "polyethylene", -376664948);
	}

}