package electrodynamics.common.fluid.types;

import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraftforge.fluids.FluidType;
import voltaic.common.fluid.FluidNonPlaceable;

public class FluidSulfate extends FluidNonPlaceable {

    public SubtypeSulfateFluid mineral;

    public FluidSulfate(SubtypeSulfateFluid mineral, FluidType type) {
	super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(), type);
	this.mineral = mineral;
    }

}
