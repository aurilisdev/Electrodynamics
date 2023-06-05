package electrodynamics.compatibility.jei.utils.gui.arrows.animated;

import electrodynamics.compatibility.jei.utils.gui.ScreenObjectWrapper;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import net.minecraft.resources.ResourceLocation;

public abstract class ArrowAnimatedWrapper extends ScreenObjectWrapper {

	private ScreenObjectWrapper staticArrow;

	public ArrowAnimatedWrapper(ResourceLocation texture, int xStart, int yStart, int textX, int textY, int width, int height, ScreenObjectWrapper staticArrow) {
		super(texture, xStart, yStart, textX, textY, width, height);
		this.staticArrow = staticArrow;
	}

	public abstract StartDirection getStartDirection();

	public ScreenObjectWrapper getStaticArrow() {
		return staticArrow;
	}

}
