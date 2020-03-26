package physica.tile;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileBase extends TileEntity {

	@Override
	public void handleUpdateTag(@Nonnull NBTTagCompound tag)
	{
		super.readFromNBT(tag);
	}
}