package electrodynamics.api.capability.types.doublestorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityDoubleStorage implements IDoubleStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<IDoubleStorage> holder = LazyOptional.of(() -> this);

	public CapabilityDoubleStorage(int size) {
		for (int i = 0; i < size; i++) {
			serverValues.add(0.0);
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if (ElectrodynamicsCapabilities.DOUBLE_STORAGE_CAPABILITY != null) {
			return IDoubleStorage.saveToServerNBT(this);
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.DOUBLE_STORAGE_CAPABILITY != null) {
			IDoubleStorage.readFromServerNBT(nbt, this);
		}
	}
	
	public static CompoundTag saveToClientNBT(ItemStack stack) {
		LazyOptional<IDoubleStorage> lazyDoub = stack.getCapability(ElectrodynamicsCapabilities.DOUBLE_STORAGE_CAPABILITY);
		if(lazyDoub.isPresent()) {
			return IDoubleStorage.saveToClientNBT(lazyDoub.resolve().get());
		}
		return new CompoundTag();
	}
	
	public static void readFromClientNBT(CompoundTag tag, ItemStack stack) {
		LazyOptional<IDoubleStorage> lazyDoub = stack.getCapability(ElectrodynamicsCapabilities.DOUBLE_STORAGE_CAPABILITY);
		if(lazyDoub.isPresent()) {
			IDoubleStorage.readFromClientNBT(tag, lazyDoub.resolve().get());
		}
	}

	private List<Double> serverValues = new ArrayList<>();
	
	private List<Double> clientValues = new ArrayList<>();

	@Override
	public void setServerDouble(int index, double value) {
		if(index < serverValues.size()) {
			serverValues.set(index, value);
		}
	}

	@Override
	public double getServerDouble(int index) {
		if(index < serverValues.size()) {
			return serverValues.get(index);
		}
		return 0;
	}
	
	@Override
	public void addServerValue(double val) {
		serverValues.add(val);
	}
	
	@Override
	public List<Double> getServerValues() {
		return serverValues;
	}
	
	@Override
	public void clearServerValues() {
		serverValues.clear();
	}
	
	@Override
	public void setServerValues(List<Double> values) {
		serverValues = values;
	}
	
	@Override
	public double getClientValue(int index) {
		if(index < clientValues.size()) {
			return clientValues.get(index);
		}
		return 0;
	}
	
	@Override
	public List<Double> getClientValues() {
		return clientValues;
	}
	
	@Override
	public void setClientValues(List<Double> values) {
		clientValues = values;
	}
	
	@Override
	public void clearClientValues() {
		clientValues.clear();
	}

}
