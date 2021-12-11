package electrodynamics.api.capability.dirstorage;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Direction;

public class CapabilityDirStorageDefault implements ICapabilityDirStorage {

	private List<Direction> directions = new ArrayList<>();
	private boolean bool = false;
	
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

	@Override
	public void setBoolean(boolean bool) {
		this.bool = bool;
	}

	@Override
	public boolean getBoolean() {
		return bool;
	}

}
