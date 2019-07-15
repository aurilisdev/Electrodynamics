package physica.library.inventory.tooltip;

import java.awt.Rectangle;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.electricity.ElectricityHandler;

public class ToolTipEnergy extends ToolTip {

	public TileEntity receiver;

	public ToolTipEnergy(Rectangle area, TileEntity receiver) {
		super(area, "");
		this.receiver = receiver;
	}

	@Override
	public String getLocalizedTooltip()
	{
		if (ElectricityHandler.isElectricReceiver(receiver))
		{
			if (ElectricityHandler.getElectricityStored(receiver, ForgeDirection.UNKNOWN) <= 0)
			{
				return "Empty";
			}
			return "Stored: " + (ElectricityHandler.getElectricityStored(receiver, ForgeDirection.UNKNOWN)) / (ElectricityHandler.getElectricCapacity(receiver, ForgeDirection.UNKNOWN)) * 100 + "%";
		}
		return "Invalid Tile";
	}
}
