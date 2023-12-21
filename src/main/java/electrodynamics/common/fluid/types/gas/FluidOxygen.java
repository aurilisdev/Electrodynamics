package electrodynamics.common.fluid.types.gas;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidOxygen extends FluidNonPlaceable {

	public static final String FORGE_TAG = "oxygen";

	public FluidOxygen() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "oxygen");
	}

}
