package electrodynamics.compatibility.jei.utils.label.types;

import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import net.minecraft.network.chat.Component;

public class TemperatureLabelWrapper extends AbstractLabelWrapper {

	public TemperatureLabelWrapper(int color, int yPos, int endXPos, boolean xIsEnd) {
		super(color, yPos, endXPos, xIsEnd);
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		// TODO Auto-generated method stub
		return null;
	}

}
