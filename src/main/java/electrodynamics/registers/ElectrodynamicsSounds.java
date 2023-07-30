package electrodynamics.registers;

import electrodynamics.api.References;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.ID);

	public static final RegistryObject<SoundEvent> SOUND_HUM = sound("hum");
	public static final RegistryObject<SoundEvent> SOUND_MINERALCRUSHER = sound("mineralcrusher");
	public static final RegistryObject<SoundEvent> SOUND_MINERALGRINDER = sound("mineralgrinder");
	public static final RegistryObject<SoundEvent> SOUND_ELECTRICPUMP = sound("electricpump");
	public static final RegistryObject<SoundEvent> SOUND_COMBUSTIONCHAMBER = sound("combustionchamber");
	public static final RegistryObject<SoundEvent> SOUND_HYDROELECTRICGENERATOR = sound("hydroelectricgenerator");
	public static final RegistryObject<SoundEvent> SOUND_WINDMILL = sound("windmill");
	public static final RegistryObject<SoundEvent> SOUND_CERAMICPLATEBREAKING = sound("ceramicplatebreaking");
	public static final RegistryObject<SoundEvent> SOUND_CERAMICPLATEADDED = sound("ceramicplateadded");
	public static final RegistryObject<SoundEvent> SOUND_RAILGUNKINETIC = sound("railgunkinetic");
	public static final RegistryObject<SoundEvent> SOUND_RAILGUNKINETIC_NOAMMO = sound("railgunkinetic_noammo");
	public static final RegistryObject<SoundEvent> SOUND_RAILGUNPLASMA = sound("railgunplasma");
	public static final RegistryObject<SoundEvent> SOUND_RAILGUNPLASMA_NOPOWER = sound("railgunplasma_nopower");
	public static final RegistryObject<SoundEvent> SOUND_RODIMPACTINGGROUND = sound("rodhittingground");
	public static final RegistryObject<SoundEvent> SOUND_LATHEPLAYING = sound("latherunning");
	public static final RegistryObject<SoundEvent> SOUND_EQUIPHEAVYARMOR = sound("equipheavyarmor");
	public static final RegistryObject<SoundEvent> SOUND_SEISMICSCANNER = sound("seismicscanner");
	public static final RegistryObject<SoundEvent> SOUND_ELECTROLYTICSEPARATOR = sound("electrolyticseparator");
	public static final RegistryObject<SoundEvent> SOUND_NIGHTVISIONGOGGLESON = sound("nightvisiongoggleson");
	public static final RegistryObject<SoundEvent> SOUND_NIGHTVISIONGOGGLESOFF = sound("nightvisiongogglesoff");
	public static final RegistryObject<SoundEvent> SOUND_HYDRAULICBOOTS = sound("hydraulicboots");
	public static final RegistryObject<SoundEvent> SOUND_JETPACKSWITCHMODE = sound("jetpackswitchmode");
	public static final RegistryObject<SoundEvent> SOUND_JETPACK = sound("jetpack");
	public static final RegistryObject<SoundEvent> SOUND_MOTORRUNNING = sound("motorrunning");
	public static final RegistryObject<SoundEvent> SOUND_BATTERY_SWAP = sound("batteryswap");
	public static final RegistryObject<SoundEvent> SOUND_PRESSURERELEASE = sound("pressurerelease");
	public static final RegistryObject<SoundEvent> SOUND_COMPRESSORRUNNING = sound("compressorrunning");
	public static final RegistryObject<SoundEvent> SOUND_DECOMPRESSORRUNNING = sound("decompressorrunning");
	public static final RegistryObject<SoundEvent> SOUND_TRANSFORMERHUM = sound("transformerhum");

	private static RegistryObject<SoundEvent> sound(String name) {
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(References.ID + ":" + name)));
	}
}
