package electrodynamics.common.fluid.types;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeDirtyMineralFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.common.fluid.SimpleWaterBasedFluidType;

public class FluidDirtyMineral extends FluidNonPlaceable {

    public SubtypeDirtyMineralFluid subtype;

    public FluidDirtyMineral(SubtypeDirtyMineralFluid subtype) {
	super(ElectrodynamicsItems.ITEM_CANISTERREINFORCED, new SimpleWaterBasedFluidType(Electrodynamics.ID,
		"fluiddirtymineral" + subtype.name(), "dirty/dirtymineralfluid", subtype.color));
	this.subtype = subtype;
    }

    public SubtypeDirtyMineralFluid getSubtype() {
	return subtype;
    }
}
