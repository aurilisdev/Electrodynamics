package electrodynamics.compatibility.jei.utils.label.types;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.util.text.ITextComponent;

public class PowerLabelWrapperElectroRecipe extends AbstractLabelWrapper {

	private final int voltage;

	public PowerLabelWrapperElectroRecipe(int xPos, int yPos, int voltage) {
		super(0xFF808080, yPos, xPos, false);
		this.voltage = voltage;
	}

	@Override
	public ITextComponent getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		return ElectroTextUtils.jeiTranslated("guilabel.power", voltage, ((ElectrodynamicsRecipe) recipe).getUsagePerTick() * 20.0 / 1000.0);
	}
}
