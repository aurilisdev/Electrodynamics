package electrodynamics.api.capability.types.locationstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.nbt.CompoundTag;

public interface ILocationStorage {

	//SERVER
	
	void addServerLocation(Location loc);
	
	void setServerLocation(int index, double x, double y, double z);

	void addServerLocation(double x, double y, double z);

	void removeServerLocation(Location location);

	void clearServerLocations();

	List<Location> getServerLocations();

	Location getServerLocation(int index);
	
	void setServerLocations(List<Location> values);
	
	//CLIENT
	
	Location getClientLocation(int index);
	
	List<Location> getClientLocations();
	
	void clearClientLocations();
	
	void setClientLocations(List<Location> values);
	
	//FUNCTIONS
	
	static CompoundTag saveToServerNBT(ILocationStorage stor) {
		CompoundTag nbt = new CompoundTag();
		int size = stor.getServerLocations().size();
		nbt.putInt("size", size);
		for (int i = 0; i < size; i++) {
			stor.getServerLocation(i).writeToNBT(nbt, ElectrodynamicsCapabilities.LOCATION_KEY + i);
		}
		return nbt;
	}
	
	static void readFromServerNBT(CompoundTag tag, ILocationStorage stor) {
		List<Location> serverValues = new ArrayList<>();
		for (int i = 0; i < tag.getInt("size"); i++) {
			serverValues.add(Location.readFromNBT(tag, ElectrodynamicsCapabilities.LOCATION_KEY + i));
		}
		stor.setServerLocations(serverValues);
	}
	
	static CompoundTag saveToClientNBT(ILocationStorage stor) {
		CompoundTag nbt = new CompoundTag();
		int size = stor.getServerLocations().size();
		nbt.putInt("size", size);
		for (int i = 0; i < size; i++) {
			stor.getServerLocation(i).writeToNBT(nbt, ElectrodynamicsCapabilities.LOCATION_KEY + i);
		}
		return nbt;
	}
	
	static void readFromClientNBT(CompoundTag tag, ILocationStorage stor) {
		List<Location> clientValues = new ArrayList<>();
		for (int i = 0; i < tag.getInt("size"); i++) {
			clientValues.add(Location.readFromNBT(tag, ElectrodynamicsCapabilities.LOCATION_KEY + i));
		}
		stor.setClientLocations(clientValues);
	}

}
