package electrodynamics.api.capability.types.intstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityIntStorage implements IIntStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<IIntStorage> holder = LazyOptional.of(() -> this);

	public CapabilityIntStorage(int size) {
		for (int i = 0; i < size; i++) {
			serverNumbers.add(0);
		}
	}

	public CapabilityIntStorage() {
		serverNumbers.add(0);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if (ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY != null) {
			return IIntStorage.saveToServerNBT(this);
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY != null) {
			IIntStorage.readFromServerNBT(nbt, this);
		}
	}
	
	public static CompoundTag saveToClientNBT(ItemStack stack) {
		LazyOptional<IIntStorage> lazyInt = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY);
		if(lazyInt.isPresent()) {
			return IIntStorage.saveToClientNBT(lazyInt.resolve().get());
		}
		return new CompoundTag();
	}
	
	public static void readFromClientNBT(CompoundTag tag, ItemStack stack) {
		LazyOptional<IIntStorage> lazyInt = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY);
		if(lazyInt.isPresent()) {
			IIntStorage.readFromClientNBT(tag, lazyInt.resolve().get());
		}
	}

	private List<Integer> serverNumbers = new ArrayList<>();
	
	private List<Integer> clientNumbers = new ArrayList<>();

	@Override
	public void setServerInt(int index, int number) {
		if(index < serverNumbers.size()) {
			serverNumbers.set(index, number);
		}
	}

	@Override
	public int getServerInt(int index) {
		if(index < serverNumbers.size()) {
			return serverNumbers.get(index);
		}
		return 0;
	}
	
	@Override
	public void addServerValue(int value) {
		serverNumbers.add(value);
	}
	
	@Override
	public List<Integer> getServerValues() {
		return serverNumbers;
	}
	
	@Override
	public void clearServerValues() {
		serverNumbers.clear();
	}
	
	@Override
	public void setServerValues(List<Integer> values) {
		serverNumbers = values;
	}
	
	@Override
	public int getClientInt(int index) {
		if(index < clientNumbers.size()) {
			return clientNumbers.get(index);
		}
		return 0;
	}
	
	@Override
	public List<Integer> getClientValues() {
		return clientNumbers;
	}
	
	@Override
	public void clearClientValues() {
		clientNumbers.clear();
	}
	
	@Override
	public void setClientValues(List<Integer> values) {
		clientNumbers = values;
	}

}
