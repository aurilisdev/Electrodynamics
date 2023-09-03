package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraftforge.fluids.FluidType;

public class FluidHydrogen extends FluidNonPlaceable {

	public static final String FORGE_TAG = "hydrogen";

	private final FluidType type;

	public FluidHydrogen() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "fluidhydrogen");
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
