package physica.api.lib;

import java.util.Random;

import physica.api.PhysicaAPI;

public interface IBaseUtilities {
	public static Random randStatic() {
		return PhysicaAPI.random;
	}

	public static float switchRandomStatic(float val) {
		return randStatic().nextBoolean() ? val : val * -1;
	}

	public static double roundPreciseStatic(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}

	public static float roundPreciseStatic(float value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return Math.round(value * scale) / scale;
	}

	default Random rand() {
		return randStatic();
	}

	default float switchRandom(float val) {
		return randStatic().nextBoolean() ? val : val * -1;
	}

	default double roundPrecise(double value, int precision) {
		return roundPreciseStatic(value, precision);
	}

	default float roundPrecise(float value, int precision) {
		return roundPreciseStatic(value, precision);
	}
}
