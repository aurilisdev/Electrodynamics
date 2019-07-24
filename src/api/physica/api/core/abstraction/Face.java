package physica.api.core.abstraction;

import net.minecraftforge.common.util.ForgeDirection;

public enum Face {
	/** -Y */
	DOWN(0, -1, 0),

	/** +Y */
	UP(0, 1, 0),

	/** -Z */
	NORTH(0, 0, -1),

	/** +Z */
	SOUTH(0, 0, 1),

	/** -X */
	WEST(-1, 0, 0),

	/** +X */
	EAST(1, 0, 0),

	/**
	 * Used only by getOrientation, for invalid inputs
	 */
	UNKNOWN(0, 0, 0);

	public final int			offsetX;
	public final int			offsetY;
	public final int			offsetZ;
	public final int			flag;
	public static final Face[]	VALID			= { DOWN, UP, NORTH, SOUTH, WEST, EAST };
	public static final Face[]	OPPOSITES		= { UP, DOWN, SOUTH, NORTH, EAST, WEST, UNKNOWN };
	public static final int[][]	ROTATION_MATRIX	= { { 0, 1, 4, 5, 3, 2, 6 }, { 0, 1, 5, 4, 2, 3, 6 }, { 5, 4, 2, 3, 0, 1, 6 }, { 4, 5, 2, 3, 1, 0, 6 }, { 2, 3, 1, 0, 4, 5, 6 }, { 3, 2, 0, 1, 4, 5, 6 }, { 0, 1, 2, 3, 4, 5, 6 }, };
	public static final int[][]	RELATIVE_MATRIX	= { { 3, 2, 1, 0, 5, 4 }, { 4, 5, 0, 1, 2, 3 }, { 0, 1, 3, 2, 4, 5 }, { 0, 1, 2, 3, 5, 4 }, { 0, 1, 5, 4, 3, 2 }, { 0, 1, 4, 5, 2, 3 } };

	private Face(int x, int y, int z) {
		offsetX = x;
		offsetY = y;
		offsetZ = z;
		flag = 1 << ordinal();
	}

	public static Face getOrientation(int id)
	{
		if (id >= 0 && id < VALID.length)
		{
			return VALID[id];
		}
		return UNKNOWN;
	}

	public Face getOpposite()
	{
		return OPPOSITES[ordinal()];
	}

	public Face getRotation(Face axis)
	{
		return getOrientation(ROTATION_MATRIX[axis.ordinal()][ordinal()]);
	}

	public Face getRelativeSide(Face relative)
	{
		if (relative == Face.UNKNOWN || this == Face.UNKNOWN)
		{
			return Face.UNKNOWN;
		}
		return Face.getOrientation(RELATIVE_MATRIX[ordinal()][relative.ordinal()]);
	}

	public ForgeDirection Forge()
	{
		return ForgeDirection.getOrientation(ordinal());
	}

	public static Face Parse(Enum<?> obj)
	{
		return getOrientation(obj.ordinal());
	}

	public static Face Parse(int ordinal)
	{
		return getOrientation(ordinal);
	}
}
