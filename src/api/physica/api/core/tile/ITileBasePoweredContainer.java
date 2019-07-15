package physica.api.core.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.electricity.ElectricityHandler;

public interface ITileBasePoweredContainer extends ITileBasePowered, ITileBaseContainer {

	default void drainBattery(int slot)
	{
		if (receiveElectricity(ForgeDirection.UNKNOWN, 1, true) > 0)
		{
			ItemStack itemStack = getStackInSlot(slot);

			if (ElectricityHandler.isItemElectric(itemStack))
			{
				int power = ElectricityHandler.getElectricityStored(itemStack);
				if (power > 0)
				{
					power = ElectricityHandler.extractElectricity(itemStack, power, true);
					ElectricityHandler.extractElectricity(itemStack, receiveElectricity(ForgeDirection.UNKNOWN, power, false), false);
					setInventorySlotContents(slot, itemStack);
				}
			}
		}
	}

	@Override
	default void handleWriteToNBT(NBTTagCompound nbt)
	{
		ITileBaseContainer.super.handleWriteToNBT(nbt);
		ITileBasePowered.super.handleWriteToNBT(nbt);
	}

	@Override
	default void handleReadFromNBT(NBTTagCompound nbt)
	{
		ITileBaseContainer.super.handleReadFromNBT(nbt);
		ITileBasePowered.super.handleReadFromNBT(nbt);
	}

	@Override
	default void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		ITileBasePowered.super.writeSynchronizationPacket(dataList, player);
		ITileBaseContainer.super.writeSynchronizationPacket(dataList, player);
		writeClientGuiPacket(dataList, player);
	}

	@Override
	default void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		ITileBasePowered.super.readSynchronizationPacket(buf, player);
		ITileBaseContainer.super.readSynchronizationPacket(buf, player);
		readClientGuiPacket(buf, player);
	}

	@Override
	default void writeClientGuiPacket(List<Object> dataList, EntityPlayer player)
	{
		ITileBaseContainer.super.writeClientGuiPacket(dataList, player);
		dataList.add(getElectricityStored());
	}

	@Override
	default void readClientGuiPacket(ByteBuf buf, EntityPlayer player)
	{
		ITileBaseContainer.super.readClientGuiPacket(buf, player);
		setElectricityStored(buf.readInt());
	}
}
