package electrodynamics.prefab.sound;

import electrodynamics.prefab.sound.utils.ITickableSound;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SoundBarrierMethods {

	public static <T extends BlockEntity & ITickableSound> void playTileSound(SoundEvent event, T tile, boolean repeat) {
		Minecraft.getInstance().getSoundManager().play(new TickableSoundTile<>(event, tile, repeat));
	}

	public static <T extends BlockEntity & ITickableSound> void playTileSound(SoundEvent event, SoundSource source, T tile, float volume, float pitch, boolean repeat) {
		Minecraft.getInstance().getSoundManager().play(new TickableSoundTile<>(event, source, tile, volume, pitch, repeat));
	}

}
