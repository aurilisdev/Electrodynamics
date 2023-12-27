package electrodynamics.api.electricity.formatting;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.util.text.IFormattableTextComponent;

public enum MeasurementUnit implements IMeasurementUnit {

	PICO(ElectroTextUtils.gui("measurementunit.pico.name"), ElectroTextUtils.gui("measurementunit.pico.symbol"), 1.0E-12D),
	NANO(ElectroTextUtils.gui("measurementunit.nano.name"), ElectroTextUtils.gui("measurementunit.nano.symbol"), 1.0E-9D),
	MICRO(ElectroTextUtils.gui("measurementunit.micro.name"), ElectroTextUtils.gui("measurementunit.micro.symbol"), 1.0E-6D),
	MILLI(ElectroTextUtils.gui("measurementunit.milli.name"), ElectroTextUtils.gui("measurementunit.milli.symbol"), 1.0E-3D),
	NONE(ElectroTextUtils.gui("measurementunit.none.name"), ElectroTextUtils.gui("measurementunit.none.symbol"), 1.0),
	KILO(ElectroTextUtils.gui("measurementunit.kilo.name"), ElectroTextUtils.gui("measurementunit.kilo.symbol"), 1.0E3D),
	MEGA(ElectroTextUtils.gui("measurementunit.mega.name"), ElectroTextUtils.gui("measurementunit.mega.symbol"), 1.0E6D),
	GIGA(ElectroTextUtils.gui("measurementunit.giga.name"), ElectroTextUtils.gui("measurementunit.giga.symbol"), 1.0E9D);

	private final double value;

	private final IFormattableTextComponent symbol;
	private final IFormattableTextComponent name;

	private MeasurementUnit(IFormattableTextComponent name, IFormattableTextComponent symbol, double value) {
		this.name = name;
		this.symbol = symbol;
		this.value = value;
	}

	public IFormattableTextComponent getName(boolean isSymbol) {
		if (isSymbol) {
			return symbol;
		}

		return name;
	}

	public double process(double value) {
		return value / this.value;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public IFormattableTextComponent getSymbol() {
		return symbol;
	}

	@Override
	public IFormattableTextComponent getName() {
		return name;
	}
}