package electrodynamics.compatibility.jei.utils.gui.types;

import electrodynamics.api.screen.ITexture;
import electrodynamics.compatibility.jei.utils.gui.ScreenObject;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;

public class ArrowAnimatedObject extends ScreenObject {

	private ScreenObject offArrow;
	private StartDirection startDirection;

	public ArrowAnimatedObject(ITexture offTexture, ITexture onTexture, int x, int y, StartDirection startDirection) {
		super(onTexture, x, y);
		offArrow = new ScreenObject(offTexture, x, y);
		this.startDirection = startDirection;
	}

	public ArrowAnimatedObject(ProgressBars bar, int x, int y, StartDirection startDirection) {
		this(bar.off, bar.on, x, y, startDirection);
	}

	public ScreenObject getOffArrow() {
		return offArrow;
	}

	public StartDirection startDirection() {
		return startDirection;
	}

}
