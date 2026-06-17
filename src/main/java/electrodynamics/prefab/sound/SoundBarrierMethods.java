package electrodynamics.prefab.sound;

import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer;
import electrodynamics.prefab.sound.tickable.TickableSoundTransformer;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundBarrierMethods {

    public static void playTransformerSound(SoundEvent event, SoundSource source, TileGenericTransformer tile,
	    float volume, float pitch, boolean repeat) {
	Minecraft.getInstance().getSoundManager()
		.play(new TickableSoundTransformer(event, source, tile, volume, pitch, repeat));
    }

}
