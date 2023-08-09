package electrodynamics.api.electricity;

/**
 * Wrapper interface allowing for a material a wire is resting on to have a certain voltage value it can insulate to
 * @author skip999
 *
 */
public interface IPorcelainInsulator {
	
	double getMaximumVoltage();

}
