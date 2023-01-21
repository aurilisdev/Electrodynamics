package electrodynamics.compatibility.jei.utils.label;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.prefab.utilities.TextUtils;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Recipe;

public class PowerLabelWrapper extends AbstractLabelWrapper {

	private int voltage;
	private double wattage = -1;

	public PowerLabelWrapper(int xPos, int yPos, int voltage) {
		super(0xFF808080, yPos, xPos, false);
		this.voltage = voltage;
	}
	
	public PowerLabelWrapper(int xPos, int yPos, double joulesPerTick, int voltage) {
		super(0xFF808080, yPos, xPos, false);
		this.voltage = voltage;
		this.wattage = joulesPerTick * 20.0 / 1000.0;
	}

	@Override
	public Component getComponent(IRecipeCategory<?> category, Recipe<?> recipe) {
		if(wattage > -1) {
			return TextUtils.jeiTranslated("guilabel.power", voltage, wattage);
		} else {
			return TextUtils.jeiTranslated("guilabel.power", voltage, ((ElectrodynamicsRecipe) recipe).getUsagePerTick() * 20.0 / 1000.0);
		}
	}
}
