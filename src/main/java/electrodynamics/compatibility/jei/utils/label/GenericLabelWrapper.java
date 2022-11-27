package electrodynamics.compatibility.jei.utils.label;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import net.minecraft.network.chat.Component;

public abstract class GenericLabelWrapper {

	private int COLOR;
	private int Y_POS;
	private int X_POS;

	public GenericLabelWrapper(int color, int yPos, int endXPos) {
		COLOR = color;
		Y_POS = yPos;
		X_POS = endXPos;
	}

	public int getColor() {
		return COLOR;
	}

	public int getYPos() {
		return Y_POS;
	}

	public int getXPos() {
		return X_POS;
	}

	public abstract Component getComponent(ElectrodynamicsRecipeCategory<?> category, ElectrodynamicsRecipe recipe);
}
