package electrodynamics.api.capability.dirstorage;

import java.util.List;

import net.minecraft.core.Direction;

public interface ICapabilityDirectionalStorage {

	void addDirection(Direction dir);

	void removeDirection(Direction dir);

	void removeAllDirs();

	List<Direction> getDirections();

	void setBoolean(boolean bool);

	boolean getBoolean();

	void setInt(int number);

	int getInt();

}
