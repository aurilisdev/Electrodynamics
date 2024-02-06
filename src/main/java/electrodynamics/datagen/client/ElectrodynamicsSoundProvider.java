package electrodynamics.datagen.client;

import electrodynamics.api.References;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinition.Sound;
import net.neoforged.neoforge.common.data.SoundDefinition.SoundType;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ElectrodynamicsSoundProvider extends SoundDefinitionsProvider {

	private final String modID;

	public ElectrodynamicsSoundProvider(PackOutput output, ExistingFileHelper helper) {
		this(output, helper, References.ID);
	}

	public ElectrodynamicsSoundProvider(PackOutput output, ExistingFileHelper helper, String modID) {
		super(output, modID, helper);
		this.modID = modID;
	}

	@Override
	public void registerSounds() {
		add(ElectrodynamicsSounds.SOUND_CERAMICPLATEADDED);
		add(ElectrodynamicsSounds.SOUND_CERAMICPLATEBREAKING);
		add(ElectrodynamicsSounds.SOUND_COMBUSTIONCHAMBER);
		add(ElectrodynamicsSounds.SOUND_ELECTRICPUMP);
		add(ElectrodynamicsSounds.SOUND_ELECTROLYTICSEPARATOR);
		add(ElectrodynamicsSounds.SOUND_EQUIPHEAVYARMOR);
		add(ElectrodynamicsSounds.SOUND_HUM);
		add(ElectrodynamicsSounds.SOUND_HYDRAULICBOOTS);
		add(ElectrodynamicsSounds.SOUND_HYDROELECTRICGENERATOR);
		add(ElectrodynamicsSounds.SOUND_JETPACK);
		add(ElectrodynamicsSounds.SOUND_JETPACKSWITCHMODE);
		add(ElectrodynamicsSounds.SOUND_LATHEPLAYING);
		add(ElectrodynamicsSounds.SOUND_MINERALCRUSHER);
		add(ElectrodynamicsSounds.SOUND_MINERALGRINDER);
		add(ElectrodynamicsSounds.SOUND_MOTORRUNNING);
		add(ElectrodynamicsSounds.SOUND_NIGHTVISIONGOGGLESOFF);
		add(ElectrodynamicsSounds.SOUND_NIGHTVISIONGOGGLESON);
		add(ElectrodynamicsSounds.SOUND_RAILGUNKINETIC);
		add(ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO);
		add(ElectrodynamicsSounds.SOUND_RAILGUNPLASMA_FIRE);
		add(ElectrodynamicsSounds.SOUND_RAILGUNPLASMA_HIT);
		add(ElectrodynamicsSounds.SOUND_RAILGUNPLASMA_NOPOWER);
		add(ElectrodynamicsSounds.SOUND_RODIMPACTINGGROUND);
		add(ElectrodynamicsSounds.SOUND_SEISMICSCANNER);
		add(ElectrodynamicsSounds.SOUND_WINDMILL);
		add(ElectrodynamicsSounds.SOUND_BATTERY_SWAP);
		add(ElectrodynamicsSounds.SOUND_PRESSURERELEASE);
		add(ElectrodynamicsSounds.SOUND_COMPRESSORRUNNING);
		add(ElectrodynamicsSounds.SOUND_DECOMPRESSORRUNNING);
		add(ElectrodynamicsSounds.SOUND_TRANSFORMERHUM);
	}

	private void add(DeferredHolder<SoundEvent, SoundEvent> sound) {
		add(sound.get(), SoundDefinition.definition().subtitle("subtitles." + modID + "." + sound.getId().getPath()).with(Sound.sound(sound.getId(), SoundType.SOUND)));
	}

}
