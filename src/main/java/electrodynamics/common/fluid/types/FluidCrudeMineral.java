package electrodynamics.common.fluid.types;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeCrudeMineralFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.common.fluid.SimpleWaterBasedFluidType;

public class FluidCrudeMineral extends FluidNonPlaceable {

    public SubtypeCrudeMineralFluid subtype;
    public FluidCrudeMineral(SubtypeCrudeMineralFluid subtype) {
        super(ElectrodynamicsItems.ITEM_CANISTERREINFORCED, new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidcrudemineral" + subtype.name(), "crude/crudemineralfluid", subtype.color));
        this.subtype = subtype;
    }

    public SubtypeCrudeMineralFluid getSubtype() {
        return subtype;
    }

}
