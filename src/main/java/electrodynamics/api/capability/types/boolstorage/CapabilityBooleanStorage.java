package electrodynamics.api.capability.types.boolstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityBooleanStorage implements IBooleanStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<IBooleanStorage> holder = LazyOptional.of(() -> this);

	public CapabilityBooleanStorage() {
		serverValues.add(false);
	}

	public CapabilityBooleanStorage(int size) {
		for (int i = 0; i < size; i++) {
			serverValues.add(false);
		}

	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if (ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY != null) {
			return IBooleanStorage.saveToServerNBT(this);
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY != null) {
			IBooleanStorage.readFromServerNBT(nbt, this);
		} 
	}
	
	public static CompoundTag saveToClientNBT(ItemStack stack) {
		LazyOptional<IBooleanStorage> lazyBool = stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY);
		if(lazyBool.isPresent()) {
			return IBooleanStorage.saveToClientNBT(lazyBool.resolve().get());
		}
		return new CompoundTag();
	}
	
	public static void readFromClientNBT(CompoundTag tag, ItemStack stack) {
		LazyOptional<IBooleanStorage> lazyBool = stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY);
		if(lazyBool.isPresent()) {
			IBooleanStorage.readFromClientNBT(tag, lazyBool.resolve().get());
		}
	}

	private List<Boolean> serverValues = new ArrayList<>();
	private List<Boolean> clientValues = new ArrayList<>();

	@Override
	public void setServerBoolean(int index, boolean bool) {
		if(index < serverValues.size()) {
			serverValues.set(index, bool);
		}
	}
	
	@Override
	public void addServerBoolean(boolean val) {
		serverValues.add(val);
	}

	@Override
	public boolean getServerBoolean(int index) {
		if(index < serverValues.size()) {
			return serverValues.get(index);
		}
		return false;
	}
	
	@Override
	public List<Boolean> getServerValues() {
		return serverValues;
	}
	
	@Override
	public void clearServerValues() {
		serverValues.clear();
	}
	
	@Override
	public void setServerValues(List<Boolean> values) {
		serverValues = values;
	}
	
	@Override
	public boolean getClientBoolean(int index) {
		if(index < clientValues.size()) {
			return clientValues.get(index);
		}
		return false;
	}
	
	@Override
	public List<Boolean> getClientValues() {
		return clientValues;
	}
	
	@Override
	public void setClientValues(List<Boolean> values) {
		clientValues = values;
	}
	
	@Override
	public void clearClientValues() {
		clientValues.clear();
	}

}
