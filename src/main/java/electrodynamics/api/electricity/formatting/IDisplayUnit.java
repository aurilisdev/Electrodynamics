package electrodynamics.api.electricity.formatting;

import net.minecraft.network.chat.Component;

public interface IDisplayUnit {

	public Component getSymbol();

	public Component getName();

	public Component getNamePlural();

	public Component getDistanceFromValue();

}
