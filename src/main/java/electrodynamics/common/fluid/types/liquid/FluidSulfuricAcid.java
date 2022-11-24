package electrodynamics.common.fluid.types.liquid;

import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraftforge.fluids.FluidType;

public class FluidSulfuricAcid extends FluidNonPlaceable {

	public static final String FORGE_TAG = "sulfuric_acid";

	private final FluidType type;

	public FluidSulfuricAcid() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "fluidsulfuricacid", -375879936);
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
