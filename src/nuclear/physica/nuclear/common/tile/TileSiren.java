package physica.nuclear.common.tile;

import net.minecraft.tileentity.TileEntity;
import physica.api.core.abstraction.Face;
import physica.library.location.GridLocation;
import physica.library.tile.TileBase;
import physica.nuclear.NuclearReferences;

public class TileSiren extends TileBase {

	@Override
	public void updateServer(int ticks) {
		if (!World().isRemote && World().getWorldTime() % 30 == 0) {
			GridLocation loc = getLocation();
			if (World().getBlockPowerInput(loc.xCoord, loc.yCoord, loc.zCoord) > 0) {
				float volume = 2.5F;
				for (Face direction : Face.VALID) {
					TileEntity tile = World().getTileEntity(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY, loc.zCoord + direction.offsetZ);
					if (tile == this) {
						volume *= 1.5F;
					}
				}
				int pitch = World().getBlockMetadata(loc.xCoord, loc.yCoord, loc.zCoord);
				World().playSoundEffect(loc.xCoord, loc.yCoord, loc.zCoord, NuclearReferences.PREFIX + "block.siren", volume, 1F - 0.18F * (pitch / 15F));
			}
		}
	}
}
