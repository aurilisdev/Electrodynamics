package electrodynamics.compatibility.jei.utils.label.types;

import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import net.minecraft.network.chat.Component;

public class LabelWrapperGeneric extends AbstractLabelWrapper {

	private final Component label;

	public LabelWrapperGeneric(int color, int yPos, int endXPos, boolean xIsEnd, Component label) {
		super(color, yPos, endXPos, xIsEnd);
		this.label = label;
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		return label;
	}

}
