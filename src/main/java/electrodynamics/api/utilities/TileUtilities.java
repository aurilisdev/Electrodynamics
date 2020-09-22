package electrodynamics.api.utilities;

import net.minecraft.util.Direction;

public class TileUtilities {
	public static final int[][] RELATIVE_MATRIX = { { 3, 2, 1, 0, 5, 4 }, { 4, 5, 0, 1, 2, 3 }, { 0, 1, 3, 2, 4, 5 }, { 0, 1, 2, 3, 5, 4 }, { 0, 1, 5, 4, 3, 2 }, { 0, 1, 4, 5, 2, 3 } };

	public static Direction getRelativeSide(Direction main, Direction relative) {
		return Direction.byIndex(RELATIVE_MATRIX[main.ordinal()][relative.ordinal()]);
	}

}
