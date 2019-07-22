package physica.nuclear.common.tile;

import net.minecraft.tileentity.TileEntity;
import physica.CoreReferences;
import physica.api.core.abstraction.FaceDirection;
import physica.library.location.Location;
import physica.library.tile.TileBase;

public class TileSiren extends TileBase {

	@Override
	public void updateServer(int ticks)
	{
		if (!worldObj.isRemote && worldObj.getWorldTime() % 30 == 0)
		{
			Location loc = getLocation();
			if (worldObj.getBlockPowerInput(loc.xCoord, loc.yCoord, loc.zCoord) > 0)
			{
				float volume = 2.5F;
				for (FaceDirection direction : FaceDirection.VALID_DIRECTIONS)
				{
					TileEntity tile = worldObj.getTileEntity(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY, loc.zCoord + direction.offsetZ);
					if (tile == this)
					{
						volume *= 1.5F;
					}
				}
				int pitch = worldObj.getBlockMetadata(loc.xCoord, loc.yCoord, loc.zCoord);
				worldObj.playSoundEffect(loc.xCoord, loc.yCoord, loc.zCoord, CoreReferences.PREFIX + "block.siren", volume, 1F - 0.18F * (pitch / 15F));
			}
		}
	}
}
