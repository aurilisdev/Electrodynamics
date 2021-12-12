package electrodynamics.api.capability.dirstorage;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Direction;

public class DirectionalStorage implements ICapabilityDirectionalStorage {

    private List<Direction> directions = new ArrayList<>();
    private boolean bool = false;
    private int num = 0;

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

    @Override
    public void setInt(int number) {
	num = number;
    }

    @Override
    public int getInt() {
	return num;
    }

}
