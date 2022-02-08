package electrodynamics.api.capability.types.dirstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public interface IDirectionalStorage {

	//SERVER
	
	void setServerDirection(int index, Direction dir);
	
	void addServerDirection(Direction dir);

	void removeServerDirection(Direction dir);

	void clearServerDirections();
	
	Direction getServerDirection(int index);

	List<Direction> getServerDirections();
	
	void setServerDirections(List<Direction> values);
	
	//CLIENT
	
	Direction getClientDirection(int index, Direction dir);
	
	List<Direction> getClientDirections();
	
	void setClientDirections(List<Direction> values);
	
	void clearClientDirections();
	
	//FUNCTIONS
	
	static CompoundTag saveToServerNBT(IDirectionalStorage directional) {
		CompoundTag nbt = new CompoundTag();
		ListTag dirList = new ListTag();
		for (Direction dir : directional.getServerDirections()) {
			CompoundTag tag = new CompoundTag();
			tag.putString(ElectrodynamicsCapabilities.DIR_KEY, dir.getName());
			dirList.add(tag);
		}
		nbt.put("dirList", dirList);
		return nbt;
	}
	
	static void readFromServerNBT(CompoundTag tag, IDirectionalStorage directional) {
		List<Direction> serverDirs = new ArrayList<>();
		ListTag dirList = tag.getList("dirList", 10);
		for (Object element : dirList) {
			CompoundTag compound = (CompoundTag) element;
			serverDirs.add(Direction.valueOf(compound.getString(ElectrodynamicsCapabilities.DIR_KEY).toUpperCase()));
		}
		directional.setServerDirections(serverDirs);
	}
	
	static CompoundTag saveToClientNBT(IDirectionalStorage directional) {
		CompoundTag nbt = new CompoundTag();
		ListTag dirList = new ListTag();
		for (Direction dir : directional.getServerDirections()) {
			CompoundTag tag = new CompoundTag();
			tag.putString(ElectrodynamicsCapabilities.DIR_KEY, dir.getName());
			dirList.add(tag);
		}
		nbt.put("dirList", dirList);
		return nbt;
	}
	
	static void readFromClientNBT(CompoundTag tag, IDirectionalStorage directional) {
		List<Direction> clientDirs = new ArrayList<>();
		ListTag dirList = tag.getList("dirList", 10);
		for (Object element : dirList) {
			CompoundTag compound = (CompoundTag) element;
			clientDirs.add(Direction.valueOf(compound.getString(ElectrodynamicsCapabilities.DIR_KEY).toUpperCase()));
		}
		directional.setClientDirections(clientDirs);
	}

}
