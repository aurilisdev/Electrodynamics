package electrodynamics.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsDamageTypes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ElectrodynamicsDamageTagsProvider extends DamageTypeTagsProvider {

    public ElectrodynamicsDamageTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider,
	    ExistingFileHelper existingFileHelper) {
	super(output, lookupProvider, Electrodynamics.ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {
	tag(DamageTypeTags.BYPASSES_ARMOR).add(ElectrodynamicsDamageTypes.ACCELERATED_BOLT_IGNOREARMOR,
		ElectrodynamicsDamageTypes.PLASMA_BOLT);
	tag(DamageTypeTags.IS_PROJECTILE).add(ElectrodynamicsDamageTypes.ACCELERATED_BOLT,
		ElectrodynamicsDamageTypes.ACCELERATED_BOLT_IGNOREARMOR, ElectrodynamicsDamageTypes.PLASMA_BOLT);
	// .isMagic()
    }

}
