package physica.library.tile;

import physica.api.core.abstraction.FaceDirection;

public abstract class TileBaseRotateable extends TileBase {

	private FaceDirection facing = FaceDirection.NORTH;

	@Override
	public FaceDirection getFacing()
	{
		return facing;
	}

	@Override
	public void setFacing(FaceDirection facing)
	{
		this.facing = facing;
	}

	@Override
	public boolean isRotateAble()
	{
		return true;
	}
}
