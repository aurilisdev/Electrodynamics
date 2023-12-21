package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidSulfuricAcid extends FluidNonPlaceable {

	public static final String FORGE_TAG = "sulfuric_acid";

	public FluidSulfuricAcid() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "sulfuricacid", -375879936);
	}

}
