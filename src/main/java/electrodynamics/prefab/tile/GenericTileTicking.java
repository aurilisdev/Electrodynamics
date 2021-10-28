package electrodynamics.prefab.tile;

import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;

public class GenericTileTicking extends GenericTile implements TickingBlockEntity {

    protected GenericTileTicking(BlockEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
	if (hasComponent(ComponentType.Tickable)) {
	    ComponentTickable tickable = getComponent(ComponentType.Tickable);
	    tickable.tickCommon();
	    if (!level.isClientSide) {
		tickable.tickServer();
	    } else {
		tickable.tickClient();
	    }
	}
    }

}
