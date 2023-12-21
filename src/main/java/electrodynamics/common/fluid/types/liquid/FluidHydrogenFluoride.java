package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidHydrogenFluoride extends FluidNonPlaceable {

	// Tags typically have underscores inbetween names!
	public static final String FORGE_TAG = "hydrofluoric_acid";

	public FluidHydrogenFluoride() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "hydrogenfluoride", -375879936);
	}

}
