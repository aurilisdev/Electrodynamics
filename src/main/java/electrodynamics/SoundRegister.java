package electrodynamics;

import com.google.common.base.Supplier;

import electrodynamics.api.References;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegister {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.ID);
	public static final RegistryObject<SoundEvent> SOUND_HUM = SOUNDS.register("hum",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":hum"))));
	public static final RegistryObject<SoundEvent> SOUND_MINERALCRUSHER = SOUNDS.register("mineralcrusher",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":mineralcrusher"))));
	public static final RegistryObject<SoundEvent> SOUND_MINERALGRINDER = SOUNDS.register("mineralgrinder",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":mineralgrinder"))));
	public static final RegistryObject<SoundEvent> SOUND_ELECTRICPUMP = SOUNDS.register("electricpump",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":electricpump"))));
	public static final RegistryObject<SoundEvent> SOUND_COMBUSTIONCHAMBER = SOUNDS.register("combustionchamber",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":combustionchamber"))));
	public static final RegistryObject<SoundEvent> SOUND_HYDROELECTRICGENERATOR = SOUNDS.register("hydroelectricgenerator",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":hydroelectricgenerator"))));
	public static final RegistryObject<SoundEvent> SOUND_WINDMILL = SOUNDS.register("windmill",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":windmill"))));
	public static final RegistryObject<SoundEvent> SOUND_CERAMICPLATEBREAKING = SOUNDS.register("ceramicplatebreaking",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":ceramicplatebreaking"))));
	public static final RegistryObject<SoundEvent> SOUND_CERAMICPLATEADDED = SOUNDS.register("ceramicplateadded",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":ceramicplateadded"))));
	public static final RegistryObject<SoundEvent> SOUND_RAILGUNKINETIC = SOUNDS.register("railgunkinetic",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":railgunkinetic"))));
	public static final RegistryObject<SoundEvent> SOUND_RAILGUNKINETIC_NOAMMO = SOUNDS.register("railgunkinetic_noammo",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":railgunkinetic_noammo"))));
	public static final RegistryObject<SoundEvent> SOUND_RAILGUNPLASMA = SOUNDS.register("railgunplasma",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":railgunplasma"))));
	public static final RegistryObject<SoundEvent> SOUND_RAILGUNPLASMA_NOPOWER = SOUNDS.register("railgunplasma_nopower",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":railgunplasma_nopower"))));
	public static final RegistryObject<SoundEvent> SOUND_RODIMPACTINGGROUND = SOUNDS.register("rodhittingground",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":rodhittingground"))));
	public static final RegistryObject<SoundEvent> SOUND_LATHEPLAYING = SOUNDS.register("latherunning",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":latherunning"))));
	public static final RegistryObject<SoundEvent> SOUND_EQUIPHEAVYARMOR = SOUNDS.register("equipheavyarmor",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":equipheavyarmor"))));
	public static final RegistryObject<SoundEvent> SOUND_SEISMICSCANNER = SOUNDS.register("seismicscanner",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":seismicscanner"))));
	public static final RegistryObject<SoundEvent> SOUND_ELECTROLYTICSEPARATOR = SOUNDS.register("electrolyticseparator",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":electrolyticseparator"))));
	public static final RegistryObject<SoundEvent> SOUND_NIGHTVISIONGOGGLESON = SOUNDS.register("nightvisiongoggleson",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":nightvisiongoggleson"))));
	public static final RegistryObject<SoundEvent> SOUND_NIGHTVISIONGOGGLESOFF = SOUNDS.register("nightvisiongogglesoff",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":nightvisiongogglesoff"))));
	public static final RegistryObject<SoundEvent> SOUND_HYDRAULICBOOTS = SOUNDS.register("hydraulicboots",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":hydraulicboots"))));
	public static final RegistryObject<SoundEvent> SOUND_JETPACKSWITCHMODE = SOUNDS.register("jetpackswitchmode",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":jetpackswitchmode"))));
	public static final RegistryObject<SoundEvent> SOUND_JETPACK = SOUNDS.register("jetpack",
			supplier(new SoundEvent(new ResourceLocation(References.ID + ":jetpack"))));

	private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
		return () -> entry;
	}
}
