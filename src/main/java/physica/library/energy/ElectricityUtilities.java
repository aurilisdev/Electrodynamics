package physica.library.energy;

import physica.library.energy.base.Unit;

public class ElectricityUtilities {

	public static double convertEnergy(double amount, Unit from, Unit to)
	{
		if (from == Unit.RF) {
			return to.ratio * amount;
		} else {
			return amount / from.ratio * to.ratio;
		}
	}
}
