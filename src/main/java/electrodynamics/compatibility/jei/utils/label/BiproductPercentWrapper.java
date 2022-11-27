package electrodynamics.compatibility.jei.utils.label;

import java.util.Arrays;
import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import net.minecraft.network.chat.Component;

public class BiproductPercentWrapper extends GenericLabelWrapper {

	private boolean isFluid;

	public BiproductPercentWrapper(int xEndPos, int yPos, boolean isFluid) {
		super(0xFF808080, yPos, xEndPos);
		this.isFluid = isFluid;
	}

	@Override
	public Component getComponent(ElectrodynamicsRecipeCategory<?> category, ElectrodynamicsRecipe recipe) {
		if (isFluid) {
			// for future use
		} else if (recipe.hasItemBiproducts()) {
			List<GenericLabelWrapper> labels = Arrays.asList(category.LABELS);
			int biPos = labels.indexOf(this) - category.itemBiLabelFirstIndex;
			ProbableItem item = recipe.getItemBiproducts()[biPos];
			return Component.literal(item.getChance() * 100 + "%");
		}
		return Component.literal("");
	}

}
