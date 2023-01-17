package electrodynamics.compatibility.jei.utils.label;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.prefab.utilities.TextUtils;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Recipe;

public class TimeLabelWrapper extends GenericLabelWrapper {

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
		
		if(ticks > -1) {
			return Component.literal(TextUtils.formatTicksToTimeValue(ticks));
		} else {
			return Component.literal(TextUtils.formatTicksToTimeValue(((ElectrodynamicsRecipe) recipe).getTicks()));
		}
	}

}
