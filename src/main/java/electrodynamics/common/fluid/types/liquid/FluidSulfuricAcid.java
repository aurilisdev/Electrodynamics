package electrodynamics.common.fluid.types.liquid;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidSulfuricAcid extends FluidNonPlaceable {

	public static final String FORGE_TAG = "sulfuric_acid";

	public FluidSulfuricAcid() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "sulfuricacid", -375879936);
	}

}
