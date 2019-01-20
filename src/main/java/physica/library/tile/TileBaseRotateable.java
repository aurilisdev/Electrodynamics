package physica.library.tile;

import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileBaseRotateable extends TileBase {

	private ForgeDirection facing = ForgeDirection.NORTH;

	@Override
	public ForgeDirection getFacing() {
		return facing;
	}

	@Override
	public void setFacing(ForgeDirection facing) {
		this.facing = facing;
	}

	@Override
	public boolean isRotateAble() {
		return true;
	}
}
