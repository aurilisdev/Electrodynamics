package electrodynamics.api.capability.types.dirstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityDirectionalStorage implements IDirectionalStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<IDirectionalStorage> holder = LazyOptional.of(() -> this);

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if (ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY != null) {
			return IDirectionalStorage.saveToServerNBT(this);
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY != null) {
			IDirectionalStorage.readFromServerNBT(nbt, this);
		}
	}
	
	public static CompoundTag saveToClientNBT(ItemStack stack) {
		LazyOptional<IDirectionalStorage> lazyDir = stack.getCapability(ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY);
		if(lazyDir.isPresent()) {
			return IDirectionalStorage.saveToClientNBT(lazyDir.resolve().get());
		}
		return new CompoundTag();
	}
	
	public static void readFromClientNBT(CompoundTag tag, ItemStack stack) {
		LazyOptional<IDirectionalStorage> lazyDir = stack.getCapability(ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY);
		if(lazyDir.isPresent()) {
			IDirectionalStorage.readFromClientNBT(tag, lazyDir.resolve().get());
		}
	}

	private List<Direction> serverDirections = new ArrayList<>();
	
	private List<Direction> clientDirections = new ArrayList<>();

	@Override
	public void addServerDirection(Direction dir) {
		serverDirections.add(dir);
	}
	
	@Override
	public Direction getServerDirection(int index) {
		if(index < serverDirections.size()) {
			return serverDirections.get(index);
		}
		return Direction.UP;
	}
	
	@Override
	public void setServerDirection(int index, Direction dir) {
		if(index < serverDirections.size()) {
			serverDirections.set(index, dir);
		}
	}

	@Override
	public void removeServerDirection(Direction dir) {
		serverDirections.remove(dir);
	}

	@Override
	public void clearServerDirections() {
		serverDirections.clear();
	}

	@Override
	public List<Direction> getServerDirections() {
		return serverDirections;
	}
	
	@Override
	public void setServerDirections(List<Direction> values) {
		serverDirections = values;
	}
	
	@Override
	public Direction getClientDirection(int index, Direction dir) {
		if(index < clientDirections.size()) {
			return clientDirections.get(index);
		}
		return Direction.UP;
	}
	
	@Override
	public List<Direction> getClientDirections() {
		return clientDirections;
	}
	
	@Override
	public void clearClientDirections() {
		clientDirections.clear();
	}
	
	@Override
	public void setClientDirections(List<Direction> values) {
		clientDirections = values;
	}

}
