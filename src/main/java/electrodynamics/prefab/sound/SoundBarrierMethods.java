package electrodynamics.prefab.sound;

import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundBarrierMethods {
	
	public static void playTransformerSound(SoundEvent event, SoundCategory source, TileGenericTransformer tile, float volume, float pitch, boolean repeat) {
		Minecraft.getInstance().getSoundManager().play(new TickableSoundTransformer(event, source, tile, volume, pitch, repeat));
	}

}
