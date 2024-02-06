package electrodynamics.compatibility.jei.utils.label.types;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import net.minecraft.network.chat.Component;

public class BiproductPercentWrapperElectroRecipe extends AbstractLabelWrapper {

	private final boolean isFluid;

	public BiproductPercentWrapperElectroRecipe(int xEndPos, int yPos, boolean isFluid) {
		super(0xFF808080, yPos, xEndPos, false);
		this.isFluid = isFluid;
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		if (isFluid) {
			// for future use
		} else {
			List<AbstractLabelWrapper> labels = Arrays.asList(category.labels);
			int biPos = labels.indexOf(this) - category.itemBiLabelFirstIndex;
			ElectrodynamicsRecipe electro = (ElectrodynamicsRecipe) recipe;
			if (electro.hasItemBiproducts()) {
				ProbableItem item = electro.getItemBiproducts().get(biPos);
				return ChatFormatter.getChatDisplayShort(item.getChance() * 100, DisplayUnit.PERCENTAGE);
			}
		}
		return Component.literal("");
	}

}
