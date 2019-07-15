package physica.api.core.misc;

import net.minecraftforge.common.util.ForgeDirection;

public interface IRotatable {

	ForgeDirection getFacing();

	void setFacing(ForgeDirection facing);
}
