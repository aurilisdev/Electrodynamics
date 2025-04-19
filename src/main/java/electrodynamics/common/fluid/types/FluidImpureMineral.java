package electrodynamics.common.fluid.types;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeImpureMineralFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.common.fluid.SimpleWaterBasedFluidType;

public class FluidImpureMineral extends FluidNonPlaceable {

    public SubtypeImpureMineralFluid subtype;
    public FluidImpureMineral(SubtypeImpureMineralFluid subtype) {
        super(ElectrodynamicsItems.ITEM_CANISTERREINFORCED, new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidimpuremineral" + subtype.name(), "impure/impuremineralfluid", subtype.color));
        this.subtype = subtype;
    }

    public SubtypeImpureMineralFluid getSubtype() {
        return subtype;
    }
}
