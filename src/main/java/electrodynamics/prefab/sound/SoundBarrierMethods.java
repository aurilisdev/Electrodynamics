package electrodynamics.prefab.sound;

import electrodynamics.prefab.sound.utils.ITickableSoundTile;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundBarrierMethods {

	public static void playTileSound(SoundEvent event, ITickableSoundTile tile, boolean repeat) {
		Minecraft.getInstance().getSoundManager().play(new TickableSoundTile(event, tile, repeat));
	}

	public static void playTileSound(SoundEvent event, SoundSource source, ITickableSoundTile tile, float volume, float pitch,
			boolean repeat) {
		Minecraft.getInstance().getSoundManager().play(new TickableSoundTile(event, source, tile, volume, pitch, repeat));
	}
	
}
