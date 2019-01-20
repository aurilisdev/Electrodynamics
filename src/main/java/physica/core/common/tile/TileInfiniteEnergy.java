package physica.core.common.tile;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import physica.core.common.configuration.ConfigCore;
import physica.library.tile.TileBase;

public class TileInfiniteEnergy extends TileBase implements IEnergyProvider {

	public static final int VISIBLE_STORAGE = Integer.MAX_VALUE - 100;

	@Override
	public void updateServer(int ticks) {
		if (ConfigCore.DISABLE_INFINITE_ENERGY_CUBE) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			return;
		}
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			if (tile != null) {
				if (tile instanceof IEnergyReceiver) {
					IEnergyReceiver reciever = (IEnergyReceiver) tile;
					if (reciever.canConnectEnergy(dir.getOpposite())) {
						reciever.receiveEnergy(dir.getOpposite(), VISIBLE_STORAGE, false);
					}
				}
			}
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return maxExtract;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return VISIBLE_STORAGE;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return VISIBLE_STORAGE;
	}

}
