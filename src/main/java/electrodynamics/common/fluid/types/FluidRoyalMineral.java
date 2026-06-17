package electrodynamics.common.fluid.types;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeRoyalMineralFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.common.fluid.SimpleWaterBasedFluidType;

public class FluidRoyalMineral extends FluidNonPlaceable {

    public SubtypeRoyalMineralFluid subtype;

    public FluidRoyalMineral(SubtypeRoyalMineralFluid subtype) {
	super(ElectrodynamicsItems.ITEM_CANISTERREINFORCED, new SimpleWaterBasedFluidType(Electrodynamics.ID,
		"fluidroyalmineral" + subtype.name(), "royal/royalmineralfluid", subtype.color));
	this.subtype = subtype;
    }

    public SubtypeRoyalMineralFluid getSubtype() {
	return subtype;
    }
}
