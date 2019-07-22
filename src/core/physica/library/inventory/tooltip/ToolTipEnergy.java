package physica.library.inventory.tooltip;

import java.awt.Rectangle;

import net.minecraft.tileentity.TileEntity;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.abstraction.FaceDirection;

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
			if (AbstractionLayer.Electricity.getElectricityStored(receiver, FaceDirection.UNKNOWN) <= 0)
			{
				return "Empty";
			}
			return "Stored: " + AbstractionLayer.Electricity.getElectricityStored(receiver, FaceDirection.UNKNOWN) / AbstractionLayer.Electricity.getElectricCapacity(receiver, FaceDirection.UNKNOWN) * 100 + "%";
		}
		return "Invalid Tile";
	}
}
