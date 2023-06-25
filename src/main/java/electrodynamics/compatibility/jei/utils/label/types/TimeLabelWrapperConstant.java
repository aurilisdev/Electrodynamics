package electrodynamics.compatibility.jei.utils.label.types;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import net.minecraft.network.chat.Component;

public class TimeLabelWrapperConstant extends AbstractLabelWrapper {

	private final int ticks;

	public TimeLabelWrapperConstant(int xPos, int yPos, int processingTicks) {
		super(0xFF808080, yPos, xPos, true);
		ticks = processingTicks;
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {

		return Component.literal(ChatFormatter.getChatDisplayShort((double) ticks / 20.0, DisplayUnit.TIME_SECONDS));

	}

}
