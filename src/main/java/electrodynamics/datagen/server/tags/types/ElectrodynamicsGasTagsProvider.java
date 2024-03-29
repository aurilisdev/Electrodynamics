package electrodynamics.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsGases;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ElectrodynamicsGasTagsProvider extends IntrinsicHolderTagsProvider<Gas> {

	public ElectrodynamicsGasTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, ElectrodynamicsRegistries.GAS_REGISTRY_KEY, lookupProvider, gas -> ResourceKey.create(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, ElectrodynamicsRegistries.gasRegistry().getKey(gas)), modId, existingFileHelper);
	}

	public ElectrodynamicsGasTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		this(output, lookupProvider, References.ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider pProvider) {
		tag(ElectrodynamicsTags.Gases.HYDROGEN).add(ElectrodynamicsGases.HYDROGEN.get());
		tag(ElectrodynamicsTags.Gases.OXYGEN).add(ElectrodynamicsGases.OXYGEN.get());
		tag(ElectrodynamicsTags.Gases.STEAM).add(ElectrodynamicsGases.STEAM.get());

		tag(ElectrodynamicsTags.Gases.IS_CORROSIVE);
	}

}
