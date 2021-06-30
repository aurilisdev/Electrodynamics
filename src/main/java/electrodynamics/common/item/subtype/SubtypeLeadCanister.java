package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

public enum SubtypeLeadCanister implements ISubtype {
    empty(Fluids.EMPTY);

    private Fluid fluid;

    private SubtypeLeadCanister(Fluid fluid) {
	this.fluid = fluid;
    }

    public Fluid getFluid() {
	return fluid;
    }

    @Override
    public String tag() {
	return "leadcanister" + name();
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
