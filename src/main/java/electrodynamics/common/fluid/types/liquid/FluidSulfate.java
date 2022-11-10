package electrodynamics.common.fluid.types.liquid;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
import net.minecraftforge.fluids.FluidType;

public class FluidSulfate extends FluidNonPlaceable {

	public static final String FORGE_TAG = "_sulfate";

	public SubtypeSulfateFluid mineral;

	public FluidSulfate(SubtypeSulfateFluid mineral) {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
		this.mineral = mineral;
		type = new SimpleWaterBasedFluidType(References.ID, mineral.name() + "sulfate");
	}

	private final FluidType type;

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
