package electrodynamics.prefab.utilities.object;

import voltaic.prefab.utilities.object.Location;

public class QuarryArmFrameWrapper {
	
	private final Location frame;
	private final int deltaX;
	private final int deltaZ;
	private final float degrees;
	
	public QuarryArmFrameWrapper(Location frame, int deltaX, int deltaZ, float degrees) {
		this.frame = frame;
		this.deltaX = deltaX;
		this.deltaZ = deltaZ;
		this.degrees = degrees;
	}
	
	public Location frame() {
		return frame;
	}
	
	public int deltaX() {
		return deltaX;
	}
	
	public int deltaZ() {
		return deltaZ;
	}
	
	public float degrees() {
		return degrees;
	}

}
