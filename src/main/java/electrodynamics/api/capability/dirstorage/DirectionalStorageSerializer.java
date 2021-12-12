package electrodynamics.api.capability.dirstorage;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class DirectionalStorageSerializer implements ICapabilitySerializable<CompoundTag> {

    private final DirectionalStorage dirStorage = new DirectionalStorage();
    private final LazyOptional<ICapabilityDirectionalStorage> lazyOptional = LazyOptional.of(() -> dirStorage);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
	if (cap == CapabilityDirectionalStorage.DIR_STORAGE_CAPABILITY) {
	    return lazyOptional.cast();
	}
	return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
	if (CapabilityDirectionalStorage.DIR_STORAGE_CAPABILITY != null) {
	    CompoundTag nbt = new CompoundTag();
	    ListTag dirList = new ListTag();
	    for (Direction dir : dirStorage.getDirections()) {
		CompoundTag tag = new CompoundTag();
		tag.putString(CapabilityDirectionalStorage.DIR_KEY, dir.getName());
		dirList.add(tag);
	    }
	    nbt.put("dirList", dirList);
	    nbt.putBoolean(CapabilityDirectionalStorage.BOOL_KEY, dirStorage.getBoolean());
	    return nbt;
	}
	return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
	if (CapabilityDirectionalStorage.DIR_STORAGE_CAPABILITY != null) {
	    ListTag dirList = nbt.getList("dirList", 10);
	    for (Object element : dirList) {
		CompoundTag compound = (CompoundTag) element;
		dirStorage.addDirection(Direction.valueOf(compound.getString(CapabilityDirectionalStorage.DIR_KEY).toUpperCase()));
	    }
	    dirStorage.setBoolean(nbt.getBoolean(CapabilityDirectionalStorage.BOOL_KEY));
	}
    }

}
