package defense.api;

import net.minecraft.tileentity.TileEntity;

/**
 * Make your TileEntity implement this to have special reaction to radar scanning.
 *
 * @author Calclavia
 */
public interface IRadarDetectable {

	/**
	 * Whether or not this TileEntity can be detected by radar.
	 *
	 * @param radar - the radar TileEntity
	 * @return if this TileEntity is detectable by radar
	 */
	boolean canDetect(TileEntity radar);

	/**
	 * Gets the name displayed by a radar block's interface for this TileEntity
	 *
	 * @return this TileEntity's radar display name
	 */
	String getRadarDisplayName();
}
