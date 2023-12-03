package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidConcrete extends FluidNonPlaceable {

	public static final String FORGE_TAG = "concrete";

	public FluidConcrete() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "concrete");
	}

}
