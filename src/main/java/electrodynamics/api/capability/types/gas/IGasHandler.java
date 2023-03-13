package electrodynamics.api.capability.types.gas;

import javax.annotation.Nonnull;

import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;

/**
 * An implementation of a Gas Handler capability modeled after IFluidHandler
 * 
 * @author skip999
 *
 */
public interface IGasHandler {
	
	/**
	 * @return The number of gas storage units available in this handler.
	 */
	int getTanks();
	
	/**
	 * @param tank : The tank to check.
	 * @return A GasStack representing the gas stored in that tank.
	 */
	GasStack getGasInTank(int tank);
	
	/**
	 * @param tank : The tank to check.
	 * @return A double representing the maximum storage capacity of the checked tank.
	 */
	double getTankCapacity(int tank);
	
	/**
	 * @param tank : The tank to check.
	 * @return A double representing the maximum temperature (in degrees Kelvin) of the checked tank
	 */
	double getTankMaxTemperature(int tank);
	
	/**
	 * @param tank : The tank to check
	 * @return A double representing the maximum pressure (in ATM) of the checked tank
	 */
	double getTankMaxPressure(int tank);
	
	/**
	 * @param tank : The tank to check
	 * @param gas : The gas being checked
	 * @return Whether or not the checked tank could EVER accept the checked gas
	 */
	boolean isGasValid(int tank, @Nonnull GasStack gas);
	
	/**
	 * @param tank : The tank to fill
	 * @param gas : The gas to fill
	 * @param action : If SIMULATE, the action will only be simulated
	 * @return The amount of gas that was accepted
	 */
	double fillTank(int tank, GasStack gas, GasAction action);
	
	/**
	 * @param tank : The tank to drain
	 * @param gas : The gas to be drained
	 * @param action : If SIMULATE, the action will only be simulated 
	 * @return A GasStack representing how much gas was actually drained
	 */
	GasStack drainTank(int tank, GasStack gas, GasAction action);
	
	/**
	 * @param tank : The tank to drain
	 * @param maxFill : The amount of gas to drain
	 * @param action : If SIMULATE, the action will only be simulated
	 * @return A GasStack representing how much gas was actually drained
	 */
	GasStack drainTank(int tank, double maxFill, GasAction action);
	
	/**
	 * @param tank : The tank to heat
	 * @param deltaTemperature : The amount the temperature should change.
	 * @param action : If SIMULATE, the heating will only be simulated.
	 * @return How much room is left in the tank after the gas is heated. A VALUE OF NEGATIVE ONE INDICATES THERE IS NOT ENOUGH ROOM.
	 */
	double heat(int tank, double deltaTemperature, GasAction action);
	
	/**
	 * @param tank : the tank to pressurize
	 * @param deltaPressure : The amount the pressure should change.
	 * @param action : If SIMULATE, the pressurizing will only be simulated.
	 * @return How much room is left in the tank after the gas is pressurized. A VALUE OF NEGATIVE ONE INDICATES THERE IS NOT ENOUGH ROOM.
	 */
	double pressureize(int tank, double deltaPressure, GasAction action);

}
