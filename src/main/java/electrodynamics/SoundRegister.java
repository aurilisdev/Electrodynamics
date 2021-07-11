package electrodynamics;

import electrodynamics.api.References;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegister {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.ID);
    public static final RegistryObject<SoundEvent> SOUND_HUM = SOUNDS.register("hum",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":hum")));
    public static final RegistryObject<SoundEvent> SOUND_MINERALCRUSHER = SOUNDS.register("mineralcrusher",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":mineralcrusher")));
    public static final RegistryObject<SoundEvent> SOUND_MINERALGRINDER = SOUNDS.register("mineralgrinder",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":mineralgrinder")));
    public static final RegistryObject<SoundEvent> SOUND_ELECTRICPUMP = SOUNDS.register("electricpump",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":electricpump")));
    public static final RegistryObject<SoundEvent> SOUND_COMBUSTIONCHAMBER = SOUNDS.register("combustionchamber",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":combustionchamber")));
    public static final RegistryObject<SoundEvent> SOUND_HYDROELECTRICGENERATOR = SOUNDS.register("hydroelectricgenerator",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":hydroelectricgenerator")));
    public static final RegistryObject<SoundEvent> SOUND_WINDMILL = SOUNDS.register("windmill",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":windmill")));

    public static final RegistryObject<SoundEvent> SOUND_CERAMICPLATEBREAKING = SOUNDS.register("ceramicplatebreaking",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":ceramicplatebreaking")));

    public static final RegistryObject<SoundEvent> SOUND_CERAMICPLATEADDED = SOUNDS.register("ceramicplateadded",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":ceramicplateadded")));

}
