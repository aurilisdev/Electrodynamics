package electrodynamics.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import voltaic.api.gas.Gas;
import voltaic.common.tags.VoltaicTags;
import voltaic.registers.VoltaicGases;

public class ElectrodynamicsGasTagsProvider extends IntrinsicHolderTagsProvider<Gas> {

	public ElectrodynamicsGasTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, VoltaicGases.GAS_REGISTRY_KEY, lookupProvider, gas -> ResourceKey.create(VoltaicGases.GAS_REGISTRY_KEY, VoltaicGases.GAS_REGISTRY.getKey(gas)), modId, existingFileHelper);
	}

	public ElectrodynamicsGasTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		this(output, lookupProvider, Electrodynamics.ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider pProvider) {
		tag(VoltaicTags.Gases.HYDROGEN).add(ElectrodynamicsGases.HYDROGEN.value());
		tag(VoltaicTags.Gases.OXYGEN).add(ElectrodynamicsGases.OXYGEN.value());
		tag(VoltaicTags.Gases.STEAM).add(ElectrodynamicsGases.STEAM.value());
		tag(VoltaicTags.Gases.NITROGEN).add(ElectrodynamicsGases.NITROGEN.value());
		tag(VoltaicTags.Gases.ARGON).add(ElectrodynamicsGases.ARGON.value());
		tag(VoltaicTags.Gases.CARBON_DIOXIDE).add(ElectrodynamicsGases.CARBON_DIOXIDE.value());
		tag(VoltaicTags.Gases.SULFUR_DIOXIDE).add(ElectrodynamicsGases.SULFUR_DIOXIDE.value());
		tag(VoltaicTags.Gases.AMMONIA).add(ElectrodynamicsGases.AMMONIA.value());

		tag(VoltaicTags.Gases.IS_CORROSIVE).add(ElectrodynamicsGases.SULFUR_DIOXIDE.value());
	}

}
