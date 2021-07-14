package electrodynamics.common.item.subtype;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.ISubtype;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

public enum SubtypeCanister implements ISubtype {
    empty(Fluids.EMPTY), sulfuric(DeferredRegisters.fluidSulfuricAcid), ethanol(DeferredRegisters.fluidEthanol),
    polyethylene(DeferredRegisters.fluidPolyethylene), molybdenum(DeferredRegisters.fluidMolybdenum);

    private Fluid fluid;

    private SubtypeCanister(Fluid assocFluid) {
	fluid = assocFluid;
    }

    public Fluid getFluid() {
	return fluid;
    }

    @Override
    public String tag() {
	return "canister" + name();
    }

    @Override
    public String forgeTag() {
	return "buckets/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }

}
