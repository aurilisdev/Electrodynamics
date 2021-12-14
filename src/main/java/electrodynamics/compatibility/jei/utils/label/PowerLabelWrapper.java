package electrodynamics.compatibility.jei.utils.label;

import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;

public class PowerLabelWrapper extends GenericLabelWrapper {

    public PowerLabelWrapper(int yPos, BackgroundWrapper wrap) {
	super(0xFF808080, yPos, wrap.getLength(), POWER);
    }

}
