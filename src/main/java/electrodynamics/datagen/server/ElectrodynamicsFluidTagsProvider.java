package electrodynamics.datagen.server;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import voltaic.common.tags.VoltaicTags;

public class ElectrodynamicsFluidTagsProvider extends FluidTagsProvider {

	public ElectrodynamicsFluidTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Electrodynamics.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(VoltaicTags.Fluids.CLAY).add(ElectrodynamicsFluids.FLUID_CLAY.get());
		tag(VoltaicTags.Fluids.ETHANOL).add(ElectrodynamicsFluids.FLUID_ETHANOL.get());
		tag(VoltaicTags.Fluids.HYDRAULIC_FLUID).add(ElectrodynamicsFluids.FLUID_HYDRAULIC.get());
		tag(VoltaicTags.Fluids.HYDROGEN).add(ElectrodynamicsFluids.FLUID_HYDROGEN.get());
		tag(VoltaicTags.Fluids.HYDROFLUORIC_ACID).add(ElectrodynamicsFluids.FLUID_HYDROFLUORICACID.get());
		tag(VoltaicTags.Fluids.OXYGEN).add(ElectrodynamicsFluids.FLUID_OXYGEN.get());
		tag(VoltaicTags.Fluids.POLYETHLYENE).add(ElectrodynamicsFluids.FLUID_POLYETHYLENE.get());
		tag(VoltaicTags.Fluids.SULFURIC_ACID).add(ElectrodynamicsFluids.FLUID_SULFURICACID.get());
		tag(VoltaicTags.Fluids.AMMONIA).add(ElectrodynamicsFluids.FLUID_AMMONIA.get());
		for (SubtypeSulfateFluid sulfate : SubtypeSulfateFluid.values()) {
			tag(sulfate.tag).add(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(sulfate));
		}

	}

}
