package electrodynamics.api.capability.dirstorage;

import java.util.List;

import net.minecraft.core.Direction;

public interface IDirectionalStorage {

	void addDirection(Direction dir);

	void removeDirection(Direction dir);

	void removeAllDirs();

	List<Direction> getDirections();

}
