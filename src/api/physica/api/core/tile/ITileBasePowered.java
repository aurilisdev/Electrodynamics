package physica.api.core.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import physica.api.core.abstraction.Face;
import physica.api.core.electricity.IElectricityReceiver;

public interface ITileBasePowered extends ITileBase, IElectricityReceiver {

	default boolean hasEnoughEnergy()
	{
		return getElectricityStored() >= getElectricityUsage();
	}

	default int extractEnergy()
	{
		setElectricityStored(Math.max(0, getElectricityStored() - getElectricityUsage()));
		return getElectricityUsage();
	}

	@Override
	default void handleWriteToNBT(NBTTagCompound nbt)
	{
		ITileBase.super.handleWriteToNBT(nbt);
		nbt.setInteger(ELECTRICITY_NBT, getElectricityStored());
	}

	@Override
	default void handleReadFromNBT(NBTTagCompound nbt)
	{
		ITileBase.super.handleReadFromNBT(nbt);
		setElectricityStored(nbt.getInteger(ELECTRICITY_NBT));
	}

	@Override
	default void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		ITileBase.super.writeSynchronizationPacket(dataList, player);
		dataList.add(getElectricityStored());
	}

	@Override
	default void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		ITileBase.super.readSynchronizationPacket(buf, player);
		setElectricityStored(buf.readInt());
	}

	@Override
	default int getElectricityStored(Face from)
	{
		return getElectricityStored();
	}

	@Override
	default int receiveElectricity(Face from, int maxReceive, boolean simulate)
	{
		int capacityLeft = getElectricCapacity(from) - getElectricityStored();
		setElectricityStored(simulate ? getElectricityStored() : capacityLeft >= maxReceive ? getElectricityStored() + maxReceive : getElectricCapacity(from));
		return capacityLeft >= maxReceive ? maxReceive : capacityLeft;
	}

	@Override
	default int getElectricCapacity(Face from)
	{
		return getElectricityUsage() * 20;
	}

	default int getElectricityUsage()
	{
		return 0;
	}

	abstract int getElectricityStored();

}
