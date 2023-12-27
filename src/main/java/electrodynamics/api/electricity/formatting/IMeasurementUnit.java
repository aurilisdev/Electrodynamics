package electrodynamics.api.electricity.formatting;

import net.minecraft.util.text.IFormattableTextComponent;

public interface IMeasurementUnit {

	public double getValue();

	public IFormattableTextComponent getSymbol();

	public IFormattableTextComponent getName();

}