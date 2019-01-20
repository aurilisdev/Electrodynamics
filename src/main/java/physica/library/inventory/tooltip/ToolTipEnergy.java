package physica.library.inventory.tooltip;

import java.awt.Rectangle;

import cofh.api.energy.IEnergyReceiver;
import net.minecraftforge.common.util.ForgeDirection;

public class ToolTipEnergy extends ToolTip {

	public IEnergyReceiver reciever;

	public ToolTipEnergy(Rectangle area, IEnergyReceiver reciever) {
		super(area, "");
		this.reciever = reciever;
	}

	@Override
	public String getLocalizedTooltip() {
		if (reciever.getEnergyStored(ForgeDirection.UNKNOWN) <= 0) {
			return "Empty";
		}
		return "Stored: " + reciever.getEnergyStored(ForgeDirection.UNKNOWN) / reciever.getMaxEnergyStored(ForgeDirection.UNKNOWN) * 100 + "%";
	}
}
