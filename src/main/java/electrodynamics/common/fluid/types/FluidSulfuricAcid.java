package electrodynamics.common.fluid.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidSulfuricAcid extends FluidNonPlaceable {

    public FluidSulfuricAcid() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "sulfuricacid", -375879936);
	}

}
