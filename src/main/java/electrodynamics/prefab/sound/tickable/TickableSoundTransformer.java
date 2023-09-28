package electrodynamics.prefab.sound.tickable;

import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class TickableSoundTransformer extends TickableSoundTile<TileGenericTransformer> {

	// private double lastMultiplier = 0;

	public TickableSoundTransformer(SoundEvent event, SoundSource source, TileGenericTransformer tile, float volume, float pitch, boolean repeat) {
		super(event, source, tile, volume, pitch, repeat);
	}

	@Override
	public float getVolume() {
		float volume = super.getVolume();

		if (tile == null) {
			return volume;
		}

		double multiplier = 1;

		if (Constants.TRANSFORMER_SOUND_LOAD_TARGET > 0) {
			multiplier = tile.lastTransfer.get().getWatts() / Constants.TRANSFORMER_SOUND_LOAD_TARGET;
			multiplier = Math.min(multiplier, 1);
			// lastMultiplier = multiplier;
		}

		// Electrodynamics.LOGGER.info(multiplier);
		// Electrodynamics.LOGGER.info(lastMultiplier);

		return (float) (volume * multiplier);
	}

}
