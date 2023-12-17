package electrodynamics.prefab.sound;

import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer;
import electrodynamics.prefab.sound.utils.ITickableSound;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundBarrierMethods {

	public static <T extends TileEntity & ITickableSound> void playTileSound(SoundEvent event, T tile, boolean repeat) {
		Minecraft.getInstance().getSoundManager().play(new TickableSoundTile<>(event, tile, repeat));
	}

	public static <T extends TileEntity & ITickableSound> void playTileSound(SoundEvent event, SoundCategory source, T tile, float volume, float pitch, boolean repeat) {
		Minecraft.getInstance().getSoundManager().play(new TickableSoundTile<>(event, source, tile, volume, pitch, repeat));
	}
	
	public static void playTransformerSound(SoundEvent event, SoundCategory source, TileGenericTransformer tile, float volume, float pitch, boolean repeat) {
		Minecraft.getInstance().getSoundManager().play(new TickableSoundTransformer(event, source, tile, volume, pitch, repeat));
	}

}
