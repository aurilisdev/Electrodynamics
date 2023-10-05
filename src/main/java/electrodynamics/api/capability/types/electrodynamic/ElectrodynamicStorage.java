package electrodynamics.api.capability.types.electrodynamic;

import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.Direction;

public class ElectrodynamicStorage implements ICapabilityElectrodynamic {

	private final double max;
	private double joules;

	public ElectrodynamicStorage(double max, double val) {
		this.max = max;
		joules = val;
	}

	@Override
	public void setJoulesStored(double joules) {
		this.joules = joules;
	}

	@Override
	public double getJoulesStored() {
		return joules;
	}

	@Override
	public double getMaxJoulesStored() {
		return max;
	}

	@Override
	public void onChange() {
		// Not Needed
	}

	@Override
	public TransferPack getConnectedLoad(LoadProfile loadProfile, Direction dir) {
		return TransferPack.EMPTY;
	}

	@Override
	public boolean isEnergyReceiver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnergyProducer() {
		// TODO Auto-generated method stub
		return false;
	}

}
