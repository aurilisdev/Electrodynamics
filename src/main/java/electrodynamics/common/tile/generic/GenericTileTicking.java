package electrodynamics.common.tile.generic;

import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class GenericTileTicking extends GenericTile implements ITickableTileEntity {

    protected GenericTileTicking(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
	if (hasComponent(ComponentType.Tickable)) {
	    ComponentTickable tickable = getComponent(ComponentType.Tickable);
	    tickable.tickCommon();
	    if (!world.isRemote) {
		tickable.tickServer();
	    } else {
		tickable.tickClient();
	    }
	}
    }
}
