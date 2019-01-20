package physica.api.base;

import net.minecraftforge.common.util.ForgeDirection;

public interface IRotatable {
	ForgeDirection getFacing();

	void setFacing(ForgeDirection facing);
}