package electrodynamics.compatibility.jei.utils.label;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class PowerLabelWrapper extends GenericLabelWrapper {

	public PowerLabelWrapper(int xPos, int yPos) {
		super(0xFF808080, yPos, xPos, POWER);
	}

	@Override
	public BaseComponent getComponent(ElectrodynamicsRecipeCategory<?> category, ElectrodynamicsRecipe recipe) {
		return new TranslatableComponent("gui.jei.category." + category.getRecipeGroup() + ".info." + getName(), category.getAnimationTime() / 20);
	}
}
