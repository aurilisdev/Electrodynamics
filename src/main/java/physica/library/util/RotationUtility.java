package physica.library.util;

import net.minecraftforge.common.util.ForgeDirection;

public class RotationUtility {

	public static final int[][] relativeMatrix = new int[][] { new int[] { 3, 2, 1, 0, 5, 4 }, new int[] { 4, 5, 0, 1, 2, 3 }, new int[] { 0, 1, 3, 2, 4, 5 }, new int[] { 0, 1, 2, 3, 5, 4 }, new int[] { 0, 1, 5, 4, 3, 2 },
			new int[] { 0, 1, 4, 5, 2, 3 } };

	public static ForgeDirection getRelativeSide(ForgeDirection relativeDirection, ForgeDirection currentDirection)
	{
		if (relativeDirection == ForgeDirection.UNKNOWN || currentDirection == ForgeDirection.UNKNOWN)
		{
			return ForgeDirection.UNKNOWN;
		}
		return ForgeDirection.getOrientation(relativeMatrix[currentDirection.ordinal()][relativeDirection.ordinal()]);
	}
}
