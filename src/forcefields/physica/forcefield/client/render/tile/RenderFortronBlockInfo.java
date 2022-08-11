package physica.forcefield.client.render.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import physica.api.core.tile.ITileBase;

@SideOnly(Side.CLIENT)
public class RenderFortronBlockInfo {

	private ITileBase tile;
	private double x;
	private double y;
	private double z;
	private double delta;

	public RenderFortronBlockInfo(ITileBase tile, double x, double y, double z, double delta) {
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.z = z;
		this.delta = delta;
	}

	public ITileBase getTile() {
		return tile;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getDeltaFrame() {
		return delta;
	}

}
