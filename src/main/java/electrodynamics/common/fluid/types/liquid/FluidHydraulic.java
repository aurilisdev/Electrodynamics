package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraftforge.fluids.FluidType;

public class FluidHydraulic extends FluidNonPlaceable {

	public static final String FORGE_TAG = "hydraulic_fluid";

	private final FluidType type;

	public FluidHydraulic() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "fluidhydraulic");
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
