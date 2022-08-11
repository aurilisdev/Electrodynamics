package physica.library.tile;

import physica.api.core.abstraction.Face;

public abstract class TileBaseRotateable extends TileBase {

	private Face facing = Face.NORTH;

	@Override
	public Face getFacing() {
		return facing;
	}

	@Override
	public void setFacing(Face facing) {
		this.facing = facing;
	}

	@Override
	public boolean isRotateAble() {
		return true;
	}
}
