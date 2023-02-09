package electrodynamics.compatibility.jei.utils.label;

import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Recipe;

public class LabelWrapperGeneric extends AbstractLabelWrapper {

	private final Component label;

	public LabelWrapperGeneric(int color, int yPos, int endXPos, boolean xIsEnd, Component label) {
		super(color, yPos, endXPos, xIsEnd);
		this.label = label;
	}

	@Override
	public Component getComponent(IRecipeCategory<?> category, Recipe<?> recipe) {
		return label;
	}

}
