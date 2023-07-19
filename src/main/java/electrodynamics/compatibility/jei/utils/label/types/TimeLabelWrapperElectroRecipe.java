package electrodynamics.compatibility.jei.utils.label.types;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import net.minecraft.network.chat.Component;

public class TimeLabelWrapperElectroRecipe extends AbstractLabelWrapper {

	public TimeLabelWrapperElectroRecipe(int xPos, int yPos) {
		super(0xFF808080, yPos, xPos, true);
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		return ChatFormatter.getChatDisplayShort((double) ((ElectrodynamicsRecipe) recipe).getTicks() / 20.0, DisplayUnit.TIME_SECONDS);
	}

}
