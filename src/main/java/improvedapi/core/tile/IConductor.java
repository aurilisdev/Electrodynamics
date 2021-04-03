package improvedapi.core.tile;

public interface IConductor extends INetworkProvider, IConnectionProvider
{
	/**
	 * Gets the resistance of the conductor. Used to calculate energy loss. A higher resistance
	 * means a higher energy loss.
	 * 
	 * @return The amount of resistance in Ohms.
	 */
	public double getResistance();

	/**
	 * @return The maximum amount of amps this conductor can handle before melting down.
	 */
	public double getCurrentCapacity();
}
