package physica.api.lib.tile;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.PhysicaAPI;

public abstract class TileBasePoweredContainer extends TileBaseContainer implements IEnergyReceiver {
	private int energyStored;

	protected boolean hasEnoughEnergy() {
		return PhysicaAPI.isDebugMode || getEnergyStored() >= getEnergyUsage();
	}

	protected int extractEnergy() {
		energyStored = Math.max(0, energyStored - getEnergyUsage());
		return getEnergyUsage();
	}

	protected void drainBattery(int slot) {
		ItemStack itemStack = getStackInSlot(slot);

		if (itemStack != null)
		{
			if (itemStack.getItem() instanceof IEnergyContainerItem)
			{
				IEnergyContainerItem energized = (IEnergyContainerItem) itemStack.getItem();
				int power = energized.getEnergyStored(itemStack);
				if (power > 0)
				{
					power = energized.extractEnergy(itemStack, power, true);
					energized.extractEnergy(itemStack, receiveEnergy(ForgeDirection.UNKNOWN, power, true), false);
					setInventorySlotContents(slot, itemStack);
				}
			}
		}
	}

	public int getEnergyStored() {
		return energyStored;
	}

	public abstract int getEnergyUsage();

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Energy", energyStored);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energyStored = nbt.getInteger("Energy");
	}

	@Override
	protected void writeGuiPacket(List<Object> dataList, EntityPlayer player) {
		super.writeGuiPacket(dataList, player);
		dataList.add(energyStored);
	}

	@Override
	protected void readGuiPacket(ByteBuf buf, EntityPlayer player) {
		super.readGuiPacket(buf, player);
		energyStored = buf.readInt();
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energyStored;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int capacityLeft = getMaxEnergyStored(from) - getEnergyStored();
		energyStored += simulate ? 0 : capacityLeft >= maxReceive ? maxReceive : capacityLeft;
		return capacityLeft >= maxReceive ? maxReceive : capacityLeft;
	}
}
