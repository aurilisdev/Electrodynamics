package electrodynamics.prefab.sound.tickable;

import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import voltaic.prefab.sound.TickableSoundTile;

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

		if (ElectrodynamicsConfig.INSTANCE.TRANSFORMER_SOUND_LOAD_TARGET.get() > 0) {
			multiplier = tile.lastTransfer.getValue().getWatts() / ElectrodynamicsConfig.INSTANCE.TRANSFORMER_SOUND_LOAD_TARGET.get();
			multiplier = Math.min(multiplier, 1);
			// lastMultiplier = multiplier;
		}

		// Electrodynamics.LOGGER.info(multiplier);
		// Electrodynamics.LOGGER.info(lastMultiplier);

		return (float) (volume * multiplier);
	}

}
