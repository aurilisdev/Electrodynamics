package electrodynamics.common.fluid.types.liquid;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidSulfate extends FluidNonPlaceable {

	public static final String FORGE_TAG = "_sulfate";

	public SubtypeSulfateFluid mineral;

	public FluidSulfate(SubtypeSulfateFluid mineral) {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
		this.mineral = mineral;
	}

	@Override
	protected FluidAttributes createAttributes() {
		return FluidAttributes
				.builder(new ResourceLocation(References.ID + ":fluid/" + mineral.name() + "sulfate"),
						new ResourceLocation(References.ID + ":fluid/" + mineral.name() + "sulfate"))
				.translationKey("fluid.electrodynamics.sulfate" + mineral.name()).build(this);
	}

}
