package electrodynamics.common.fluid.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidConcrete extends FluidNonPlaceable {

	public static final String FORGE_TAG = "concrete";

	public FluidConcrete() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "concrete");
	}

}
