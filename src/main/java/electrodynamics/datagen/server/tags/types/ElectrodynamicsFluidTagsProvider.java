package electrodynamics.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypePureMineralFluid;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import voltaic.common.tags.VoltaicTags;

public class ElectrodynamicsFluidTagsProvider extends FluidTagsProvider {

	public ElectrodynamicsFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Electrodynamics.ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider pProvider) {
		tag(VoltaicTags.Fluids.CLAY).add(ElectrodynamicsFluids.FLUID_CLAY.get());
		tag(VoltaicTags.Fluids.ETHANOL).add(ElectrodynamicsFluids.FLUID_ETHANOL.get());
		tag(VoltaicTags.Fluids.HYDRAULIC_FLUID).add(ElectrodynamicsFluids.FLUID_HYDRAULIC.get());
		tag(VoltaicTags.Fluids.HYDROGEN).add(ElectrodynamicsFluids.FLUID_HYDROGEN.get());
		tag(VoltaicTags.Fluids.HYDROFLUORIC_ACID).add(ElectrodynamicsFluids.FLUID_HYDROFLUORICACID.get());
		tag(VoltaicTags.Fluids.OXYGEN).add(ElectrodynamicsFluids.FLUID_OXYGEN.get());
		tag(VoltaicTags.Fluids.POLYETHLYENE).add(ElectrodynamicsFluids.FLUID_POLYETHYLENE.get());
		tag(VoltaicTags.Fluids.SULFURIC_ACID).add(ElectrodynamicsFluids.FLUID_SULFURICACID.get());
		tag(VoltaicTags.Fluids.NITRIC_ACID).add(ElectrodynamicsFluids.FLUID_NITRICACID.get());
		tag(VoltaicTags.Fluids.HYDROCHLORIC_ACID).add(ElectrodynamicsFluids.FLUID_HYDROCHLORICACID.get());
		tag(VoltaicTags.Fluids.AMMONIA).add(ElectrodynamicsFluids.FLUID_AMMONIA.get());
		tag(VoltaicTags.Fluids.AQUA_REGIA).add(ElectrodynamicsFluids.FLUID_AQUAREGIA.get());
		for (SubtypeSulfateFluid sulfate : SubtypeSulfateFluid.values()) {
			tag(sulfate.tag).add(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(sulfate));
		}
		for (SubtypePureMineralFluid pure : SubtypePureMineralFluid.values()) {
			tag(pure.tag).add(ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(pure));
		}
	}

}
