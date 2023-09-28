package electrodynamics.compatibility.jei.screenhandlers.cliableingredients;

import mezz.jei.api.runtime.IClickableIngredient;
import net.minecraft.client.renderer.Rect2i;

public abstract class AbstractClickableIngredient<T> implements IClickableIngredient<T> {

	private final Rect2i rect;

	public AbstractClickableIngredient(Rect2i rect) {
		this.rect = rect;
	}

	@Override
	public Rect2i getArea() {
		return rect;
	}

}
