package electrodynamics.compatibility.jei.utils.label.types;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import net.minecraft.util.text.ITextComponent;

public class BiproductPercentWrapperConstant extends AbstractLabelWrapper {

	private final double percentage;

	public BiproductPercentWrapperConstant(int xEndPos, int yPos, double percentage) {
		super(0xFF808080, yPos, xEndPos, false);
		this.percentage = percentage;
	}

	@Override
	public ITextComponent getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		return ChatFormatter.getChatDisplayShort(percentage * 100, DisplayUnit.PERCENTAGE);
	}

}
