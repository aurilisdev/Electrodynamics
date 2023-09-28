package electrodynamics.api.electricity;

import net.minecraft.sounds.SoundEvent;

/**
 * Wrapper interface allowing for a material a wire is resting on to have a certain voltage value it can insulate to
 * 
 * This should be implemented on the Block
 * 
 * @author skip999
 *
 */
public interface IInsulator {

	double getMaximumVoltage();

	SoundEvent getBreakingSound();

}
