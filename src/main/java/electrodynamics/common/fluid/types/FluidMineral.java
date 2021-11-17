package electrodynamics.common.fluid.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.item.subtype.SubtypeMineralFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidMineral extends FluidNonPlaceable {

    public static final String FORGE_TAG = "_mineral_fluid";

    public SubtypeMineralFluid mineral;

    public FluidMineral(SubtypeMineralFluid mineral) {
	super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED);
	this.mineral = mineral;
    }

    @Override
    protected FluidAttributes createAttributes() {
	return FluidAttributes.builder(new ResourceLocation(References.ID + ":fluid/mineral"), new ResourceLocation(References.ID + ":fluid/mineral"))
		.translationKey("fluid.electrodynamics.mineral" + mineral.name()).color(-1383766208).build(this);
    }

}
