package electrodynamics.api.electricity.formatting;

import net.minecraft.network.chat.MutableComponent;

public interface IDisplayUnit {

	public MutableComponent getSymbol();

	public MutableComponent getName();

	public MutableComponent getNamePlural();

	public MutableComponent getDistanceFromValue();

}