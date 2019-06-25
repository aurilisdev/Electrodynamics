package physica.nuclear.common.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import physica.CoreReferences;
import physica.library.tile.TileBase;

public class TileSiren extends TileBase {

	@Override
	public void updateServer(int ticks)
	{
		if (!worldObj.isRemote && worldObj.getWorldTime() % 30 == 0)
		{
			if (worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0)
			{
				float volume = 2.5F;
				for (final ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
				{
					TileEntity tile = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
					if (tile == this)
					{
						volume *= 1.5F;
					}
				}
				int pitch = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, CoreReferences.PREFIX + "block.siren", volume, 1F - 0.18F * (pitch / 15F));
			}
		}
	}
}
