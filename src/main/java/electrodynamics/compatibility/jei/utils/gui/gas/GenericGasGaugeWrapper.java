package electrodynamics.compatibility.jei.utils.gui.gas;

import electrodynamics.compatibility.jei.utils.gui.ScreenObjectWrapper;
import net.minecraft.resources.ResourceLocation;

public abstract class GenericGasGaugeWrapper extends ScreenObjectWrapper {

	private double amount;
	
	public GenericGasGaugeWrapper(ResourceLocation texture, int xStart, int yStart, int textX, int textY, int width, int height, double amount) {
		super(texture, xStart, yStart, textX, textY, width, height);
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
	
	public abstract int getGaugeOffset();
	
	public abstract int getMercuryOffset();
	
}
