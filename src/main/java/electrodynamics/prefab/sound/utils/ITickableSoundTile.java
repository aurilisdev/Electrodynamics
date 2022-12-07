package electrodynamics.prefab.sound.utils;

import net.minecraft.core.BlockPos;

/**
 * Basic interface that allows tickable sounds to check if they should keep playing or stop
 * 
 * @author skip999
 *
 */
public interface ITickableSoundTile {

	void setNotPlaying();
	
	boolean shouldPlaySound();
	
	BlockPos getBlockPos();
	
	boolean isRemoved();
	
}
