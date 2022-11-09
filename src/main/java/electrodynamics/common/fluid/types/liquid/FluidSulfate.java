package electrodynamics.common.fluid.types.liquid;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;

public class FluidSulfate extends FluidNonPlaceable {

	public static final String FORGE_TAG = "_sulfate";

	public SubtypeSulfateFluid mineral;

	public FluidSulfate(SubtypeSulfateFluid mineral) {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
		this.mineral = mineral;
	}

// TODO: Fix fluid attributes
//	@Override
//	protected FluidAttributes createAttributes() {
//		return FluidAttributes.builder(new ResourceLocation(References.ID + ":fluid/" + mineral.name() + "sulfate"), new ResourceLocation(References.ID + ":fluid/" + mineral.name() + "sulfate")).translationKey("fluid.electrodynamics.sulfate" + mineral.name()).build(this);
//	}

}
