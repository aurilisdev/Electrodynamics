package universalelectricity.api.tile;

import net.minecraft.util.EnumFacing;

public interface ITileRotatable {
	boolean canSetFacing(EnumFacing facing);

	EnumFacing getFacing();

	void setFacing(EnumFacing facing);
}