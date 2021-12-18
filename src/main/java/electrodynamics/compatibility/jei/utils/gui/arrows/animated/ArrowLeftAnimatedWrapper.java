package electrodynamics.compatibility.jei.utils.gui.arrows.animated;

import electrodynamics.compatibility.jei.utils.gui.arrows.stat.ArrowLeftStaticWrapper;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;

public class ArrowLeftAnimatedWrapper extends ArrowAnimatedWrapper {

	public ArrowLeftAnimatedWrapper(int xStart, int yStart) {
		super(ARROWS, xStart, yStart, 22, 15, 22, 15, new ArrowLeftStaticWrapper(xStart, yStart));
	}

	@Override
	public StartDirection getStartDirection() {
		return StartDirection.RIGHT;
	}

}
