package electrodynamics.api.capability.types.dirstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
			CompoundTag nbt = new CompoundTag();
			ListTag dirList = new ListTag();
			for (Direction dir : directions) {
				CompoundTag tag = new CompoundTag();
				tag.putString(ElectrodynamicsCapabilities.DIR_KEY, dir.getName());
				dirList.add(tag);
			}
			nbt.put("dirList", dirList);
			return nbt;
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY != null) {
			ListTag dirList = nbt.getList("dirList", 10);
			for (Object element : dirList) {
				CompoundTag compound = (CompoundTag) element;
				addDirection(Direction.valueOf(compound.getString(ElectrodynamicsCapabilities.DIR_KEY).toUpperCase()));
			}
		}
	}

	private List<Direction> directions = new ArrayList<>();

	@Override
	public void addDirection(Direction dir) {
		directions.add(dir);
	}

	@Override
	public void removeDirection(Direction dir) {
		directions.remove(dir);
	}

	@Override
	public void removeAllDirs() {
		directions.clear();
	}

	@Override
	public List<Direction> getDirections() {
		return directions;
	}

}
