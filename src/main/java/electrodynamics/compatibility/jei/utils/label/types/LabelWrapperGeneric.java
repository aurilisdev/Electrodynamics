package electrodynamics.compatibility.jei.utils.label.types;

import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import net.minecraft.util.text.ITextComponent;

public class LabelWrapperGeneric extends AbstractLabelWrapper {

	private final ITextComponent label;

	public LabelWrapperGeneric(int color, int yPos, int endXPos, boolean xIsEnd, ITextComponent label) {
		super(color, yPos, endXPos, xIsEnd);
		this.label = label;
	}

	@Override
	public ITextComponent getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		return label;
	}

}
