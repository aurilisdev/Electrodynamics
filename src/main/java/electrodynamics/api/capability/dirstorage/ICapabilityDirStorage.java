package electrodynamics.api.capability.dirstorage;

import java.util.List;

import net.minecraft.core.Direction;

public interface ICapabilityDirStorage {

    void addDirection(Direction dir);

    void removeDirection(Direction dir);

    void removeAllDirs();

    List<Direction> getDirections();

    void setBoolean(boolean bool);

    boolean getBoolean();

}
