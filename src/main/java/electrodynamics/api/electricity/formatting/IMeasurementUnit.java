package electrodynamics.api.electricity.formatting;

import net.minecraft.network.chat.Component;

public interface IMeasurementUnit {

	public double getValue();

	public Component getSymbol();

	public Component getName();

}
