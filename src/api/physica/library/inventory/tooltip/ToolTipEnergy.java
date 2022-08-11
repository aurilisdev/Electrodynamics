package physica.library.inventory.tooltip;

import java.awt.Rectangle;

import net.minecraft.tileentity.TileEntity;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.abstraction.Face;

public class ToolTipEnergy extends ToolTip {

	public TileEntity receiver;

	public ToolTipEnergy(Rectangle area, TileEntity receiver) {
		super(area, "");
		this.receiver = receiver;
	}

	@Override
	public String getLocalizedTooltip()
	{
		if (AbstractionLayer.Electricity.isElectricReceiver(receiver))
		{
			if (AbstractionLayer.Electricity.getElectricityStored(receiver, Face.UNKNOWN) <= 0)
			{
				return "Empty";
			}
			return "Stored: " + AbstractionLayer.Electricity.getElectricityStored(receiver, Face.UNKNOWN) / AbstractionLayer.Electricity.getElectricCapacity(receiver, Face.UNKNOWN) * 100 + "%";
		}
		return "Invalid Tile";
	}
}
