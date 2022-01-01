package electrodynamics.compatibility.jei.utils.label;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class PowerLabelWrapper extends GenericLabelWrapper {

	private double wattage;
	private int voltage;
	
	public PowerLabelWrapper(int xPos, int yPos, double joulesPerTick, int voltage) {
		super(0xFF808080, yPos, xPos, POWER);
		this.wattage = joulesPerTick * 20 / 1000.0;
		this.voltage = voltage;
	}

	@Override
	public BaseComponent getComponent(ElectrodynamicsRecipeCategory<?> category, ElectrodynamicsRecipe recipe) {
		return new TranslatableComponent(getLocation(), voltage, wattage);
	}
}
