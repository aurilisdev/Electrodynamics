package physica.api.core.electricity;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IElectricItem extends IEnergyContainerItem {
	public static final String ENERGY_NBT_DATA = "Energy";

	default int extractElectricity(ItemStack container, int maxExtract, boolean simulate)
	{
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey(ENERGY_NBT_DATA))
		{
			return 0;
		}
		int energy = container.stackTagCompound.getInteger(ENERGY_NBT_DATA);
		int energyExtracted = Math.min(energy, Math.min(getExtractRate(container), maxExtract));

		if (!simulate)
		{
			energy -= energyExtracted;
			setEnergyStored(container, energy);
		}
		return energyExtracted;
	}

	default int receiveElectricity(ItemStack container, int maxReceive, boolean simulate)
	{
		return 0;
	}

	default int getElectricityStored(ItemStack container)
	{
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey(ENERGY_NBT_DATA))
		{
			return 0;
		}
		return container.stackTagCompound.getInteger(ENERGY_NBT_DATA);
	}

	default int getElectricCapacity(ItemStack container)
	{
		return 0;
	}

	default void setEnergyStored(ItemStack container, int amount)
	{
		if (container.stackTagCompound == null)
		{
			container.stackTagCompound = new NBTTagCompound();
		}

		int energyStored = (int) Math.max(Math.min(amount, getElectricCapacity(container)), 0.0D);
		container.stackTagCompound.setInteger(ENERGY_NBT_DATA, energyStored);
		container.setItemDamage((int) Math.max(0, Math.abs(energyStored / (float) getElectricCapacity(container) * 100 - 100)));
	}

	default int getExtractRate(ItemStack container)
	{
		return getElectricCapacity(container);
	}

	default int getReceiveRate(ItemStack container)
	{
		return getElectricCapacity(container);
	}

	@Override
	@Deprecated
	default int extractEnergy(ItemStack container, int maxExtract, boolean simulate)
	{
		return extractElectricity(container, maxExtract, simulate);
	}

	@Override
	@Deprecated
	default int receiveEnergy(ItemStack container, int maxReceive, boolean simulate)
	{
		return receiveElectricity(container, maxReceive, simulate);
	}

	@Override
	@Deprecated
	default int getEnergyStored(ItemStack container)
	{
		return getElectricityStored(container);
	}

	@Override
	@Deprecated
	default int getMaxEnergyStored(ItemStack container)
	{
		return getElectricCapacity(container);
	}
}
