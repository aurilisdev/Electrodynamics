package electrodynamics.common.fluid.types;

import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.prefab.utilities.math.Color;

public class FluidSulfate extends FluidNonPlaceable {

	public SubtypeSulfateFluid mineral;

	public FluidSulfate(SubtypeSulfateFluid mineral, String modId, String id, String texture, Color color) {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(), modId, id, texture, color);
		this.mineral = mineral;
	}

}
