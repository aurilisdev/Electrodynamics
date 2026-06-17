package electrodynamics.common.fluid.types;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypePureMineralFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.common.fluid.SimpleWaterBasedFluidType;

public class FluidPureMineral extends FluidNonPlaceable {

    public SubtypePureMineralFluid subtype;

    public FluidPureMineral(SubtypePureMineralFluid subtype) {
	super(ElectrodynamicsItems.ITEM_CANISTERREINFORCED, new SimpleWaterBasedFluidType(Electrodynamics.ID,
		"fluidpuremineral" + subtype.name(), "pure/" + subtype.name(), subtype.color));
	this.subtype = subtype;
    }

    public SubtypePureMineralFluid getSubtype() {
	return subtype;
    }
}
