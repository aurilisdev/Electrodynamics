package electrodynamics.api.electricity.formatting;

import net.minecraft.util.text.IFormattableTextComponent;

public interface IDisplayUnit {

	public IFormattableTextComponent getSymbol();

	public IFormattableTextComponent getName();

	public IFormattableTextComponent getNamePlural();

	public IFormattableTextComponent getDistanceFromValue();

}