package universalelectricity.prefab.capabilities;

import net.minecraftforge.energy.EnergyStorage;

public class ElectricityStorage extends EnergyStorage {
	public ElectricityStorage(int capacity) {
		super(capacity, capacity, capacity);
	}

	public ElectricityStorage(int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
	}

	public ElectricityStorage(int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
	}

	public void setEnergyStored(int energy) {
		this.energy = energy;
	}
}