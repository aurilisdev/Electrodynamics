package electrodynamics.common.tile.generic;

import electrodynamics.common.tile.generic.component.ComponentTickable;
import electrodynamics.common.tile.generic.component.ComponentType;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.tileentity.TileEntityType;

public class GenericTileTickable extends GenericTile implements ITickable {

    protected GenericTileTickable(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
	if (hasComponent(ComponentType.Tickable)) {
	    ComponentTickable tickable = getComponent(ComponentType.Tickable);
	    tickable.getTickCommon().get();
	    if (!world.isRemote) {
		tickable.getTickServer().get();
	    } else {
		tickable.getTickClient().get();
	    }
	}
    }
}
