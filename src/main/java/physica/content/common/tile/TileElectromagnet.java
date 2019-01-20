package physica.content.common.tile;

import net.minecraft.tileentity.TileEntity;
import physica.api.electromagnet.IElectromagnet;

public class TileElectromagnet extends TileEntity implements IElectromagnet {
	@Override
	public boolean isRunning() {
		return true;
	}
}