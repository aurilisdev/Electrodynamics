package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidEthanol extends FluidNonPlaceable {

	public static final String FORGE_TAG = "ethanol";

	public FluidEthanol() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "ethanol", -428574419);
	}

}
