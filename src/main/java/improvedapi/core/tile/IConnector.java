package improvedapi.core.tile;

import net.minecraft.util.Direction;

public interface IConnector {

    /**
     * @return If the connection is possible.
     */
    public boolean canConnect(Direction direction);
}
