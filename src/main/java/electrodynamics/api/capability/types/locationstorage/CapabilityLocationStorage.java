package electrodynamics.api.capability.types.locationstorage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityLocationStorage implements ILocationStorage, ICapabilitySerializable<CompoundNBT> {

	public final LazyOptional<ILocationStorage> holder = LazyOptional.of(() -> this);

	public CapabilityLocationStorage(int size) {
		// avoids null errors
		for (int i = 0; i < size; i++) {
			locations.add(new Location(0, 0, 0));
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		if (ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY != null) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putInt("size", locations.size());
			for (int i = 0; i < locations.size(); i++) {
				locations.get(i).writeToNBT(nbt, ElectrodynamicsCapabilities.LOCATION_KEY + i);
			}
			return nbt;
		}
		return new CompoundNBT();
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if (ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY != null) {
			locations.clear();
			for (int i = 0; i < nbt.getInt("size"); i++) {
				locations.add(Location.readFromNBT(nbt, ElectrodynamicsCapabilities.LOCATION_KEY + i));
			}
		}
	}

	private final List<Location> locations = new ArrayList<>();

	@Override
	public void setLocation(int index, double x, double y, double z) {
		locations.set(index, new Location(x, y, z));
	}

	@Override
	public Location getLocation(int index) {
		return locations.get(index);
	}

	@Override
	public void addLocation(double x, double y, double z) {
		locations.add(new Location(x, y, z));
	}

	@Override
	public void removeLocation(Location location) {
		locations.remove(location);
	}

	@Override
	public void clearLocations() {
		locations.clear();
	}

	@Override
	public List<Location> getLocations() {
		return locations;
	}

}