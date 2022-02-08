package electrodynamics.api.capability.types.locationstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityLocationStorage implements ILocationStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<ILocationStorage> holder = LazyOptional.of(() -> this);

	public CapabilityLocationStorage(int size) {
		// avoids null errors
		for (int i = 0; i < size; i++) {
			serverLocations.add(new Location(0, 0, 0));
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if (ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY != null) {
			return ILocationStorage.saveToServerNBT(this);
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY != null) {
			ILocationStorage.readFromServerNBT(nbt, this);
		}
	}
	
	public static CompoundTag saveToClientNBT(ItemStack stack) {
		LazyOptional<ILocationStorage> lazyLoc = stack.getCapability(ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY);
		if(lazyLoc.isPresent()) {
			return ILocationStorage.saveToClientNBT(lazyLoc.resolve().get());
		}
		return new CompoundTag();
	}
	
	public static void readFromClientNBT(CompoundTag tag, ItemStack stack) {
		LazyOptional<ILocationStorage> lazyLoc = stack.getCapability(ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY);
		if(lazyLoc.isPresent()) {
			ILocationStorage.readFromClientNBT(tag, lazyLoc.resolve().get());
		}
	}

	private List<Location> serverLocations = new ArrayList<>();
	
	private List<Location> clientLocations = new ArrayList<>();

	@Override
	public void setServerLocation(int index, double x, double y, double z) {
		if(index < serverLocations.size()) {
			serverLocations.set(index, new Location(x, y, z));
		}
	}

	@Override
	public Location getServerLocation(int index) {
		if(index < serverLocations.size()) {
			return serverLocations.get(index);
		}
		return new Location(0,0,0);
	}

	@Override
	public void addServerLocation(double x, double y, double z) {
		serverLocations.add(new Location(x, y, z));
	}

	@Override
	public void removeServerLocation(Location location) {
		serverLocations.remove(location);
	}

	@Override
	public void clearServerLocations() {
		serverLocations.clear();
	}

	@Override
	public List<Location> getServerLocations() {
		return serverLocations;
	}
	
	@Override
	public void addServerLocation(Location loc) {
		serverLocations.add(loc);
	}
	
	@Override
	public void setServerLocations(List<Location> values) {
		serverLocations = values;
	}
	
	@Override
	public Location getClientLocation(int index) {
		if(index < clientLocations.size()) {
			return clientLocations.get(index);
		}
		return new Location(0,0,0);
	}
	
	@Override
	public List<Location> getClientLocations() {
		return clientLocations;
	}
	
	@Override
	public void clearClientLocations() {
		clientLocations.clear();
	}
	
	@Override
	public void setClientLocations(List<Location> values) {
		clientLocations = values;
	}

}
