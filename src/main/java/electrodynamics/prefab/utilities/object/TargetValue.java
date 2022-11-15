package electrodynamics.prefab.utilities.object;

import com.electronwill.nightconfig.core.conversion.InvalidValueException;

import electrodynamics.prefab.properties.Property;

public class TargetValue {
	private Property<Double> val;
	private double target;
	private double acceleration;

	public TargetValue(Property<Double> val) {
		this.val = val;
	}

	public Property<Double> getValue() {
		return val;
	}

	public TargetValue flush(double target, double acceleration) {
		if (acceleration < 0) {
			throw new InvalidValueException("Negative acceleration is not supported");
		}
		this.target = target;
		this.acceleration = val.get() < target && acceleration > 1 ? acceleration : 1 / acceleration;
		boolean aimsUp = this.acceleration > 1;
		double valAcc = val.get() * this.acceleration;
		val.set(aimsUp ? valAcc > target ? target : valAcc : valAcc < target ? target : valAcc);
		return this;
	}

	public TargetValue rangeParameterize(double starttarget, double endtarget, double currentTarget, double value, int ticks) {
		val.set(value);
		target = currentTarget;
		acceleration = Math.pow(endtarget / starttarget, 1.0 / ticks);
		return this;
	}

	public TargetValue flush() {
		return flush(target, acceleration);
	}

	public void set(double val) {
		this.val.set(val);
	}

}
