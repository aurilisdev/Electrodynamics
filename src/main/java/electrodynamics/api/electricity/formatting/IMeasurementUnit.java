package electrodynamics.api.electricity.formatting;

import net.minecraft.network.chat.MutableComponent;

public interface IMeasurementUnit {

	public double getValue();

	public MutableComponent getSymbol();

	public MutableComponent getName();

}