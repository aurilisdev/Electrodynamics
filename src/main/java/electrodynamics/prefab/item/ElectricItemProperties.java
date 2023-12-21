package electrodynamics.prefab.item;

import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.world.item.Item.Properties;

public class ElectricItemProperties extends Properties {
	public double temperature;
	public double capacity;
	// these now function as default values for items that can have their battery storage swapped
	public TransferPack receive = TransferPack.EMPTY;
	public TransferPack extract = TransferPack.EMPTY;

	public boolean isEnergyStorageOnly = false;

	public boolean cannotHaveBatterySwapped = false;

	public ElectricItemProperties capacity(double capacity) {
		this.capacity = (int) capacity;
		return this;
	}

	public ElectricItemProperties temperature(double temperature) {
		this.temperature = temperature;
		return this;
	}

	public ElectricItemProperties receive(TransferPack receive) {
		this.receive = receive;
		return this;
	}

	public ElectricItemProperties extract(TransferPack extract) {
		this.extract = extract;
		return this;
	}

	public ElectricItemProperties setIsEnergyStorageOnly() {
		isEnergyStorageOnly = true;
		return this;
	}

	public ElectricItemProperties setCannotHaveBatterySwapped() {
		cannotHaveBatterySwapped = true;
		return this;
	}

}