package electrodynamics.compatability.jei.utils.gui.arrows.animated;

import electrodynamics.compatability.jei.utils.gui.arrows.stat.FeynmanDiagramStaticWrapper;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;

public class FeynmanDiagramAnimatedWrapper extends ArrowAnimatedWrapper {
	
	public FeynmanDiagramAnimatedWrapper(int xStart, int yStart) {
		super(ARROWS, xStart + 7, yStart + 5, 7, 123, 47, 42, new FeynmanDiagramStaticWrapper(xStart, yStart));
	}

	@Override
	public StartDirection getStartDirection() {
		return StartDirection.TOP;
	}

}
