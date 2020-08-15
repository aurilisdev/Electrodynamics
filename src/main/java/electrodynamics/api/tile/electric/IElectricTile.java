package electrodynamics.api.tile.electric;

import net.minecraft.util.Direction;

public interface IElectricTile {
	String JOULES_STORED_NBT = "joulesstored";

	boolean canConnectElectrically(Direction direction);
}
