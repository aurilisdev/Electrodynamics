package physica.library.energy;

import physica.library.energy.base.Unit;

public class ElectricityUtilities {

	public static double convertEnergy(double amount, Unit from, Unit to)
	{
		return amount / from.ratio * to.ratio;
	}

	public static int convertEnergy(int amount, Unit from, Unit to)
	{
		return (int) (amount / from.ratio * to.ratio);
	}
}
