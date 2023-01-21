package electrodynamics.compatibility.jei.utils.label;

import java.util.Arrays;
import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Recipe;

public class BiproductPercentWrapper extends AbstractLabelWrapper {

	private boolean isFluid;
	private double percentage = -1;

	public BiproductPercentWrapper(int xEndPos, int yPos, boolean isFluid) {
		super(0xFF808080, yPos, xEndPos, false);
		this.isFluid = isFluid;
	}
	
	public BiproductPercentWrapper(int xEndPos, int yPos, double percentage, boolean isFluid) {
		super(0xFF808080, yPos, xEndPos, false);
		this.isFluid = isFluid;
		this.percentage = percentage;
	}

	@Override
	public Component getComponent(IRecipeCategory<?> category, Recipe<?> recipe) {
		if (isFluid) {
			// for future use
		} else {
			if(percentage > -1) {
				Component.literal(percentage * 100 + "%");
			} else {
				ElectrodynamicsRecipeCategory<?> electroCat = (ElectrodynamicsRecipeCategory<?>) category;
				List<AbstractLabelWrapper> labels = Arrays.asList(electroCat.LABELS);
				int biPos = labels.indexOf(this) - electroCat.itemBiLabelFirstIndex;
				ElectrodynamicsRecipe electro = (ElectrodynamicsRecipe) recipe;
				if(electro.hasItemBiproducts()) {
					ProbableItem item = electro.getItemBiproducts()[biPos];
					return Component.literal(item.getChance() * 100 + "%");
				}
			}
			
		}
		return Component.literal("");
	}

}
