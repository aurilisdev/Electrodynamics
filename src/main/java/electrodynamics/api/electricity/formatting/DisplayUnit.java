package electrodynamics.api.electricity.formatting;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.Component;

public enum DisplayUnit implements IDisplayUnit {
	AMPERE(ElectroTextUtils.gui("displayunit.ampere.name"), ElectroTextUtils.gui("displayunit.ampere.nameplural"), ElectroTextUtils.gui("displayunit.ampere.symbol")),
	//
	AMP_HOUR(ElectroTextUtils.gui("displayunit.amphour.name"), ElectroTextUtils.gui("displayunit.amphour.nameplural"), ElectroTextUtils.gui("displayunit.amphour.symbol")),
	//
	VOLTAGE(ElectroTextUtils.gui("displayunit.voltage.name"), ElectroTextUtils.gui("displayunit.voltage.nameplural"), ElectroTextUtils.gui("displayunit.voltage.symbol")),
	//
	WATT(ElectroTextUtils.gui("displayunit.watt.name"), ElectroTextUtils.gui("displayunit.watt.nameplural"), ElectroTextUtils.gui("displayunit.watt.symbol")),
	//
	WATT_HOUR(ElectroTextUtils.gui("displayunit.watthour.name"), ElectroTextUtils.gui("displayunit.watthour.nameplural"), ElectroTextUtils.gui("displayunit.watthour.symbol")),
	//
	RESISTANCE(ElectroTextUtils.gui("displayunit.resistance.name"), ElectroTextUtils.gui("displayunit.resistance.nameplural"), ElectroTextUtils.gui("displayunit.resistance.symbol")),
	//
	CONDUCTANCE(ElectroTextUtils.gui("displayunit.conductance.name"), ElectroTextUtils.gui("displayunit.conductance.nameplural"), ElectroTextUtils.gui("displayunit.conductance.symbol")),
	//
	JOULES(ElectroTextUtils.gui("displayunit.joules.name"), ElectroTextUtils.gui("displayunit.joules.nameplural"), ElectroTextUtils.gui("displayunit.joules.symbol")),
	//
	BUCKETS(ElectroTextUtils.gui("displayunit.buckets.name"), ElectroTextUtils.gui("displayunit.buckets.nameplural"), ElectroTextUtils.gui("displayunit.buckets.symbol")),
	//
	TEMPERATURE_KELVIN(ElectroTextUtils.gui("displayunit.tempkelvin.name"), ElectroTextUtils.gui("displayunit.tempkelvin.nameplural"), ElectroTextUtils.gui("displayunit.tempkelvin.symbol")),
	//
	TEMPERATURE_CELCIUS(ElectroTextUtils.gui("displayunit.tempcelcius.name"), ElectroTextUtils.gui("displayunit.tempcelcius.nameplural"), ElectroTextUtils.gui("displayunit.tempcelcius.symbol")),
	//
	TEMPERATURE_FAHRENHEIT(ElectroTextUtils.gui("displayunit.tempfahrenheit.name"), ElectroTextUtils.gui("displayunit.tempfahrenheit.nameplural"), ElectroTextUtils.gui("displayunit.tempfahrenheit.symbol")),
	//
	TIME_SECONDS(ElectroTextUtils.gui("displayunit.timeseconds.name"), ElectroTextUtils.gui("displayunit.timeseconds.nameplural"), ElectroTextUtils.gui("displayunit.timeseconds.symbol")),
	//
	TIME_TICKS(ElectroTextUtils.gui("displayunit.timeticks.name"), ElectroTextUtils.gui("displayunit.timeticks.nameplural"), ElectroTextUtils.gui("displayunit.timeticks.symbol")),
	//
	PRESSURE_ATM(ElectroTextUtils.gui("displayunit.pressureatm.name"), ElectroTextUtils.gui("displayunit.pressureatm.nameplural"), ElectroTextUtils.gui("displayunit.pressureatm.symbol")),
	//
	PERCENTAGE(ElectroTextUtils.gui("displayunit.percentage.name"), ElectroTextUtils.gui("displayunit.percentage.nameplural"), ElectroTextUtils.gui("displayunit.percentage.symbol"), Component.empty()),
	//
	FORGE_ENERGY_UNIT(ElectroTextUtils.gui("displayunit.forgeenergyunit.name"), ElectroTextUtils.gui("displayunit.forgeenergyunit.nameplural"), ElectroTextUtils.gui("displayunit.forgeenergyunit.symbol"));

	private final Component symbol;
	private final Component name;
	private final Component namePlural;
	private final Component distanceFromValue;

	private DisplayUnit(Component name, Component namePlural, Component symbol) {
		this(name, namePlural, symbol, Component.literal(" "));
	}

	private DisplayUnit(Component name, Component namePlural, Component symbol, Component distanceFromValue) {
		this.name = name;
		this.namePlural = namePlural;
		this.symbol = symbol;
		this.distanceFromValue = distanceFromValue;
	}

	@Override
	public Component getSymbol() {
		return symbol;
	}

	@Override
	public Component getName() {
		return name;
	}

	@Override
	public Component getNamePlural() {
		return namePlural;
	}

	@Override
	public Component getDistanceFromValue() {
		return distanceFromValue;
	}

}
