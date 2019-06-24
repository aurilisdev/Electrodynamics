package physica.core.common.tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.ITileBasePowered;
import physica.library.tile.TileBase;

public class TileCopperCable extends TileBase implements ITileBasePowered, IEnergyProvider {

	public static final int MAX_ENERGY_STORED = 10240;
	private int energyStored;
	private ForgeDirection lastReceive = ForgeDirection.UNKNOWN;

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public int getEnergyStored()
	{
		return energyStored;
	}

	@Override
	public void setEnergyStored(int energy)
	{
		energyStored = Math.min(energy, MAX_ENERGY_STORED);
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (energyStored > 0) {
			boolean found = false;
			HashMap<IEnergyReceiver, ForgeDirection> receivers = new HashMap<>();
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if (dir != lastReceive) {
					TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
					if (tile instanceof IEnergyReceiver) {
						IEnergyReceiver receiver = (IEnergyReceiver) tile;
						if (receiver.canConnectEnergy(dir.getOpposite()) && receiver.getEnergyStored(dir.getOpposite()) < receiver.getMaxEnergyStored(dir.getOpposite())) {
							receivers.put((IEnergyReceiver) tile, dir);
						}
					}
				}
			}
			for (Entry<IEnergyReceiver, ForgeDirection> entry : receivers.entrySet()) {
				energyStored -= entry.getKey().receiveEnergy(entry.getValue().getOpposite(), energyStored / receivers.size(), false);
				found = true;
			}

			if (!found) {
				TileEntity tile = worldObj.getTileEntity(xCoord + lastReceive.offsetX, yCoord + lastReceive.offsetY, zCoord + lastReceive.offsetZ);
				if (tile instanceof IEnergyReceiver) {
					IEnergyReceiver receiver = (IEnergyReceiver) tile;
					if (receiver.canConnectEnergy(lastReceive.getOpposite())) {
						energyStored -= receiver.receiveEnergy(lastReceive.getOpposite(), energyStored, false);
					}
				}
			}
			if (energyStored > MAX_ENERGY_STORED) {
				for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
					TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
					if (tile instanceof IEnergyReceiver) {
						IEnergyReceiver receiver = (IEnergyReceiver) tile;
						if (receiver.canConnectEnergy(dir.getOpposite()) && receiver.getEnergyStored(dir.getOpposite()) < receiver.getMaxEnergyStored(dir.getOpposite())) {
							worldObj.setBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, Blocks.fire);
						}
					}
				}
				worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.fire);
			}
		}

	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		lastReceive = from;
		int count = 0;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (dir != lastReceive) {
				TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				if (tile instanceof IEnergyReceiver) {
					IEnergyReceiver receiver = (IEnergyReceiver) tile;
					if (receiver.canConnectEnergy(dir.getOpposite()) && receiver.getEnergyStored(dir.getOpposite()) < receiver.getMaxEnergyStored(dir.getOpposite())) {
						count++;
					}
				}
			}
		}
		maxReceive = count > 0 ? maxReceive : 0;
		int capacityLeft = getMaxEnergyStored(from) - getEnergyStored();
		int setCount = simulate ? getEnergyStored() : capacityLeft >= maxReceive ? getEnergyStored() + maxReceive : getMaxEnergyStored(from);

		setEnergyStored(setCount);
		if (!simulate && maxReceive > 0) {
			double lim = Math.pow(1.0005, maxReceive);
			if (lim > setCount) {
				energyStored = Integer.MAX_VALUE / 2;
			}
		}
		return capacityLeft >= maxReceive ? maxReceive : capacityLeft;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return MAX_ENERGY_STORED;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(lastReceive.ordinal());
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readSynchronizationPacket(buf, player);
		lastReceive = ForgeDirection.getOrientation(buf.readInt());
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		int remove = Math.max(0, Math.min(maxExtract, energyStored));
		if (!simulate) {
			energyStored -= remove;
		}
		return remove;
	}

	@Override
	public int getEnergyUsage()
	{
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return ITileBasePowered.super.getEnergyStored(from);
	}
}
