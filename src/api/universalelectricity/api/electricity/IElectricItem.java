package universalelectricity.api.electricity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import universalelectricity.UniversalElectricity;

public interface IElectricItem {

	default int extractElectricity(ItemStack container, int maxExtract, boolean simulate) {
		if (container.getTagCompound() == null
				|| !container.getTagCompound().hasKey(UniversalElectricity.ELECTRICITY_STORED_TAG)) {
			return 0;
		}
		int energy = container.getTagCompound().getInteger(UniversalElectricity.ELECTRICITY_STORED_TAG);
		int energyExtracted = Math.min(energy, Math.min(getExtractRate(container), maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			setElectricityStored(container, energy);
		}
		return energyExtracted;
	}

	default int receiveElectricity(ItemStack container, int maxReceive, boolean simulate) {
		if (container.getTagCompound() == null) {
			container.setTagCompound(new NBTTagCompound());
		}
		int energy = container.getTagCompound().getInteger(UniversalElectricity.ELECTRICITY_STORED_TAG);
		int energyReceived = Math.min(getElectricCapacity(container) - energy,
				Math.min(getReceiveRate(container), maxReceive));

		if (!simulate) {
			energy += energyReceived;
			setElectricityStored(container, energy);
		}
		return energyReceived;
	}

	default int getElectricityStored(ItemStack container) {
		if (container.getTagCompound() == null
				|| !container.getTagCompound().hasKey(UniversalElectricity.ELECTRICITY_STORED_TAG)) {
			return 0;
		}
		return container.getTagCompound().getInteger(UniversalElectricity.ELECTRICITY_STORED_TAG);
	}

	default int getElectricCapacity(ItemStack container) {
		return 0;
	}

	default void setElectricityStored(ItemStack container, int amount) {
		if (container.getTagCompound() == null) {
			container.setTagCompound(new NBTTagCompound());
		}

		int energyStored = (int) Math.max(Math.min(amount, getElectricCapacity(container)), 0.0D);
		container.getTagCompound().setInteger(UniversalElectricity.ELECTRICITY_STORED_TAG, energyStored);
		container.setItemDamage(
				(int) Math.max(0, Math.abs(energyStored / (float) getElectricCapacity(container) * 100 - 100)));
	}

	default int getExtractRate(ItemStack container) {
		return getElectricCapacity(container);
	}

	default int getReceiveRate(ItemStack container) {
		return getElectricCapacity(container);
	}

}
