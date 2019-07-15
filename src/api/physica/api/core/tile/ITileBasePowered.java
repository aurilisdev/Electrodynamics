package physica.api.core.tile;

import java.util.List;

import cofh.api.energy.IEnergyReceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public interface ITileBasePowered extends ITileBase, IEnergyReceiver {

	default boolean hasEnoughEnergy()
	{
		return getEnergyStored() >= getEnergyUsage();
	}

	default int extractEnergy()
	{
		setEnergyStored(Math.max(0, getEnergyStored() - getEnergyUsage()));
		return getEnergyUsage();
	}

	@Override
	default void handleWriteToNBT(NBTTagCompound nbt)
	{
		ITileBase.super.handleWriteToNBT(nbt);
		nbt.setInteger("Energy", getEnergyStored());
	}

	@Override
	default void handleReadFromNBT(NBTTagCompound nbt)
	{
		ITileBase.super.handleReadFromNBT(nbt);
		setEnergyStored(nbt.getInteger("Energy"));
	}

	@Override
	default void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		ITileBase.super.writeSynchronizationPacket(dataList, player);
		dataList.add(getEnergyStored());
	}

	@Override
	default void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		ITileBase.super.readSynchronizationPacket(buf, player);
		setEnergyStored(buf.readInt());
	}

	@Override
	default int getEnergyStored(ForgeDirection from)
	{
		return getEnergyStored();
	}

	@Override
	default int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		int capacityLeft = getMaxEnergyStored(from) - getEnergyStored();
		setEnergyStored(simulate ? getEnergyStored() : capacityLeft >= maxReceive ? getEnergyStored() + maxReceive : getMaxEnergyStored(from));
		return capacityLeft >= maxReceive ? maxReceive : capacityLeft;
	}

	@Override
	default int getMaxEnergyStored(ForgeDirection from)
	{
		return getEnergyUsage() * 20;
	}

	abstract int getEnergyUsage();

	abstract int getEnergyStored();

	abstract void setEnergyStored(int energy);

}
