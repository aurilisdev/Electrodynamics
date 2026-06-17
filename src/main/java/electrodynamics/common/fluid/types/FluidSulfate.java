package electrodynamics.common.fluid.types;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.common.fluid.SimpleWaterBasedFluidType;

public class FluidSulfate extends FluidNonPlaceable {

    public SubtypeSulfateFluid subtype;

    public FluidSulfate(SubtypeSulfateFluid subtype) {
	super(ElectrodynamicsItems.ITEM_CANISTERREINFORCED, new SimpleWaterBasedFluidType(Electrodynamics.ID,
		"fluidsulfate" + subtype.name(), "sulfate/" + subtype.name(), subtype.color));
	this.subtype = subtype;
    }

    public SubtypeSulfateFluid getSubtype() {
	return subtype;
    }
}
