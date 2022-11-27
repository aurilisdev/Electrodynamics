package electrodynamics.compatibility.jei.utils.label;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.network.chat.Component;

public class PowerLabelWrapper extends GenericLabelWrapper {

	private double wattage;
	private int voltage;

	public PowerLabelWrapper(int xPos, int yPos, double joulesPerTick, int voltage) {
		super(0xFF808080, yPos, xPos);
		wattage = joulesPerTick * 20 / 1000.0;
		this.voltage = voltage;
	}

	@Override
	public Component getComponent(ElectrodynamicsRecipeCategory<?> category, ElectrodynamicsRecipe recipe) {
		return TextUtils.jeiTranslated("guilabel.power", voltage, wattage);
	}
}
