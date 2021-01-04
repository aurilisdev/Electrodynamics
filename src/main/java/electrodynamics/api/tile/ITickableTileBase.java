package electrodynamics.api.tile;

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
		}
	}

	default void tickServer() {
	}

	default void tickClient() {
	}

	default void tickCommon() {
	}

}
