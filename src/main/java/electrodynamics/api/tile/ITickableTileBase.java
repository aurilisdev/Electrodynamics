package electrodynamics.api.tile;

import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public interface ITickableTileBase extends ITickableTileEntity {
    @Override
    default void tick() {
	TileEntity construct = (TileEntity) this;
	tickCommon();
	if (construct.getWorld().isRemote) {
	    tickClient();
	} else {
	    tickServer();
	    if (construct instanceof GenericTileInventory) {
		GenericTileInventory inv = (GenericTileInventory) construct;
		if (!inv.getViewing().isEmpty() && construct.getWorld().getWorldInfo().getDayTime() % 3 == 0
			&& construct instanceof IUpdateableTile) {
		    ((GenericTileInventory) construct).sendGUIPacket();
		}
	    }
	}
    }

    default void tickServer() {
    }

    default void tickClient() {
    }

    default void tickCommon() {
    }

}
