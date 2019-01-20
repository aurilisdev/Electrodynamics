package physica.api.core;

import net.minecraftforge.common.util.ForgeDirection;

public interface IRotatable {

	ForgeDirection getFacing();

	void setFacing(ForgeDirection facing);
}
