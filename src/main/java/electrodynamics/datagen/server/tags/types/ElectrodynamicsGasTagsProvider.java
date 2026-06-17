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
import net.minecraftforge.common.data.ExistingFileHelper;
import voltaic.api.gas.Gas;
import voltaic.common.tags.VoltaicTags;
import voltaic.registers.VoltaicRegistries;

public class ElectrodynamicsGasTagsProvider extends IntrinsicHolderTagsProvider<Gas> {

    public ElectrodynamicsGasTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
	    String modId, @Nullable ExistingFileHelper existingFileHelper) {
	super(output, VoltaicRegistries.GAS_REGISTRY_KEY, lookupProvider, gas -> ResourceKey
		.create(VoltaicRegistries.GAS_REGISTRY_KEY, VoltaicRegistries.gasRegistry().getKey(gas)), modId,
		existingFileHelper);
    }

    public ElectrodynamicsGasTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
	    ExistingFileHelper existingFileHelper) {
	this(output, lookupProvider, Electrodynamics.ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
	tag(VoltaicTags.Gases.HYDROGEN).add(ElectrodynamicsGases.HYDROGEN.get());
	tag(VoltaicTags.Gases.OXYGEN).add(ElectrodynamicsGases.OXYGEN.get());
	tag(VoltaicTags.Gases.STEAM).add(ElectrodynamicsGases.STEAM.get());
    }

}
