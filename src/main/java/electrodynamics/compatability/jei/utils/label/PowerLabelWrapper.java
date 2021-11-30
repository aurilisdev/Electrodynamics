package electrodynamics.compatability.jei.utils.label;

import electrodynamics.compatability.jei.utils.gui.backgroud.BackgroundWrapper;

public class PowerLabelWrapper extends GenericLabelWrapper {

	public PowerLabelWrapper(int yPos, BackgroundWrapper wrap) {
		super(0xFF808080, yPos, wrap.getLength(), POWER);
	}

}
