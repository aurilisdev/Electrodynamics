package electrodynamics.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import electrodynamics.api.References;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ElectrodynamicsFluidTagsProvider extends FluidTagsProvider {

	public ElectrodynamicsFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, References.ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider pProvider) {
		tag(ElectrodynamicsTags.Fluids.CLAY).add(ElectrodynamicsFluids.fluidClay);
		tag(ElectrodynamicsTags.Fluids.ETHANOL).add(ElectrodynamicsFluids.fluidEthanol);
		tag(ElectrodynamicsTags.Fluids.HYDRAULIC_FLUID).add(ElectrodynamicsFluids.fluidHydraulic);
		tag(ElectrodynamicsTags.Fluids.HYDROGEN).add(ElectrodynamicsFluids.fluidHydrogen);
		tag(ElectrodynamicsTags.Fluids.HYDROGEN_FLUORIDE).add(ElectrodynamicsFluids.fluidHydrogenFluoride);
		tag(ElectrodynamicsTags.Fluids.OXYGEN).add(ElectrodynamicsFluids.fluidOxygen);
		tag(ElectrodynamicsTags.Fluids.POLYETHLYENE).add(ElectrodynamicsFluids.fluidPolyethylene);
		tag(ElectrodynamicsTags.Fluids.SULFURIC_ACID).add(ElectrodynamicsFluids.fluidSulfuricAcid);
		for (SubtypeSulfateFluid sulfate : SubtypeSulfateFluid.values()) {
			tag(sulfate.tag).add(ElectrodynamicsFluids.SUBTYPEFLUID_REGISTRY_MAP.get(sulfate).get());
		}
	}

}
