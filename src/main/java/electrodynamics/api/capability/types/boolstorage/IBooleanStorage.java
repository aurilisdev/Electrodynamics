package electrodynamics.api.capability.types.boolstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.nbt.CompoundTag;

public interface IBooleanStorage {

	//SERVER
	
	void setServerBoolean(int index, boolean bool);

	void addServerBoolean(boolean val);
	
	boolean getServerBoolean(int index);
	
	List<Boolean> getServerValues();
	
	void setServerValues(List<Boolean> values);
	
	void clearServerValues();
	
	
	//CLIENT
	
	boolean getClientBoolean(int index);
	
	List<Boolean> getClientValues();
	
	void setClientValues(List<Boolean> values);
	
	void clearClientValues();

	
	//FUNCTIONAL
	
	static CompoundTag saveToServerNBT(IBooleanStorage bool) {
		CompoundTag nbt = new CompoundTag();
		int size = bool.getServerValues().size();
		nbt.putInt(ElectrodynamicsCapabilities.BOOLEAN_KEY, size);
		for (int i = 0; i < size; i++) {
			nbt.putBoolean(ElectrodynamicsCapabilities.BOOLEAN_KEY + i, bool.getServerBoolean(i));
		}
		return nbt;
	}
	
	static void readFromServerNBT(CompoundTag tag, IBooleanStorage bool){
		List<Boolean> serverValues = new ArrayList<>();
		int size = tag.getInt(ElectrodynamicsCapabilities.BOOLEAN_KEY);
		for (int i = 0; i < size; i++) {
			serverValues.add(tag.getBoolean(ElectrodynamicsCapabilities.BOOLEAN_KEY + i));
		}
		bool.setServerValues(serverValues);
	}
	
	static CompoundTag saveToClientNBT(IBooleanStorage bool) {
		CompoundTag nbt = new CompoundTag();
		int size = bool.getServerValues().size();
		nbt.putInt(ElectrodynamicsCapabilities.BOOLEAN_KEY, size);
		for (int i = 0; i < size; i++) {
			nbt.putBoolean(ElectrodynamicsCapabilities.BOOLEAN_KEY + i, bool.getServerBoolean(i));
		}
		return nbt;
	}
	
	static void readFromClientNBT(CompoundTag tag, IBooleanStorage bool){
		List<Boolean> clientValues = new ArrayList<>();
		int size = tag.getInt(ElectrodynamicsCapabilities.BOOLEAN_KEY);
		for (int i = 0; i < size; i++) {
			clientValues.add(tag.getBoolean(ElectrodynamicsCapabilities.BOOLEAN_KEY + i));
		}
		bool.setClientValues(clientValues);
	}
	
}
