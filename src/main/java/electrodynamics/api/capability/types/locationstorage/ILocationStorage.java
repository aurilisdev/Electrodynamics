package electrodynamics.api.capability.types.locationstorage;

import java.util.List;

import electrodynamics.prefab.utilities.object.Location;

public interface ILocationStorage {

	void setLocation(int index, double x, double y, double z);

	void addLocation(double x, double y, double z);

	void removeLocation(Location location);

	void clearLocations();

	List<Location> getLocations();

	Location getLocation(int index);

}