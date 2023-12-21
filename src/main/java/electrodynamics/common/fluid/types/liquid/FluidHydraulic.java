package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidHydraulic extends FluidNonPlaceable {

	public static final String FORGE_TAG = "hydraulic_fluid";

	public FluidHydraulic() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "hydraulic");
	}

}
