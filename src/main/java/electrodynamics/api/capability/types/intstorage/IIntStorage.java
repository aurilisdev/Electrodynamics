package electrodynamics.api.capability.types.intstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.nbt.CompoundTag;

public interface IIntStorage {

	//SERVER
	
	void setServerInt(int index, int number);

	int getServerInt(int index);
	
	void addServerValue(int value);
	
	List<Integer> getServerValues();
	
	void clearServerValues();
	
	void setServerValues(List<Integer> values);
	
	//CLIENT
	
	int getClientInt(int index);
	
	List<Integer> getClientValues();
	
	void clearClientValues();
	
	void setClientValues(List<Integer> values);
	
	//FUNCTIONS
	
	static CompoundTag saveToServerNBT(IIntStorage num) {
		CompoundTag nbt = new CompoundTag();
		int size = num.getServerValues().size();
		nbt.putInt(ElectrodynamicsCapabilities.INT_KEY, size);
		for (int i = 0; i < size; i++) {
			nbt.putInt(ElectrodynamicsCapabilities.INT_KEY + i, num.getServerInt(i));
		}
		return nbt;
	}
	
	static void readFromServerNBT(CompoundTag tag, IIntStorage num) {
		int size = tag.getInt(ElectrodynamicsCapabilities.INT_KEY);
		List<Integer> serverValues = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			serverValues.add(tag.getInt(ElectrodynamicsCapabilities.INT_KEY + i));
		}
		num.setServerValues(serverValues);
	}
	
	static CompoundTag saveToClientNBT(IIntStorage num) {
		CompoundTag nbt = new CompoundTag();
		int size = num.getServerValues().size();
		nbt.putInt(ElectrodynamicsCapabilities.INT_KEY, size);
		for (int i = 0; i < size; i++) {
			nbt.putInt(ElectrodynamicsCapabilities.INT_KEY + i, num.getServerInt(i));
		}
		return nbt;
	}
	
	static void readFromClientNBT(CompoundTag tag, IIntStorage num) {
		int size = tag.getInt(ElectrodynamicsCapabilities.INT_KEY);
		List<Integer> clientValues = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			clientValues.add(tag.getInt(ElectrodynamicsCapabilities.INT_KEY + i));
		}
		num.setClientValues(clientValues);
	}

}
