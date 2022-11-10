package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraftforge.fluids.FluidType;

public class FluidEthanol extends FluidNonPlaceable {

	public static final String FORGE_TAG = "ethanol";
	private final FluidType type;

	public FluidEthanol() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "ethanol", -428574419);
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
