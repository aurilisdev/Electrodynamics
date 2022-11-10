package electrodynamics.common.fluid.types.liquid;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import net.minecraftforge.fluids.FluidType;

public class FluidConcrete extends FluidNonPlaceable {

	public static final String FORGE_TAG = "concrete";

	private final FluidType type;

	public FluidConcrete() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "concrete");
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
