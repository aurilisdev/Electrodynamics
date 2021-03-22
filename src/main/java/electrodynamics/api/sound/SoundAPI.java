package electrodynamics.api.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SoundAPI {
    public static void playSound(SoundEvent soundEvent, SoundCategory category, float volumeIn, float pitchIn, BlockPos pos) {
	Minecraft.getInstance().getSoundHandler()
		.play(new DistanceSound(soundEvent, category, volumeIn, pitchIn, pos));
    }
}
