package electrodynamics.prefab.utilities.object;

import com.electronwill.nightconfig.core.conversion.InvalidValueException;

import electrodynamics.prefab.properties.Property;

public class TargetValue {

	private double val;

	private double target;
	private double acceleration;

	public double getValue() {
		return val;
	}

	public void setValue(double val) {
		this.val = val;
	}

	public TargetValue flush(double target, double acceleration) {
		if (acceleration < 0) {
			throw new InvalidValueException("Negative acceleration is not supported");
		}
		this.target = target;
		this.acceleration = getValue() < target && acceleration > 1 ? acceleration : 1 / acceleration;
		boolean aimsUp = this.acceleration > 1;
		double valAcc = getValue() * this.acceleration;
		setValue(aimsUp ? valAcc > target ? target : valAcc : valAcc < target ? target : valAcc);
		return this;
	}

	public TargetValue rangeParameterize(double starttarget, double endtarget, double currentTarget, double value, int ticks) {
		setValue(value);
		target = currentTarget;
		acceleration = Math.pow(endtarget / starttarget, 1.0 / ticks);
		return this;
	}

	public TargetValue flush() {
		return flush(target, acceleration);
	}

	public static class PropertyTargetValue extends TargetValue {

		private final Property<Double> valueProperty;

		public PropertyTargetValue(Property<Double> val) {
			valueProperty = val;
		}

		@Override
		public double getValue() {
			return valueProperty.get();
		}

		@Override
		public void setValue(double val) {
			valueProperty.set(val);
		}

	}

}
