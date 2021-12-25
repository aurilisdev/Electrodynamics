package electrodynamics.api.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundAPI {
	public static void playSound(SoundEvent soundEvent, SoundSource category, float volumeIn, float pitchIn, BlockPos pos) {
		Minecraft.getInstance().getSoundManager().play(new DistanceSound(soundEvent, category, volumeIn, pitchIn, pos));
	}

	public static void playSoundDelayed(SoundEvent soundEvent, SoundSource category, float volumeIn, float pitchIn, BlockPos pos, int i) {
		Minecraft.getInstance().getSoundManager().playDelayed(new DistanceSound(soundEvent, category, volumeIn, pitchIn, pos), i);
	}
}
