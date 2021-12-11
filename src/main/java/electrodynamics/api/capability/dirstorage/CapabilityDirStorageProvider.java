package electrodynamics.api.capability.dirstorage;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityDirStorageProvider implements ICapabilitySerializable<CompoundTag> {

	private final CapabilityDirStorageDefault dirStorage = new CapabilityDirStorageDefault();
    private final LazyOptional<ICapabilityDirStorage> lazyOptional = LazyOptional.of(() -> dirStorage);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityDirStorage.DIR_STORAGE_CAPABILITY) {
		    return lazyOptional.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if(CapabilityDirStorage.DIR_STORAGE_CAPABILITY != null) {
			CompoundTag nbt = new CompoundTag();
			ListTag dirList = new ListTag();
			for(Direction dir : dirStorage.getDirections()) {
				CompoundTag tag = new CompoundTag();
				tag.putString(CapabilityDirStorage.DIR_KEY, dir.getName());
				dirList.add(tag);
			}
			nbt.put("dirList", dirList);
			nbt.putBoolean(CapabilityDirStorage.BOOL_KEY, dirStorage.getBoolean());
			return nbt;
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if(CapabilityDirStorage.DIR_STORAGE_CAPABILITY != null) {
			ListTag dirList = nbt.getList("dirList", 10);
			for (int i = 0; i < dirList.size(); i++) {
				CompoundTag compound = (CompoundTag) dirList.get(i);
				dirStorage.addDirection(Direction.valueOf(compound.getString(CapabilityDirStorage.DIR_KEY).toUpperCase()));
			}
			dirStorage.setBoolean(nbt.getBoolean(CapabilityDirStorage.BOOL_KEY));
		}
	}

}
