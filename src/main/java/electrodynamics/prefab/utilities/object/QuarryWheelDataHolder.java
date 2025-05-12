package electrodynamics.prefab.utilities.object;

import voltaic.prefab.utilities.math.PrecisionVector;

public class QuarryWheelDataHolder {
	
	private final PrecisionVector vector;
	private final int yAxisRotation;
	private final float xAxisRotation;
	private final float zAxisRotation;
	
	public QuarryWheelDataHolder(PrecisionVector vector, int yAxisRotation, float xAxisRotation, float zAxisRotation) {
		this.vector = vector;
		this.yAxisRotation = yAxisRotation;
		this.xAxisRotation = xAxisRotation;
		this.zAxisRotation = zAxisRotation;
	}
	
	public PrecisionVector vector() {
		return vector;
	}
	
	public int yAxisRotation() {
		return yAxisRotation;
	}
	
	public float xAxisRotation() {
		return xAxisRotation;
	}
	
	public float zAxisRotation() {
		return zAxisRotation;
	}

}
