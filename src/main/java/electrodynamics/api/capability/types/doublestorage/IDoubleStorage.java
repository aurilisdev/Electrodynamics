package electrodynamics.api.capability.types.doublestorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.nbt.CompoundTag;

public interface IDoubleStorage {

	//SERVER
	
	void setServerDouble(int index, double value);

	double getServerDouble(int index);
	
	void addServerValue(double val);
	
	List<Double> getServerValues();
	
	void clearServerValues();
	
	void setServerValues(List<Double> values);
	
	//CLIENT
	
	double getClientValue(int index);
	
	List<Double> getClientValues();
	
	void setClientValues(List<Double> values);
	
	void clearClientValues();
	
	//FUNCTIONS
	
	static CompoundTag saveToServerNBT(IDoubleStorage doub) {
		CompoundTag nbt = new CompoundTag();
		int size = doub.getServerValues().size();
		nbt.putInt(ElectrodynamicsCapabilities.DOUBLE_KEY, size);
		for (int i = 0; i < size; i++) {
			nbt.putDouble(ElectrodynamicsCapabilities.DOUBLE_KEY + i, doub.getServerDouble(i));
		}
		return nbt;
	}
	
	static void readFromServerNBT(CompoundTag tag, IDoubleStorage doub) {
		List<Double> clientValues = new ArrayList<>();
		int size = tag.getInt(ElectrodynamicsCapabilities.DOUBLE_KEY);
		for (int i = 0; i < size; i++) {
			clientValues.add(tag.getDouble(ElectrodynamicsCapabilities.DOUBLE_KEY + i));
		}
		doub.setServerValues(clientValues);
	}
	
	static CompoundTag saveToClientNBT(IDoubleStorage doub) {
		CompoundTag nbt = new CompoundTag();
		int size = doub.getServerValues().size();
		nbt.putInt(ElectrodynamicsCapabilities.DOUBLE_KEY, size);
		for (int i = 0; i < size; i++) {
			nbt.putDouble(ElectrodynamicsCapabilities.DOUBLE_KEY + i, doub.getServerDouble(i));
		}
		return nbt;
	}
	
	static void readFromClientNBT(CompoundTag tag, IDoubleStorage doub) {
		List<Double> clientValues = new ArrayList<>();
		int size = tag.getInt(ElectrodynamicsCapabilities.DOUBLE_KEY);
		for (int i = 0; i < size; i++) {
			clientValues.add(tag.getDouble(ElectrodynamicsCapabilities.DOUBLE_KEY + i));
		}
		doub.setClientValues(clientValues);
	}
}
