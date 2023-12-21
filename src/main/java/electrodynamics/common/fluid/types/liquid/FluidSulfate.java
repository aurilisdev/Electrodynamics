package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidSulfate extends FluidNonPlaceable {

	public static final String FORGE_TAG = "sulfate_";

	public SubtypeSulfateFluid mineral;

	public FluidSulfate(SubtypeSulfateFluid mineral) {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, References.ID, "fluidsulfate" + mineral.name());
		this.mineral = mineral;
	}

	@Override
	protected FluidAttributes createAttributes() {
		return FluidAttributes.builder(new ResourceLocation(References.ID + ":fluid/sulfate" + mineral.name()), new ResourceLocation(References.ID + ":fluid/sulfate" + mineral.name())).translationKey("fluid.electrodynamics.fluidsulfate" + mineral.name()).build(this);
	}

}
