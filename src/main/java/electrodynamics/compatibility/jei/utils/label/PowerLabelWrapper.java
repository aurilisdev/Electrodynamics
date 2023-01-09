package electrodynamics.compatibility.jei.utils.label;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.network.chat.Component;

public class PowerLabelWrapper extends GenericLabelWrapper {

	private int voltage;
	private double wattage = -1;

	public PowerLabelWrapper(int xPos, int yPos, int voltage) {
		super(0xFF808080, yPos, xPos);
		this.voltage = voltage;
	}
	
	public PowerLabelWrapper(int xPos, int yPos, double joulesPerTick, int voltage) {
		super(0xFF808080, yPos, xPos);
		this.voltage = voltage;
	}

	@Override
	public Component getComponent(ElectrodynamicsRecipeCategory<?> category, ElectrodynamicsRecipe recipe) {
		double wattage = this.wattage == -1 ? recipe.getUsagePerTick() * 20.0 / 1000.0 : this.wattage;
		return TextUtils.jeiTranslated("guilabel.power", voltage, wattage);
	}
}
