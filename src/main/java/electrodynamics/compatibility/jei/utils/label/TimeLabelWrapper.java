package electrodynamics.compatibility.jei.utils.label;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Recipe;

public class TimeLabelWrapper extends AbstractLabelWrapper {

	private int ticks = -1;

	public TimeLabelWrapper(int xPos, int yPos) {
		super(0xFF808080, yPos, xPos, true);
	}

	public TimeLabelWrapper(int xPos, int yPos, int processingTicks) {
		super(0xFF808080, yPos, xPos, true);
		ticks = processingTicks;
	}

	@Override
	public Component getComponent(IRecipeCategory<?> category, Recipe<?> recipe) {

		if (ticks > -1) {
			return Component.literal(ChatFormatter.getChatDisplayShort((double) ticks / 20.0, DisplayUnit.TIME_SECONDS));
		}
		return Component.literal(ChatFormatter.getChatDisplayShort((double) ((ElectrodynamicsRecipe) recipe).getTicks() / 20.0, DisplayUnit.TIME_SECONDS));
	}

}
