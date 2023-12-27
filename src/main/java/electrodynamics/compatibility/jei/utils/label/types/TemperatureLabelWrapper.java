package electrodynamics.compatibility.jei.utils.label.types;

import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import net.minecraft.util.text.ITextComponent;

public class TemperatureLabelWrapper extends AbstractLabelWrapper {

	public TemperatureLabelWrapper(int color, int yPos, int endXPos, boolean xIsEnd) {
		super(color, yPos, endXPos, xIsEnd);
	}

	@Override
	public ITextComponent getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		// TODO Auto-generated method stub
		return null;
	}

}
