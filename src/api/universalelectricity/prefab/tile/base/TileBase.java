package universalelectricity.prefab.tile.base;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileBase extends TileEntity {
	public TileBase() {
	}

	@Override
	public void handleUpdateTag(@Nonnull NBTTagCompound tag) {
		super.readFromNBT(tag);
	}
}