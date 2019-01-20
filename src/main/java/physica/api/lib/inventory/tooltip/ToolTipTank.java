package physica.api.lib.inventory.tooltip;

import java.awt.Rectangle;

import net.minecraftforge.fluids.IFluidTank;

public class ToolTipTank extends ToolTip {
	public IFluidTank tank;

	public ToolTipTank(Rectangle area, String info, IFluidTank tank) {
		super(area, info);
		this.tank = tank;
	}

	@Override
	public String getLocalizedTooltip() {
		if (tank.getFluid() != null) return tank.getFluid().getLocalizedName() + ": " + tank.getFluidAmount() + "/" + tank.getCapacity() + "ml";
		else return localize(info + ".empty");
	}
}
