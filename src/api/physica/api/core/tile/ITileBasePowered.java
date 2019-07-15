package physica.api.core.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
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
		nbt.setInteger("Energy", getElectricityStored());
	}

	@Override
	default void handleReadFromNBT(NBTTagCompound nbt)
	{
		ITileBase.super.handleReadFromNBT(nbt);
		setElectricityStored(nbt.getInteger("Energy"));
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
	default int getElectricityStored(ForgeDirection from)
	{
		return getElectricityStored();
	}

	@Override
	default int receiveElectricity(ForgeDirection from, int maxReceive, boolean simulate)
	{
		int capacityLeft = getElectricCapacity(from) - getElectricityStored();
		setElectricityStored(simulate ? getElectricityStored() : capacityLeft >= maxReceive ? getElectricityStored() + maxReceive : getElectricCapacity(from));
		return capacityLeft >= maxReceive ? maxReceive : capacityLeft;
	}

	@Override
	default int getElectricCapacity(ForgeDirection from)
	{
		return getElectricityUsage() * 20;
	}

	abstract int getElectricityUsage();

	abstract int getElectricityStored();

}
