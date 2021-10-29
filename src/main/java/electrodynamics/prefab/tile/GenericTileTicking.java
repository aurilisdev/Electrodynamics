package electrodynamics.prefab.tile;

import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GenericTileTicking extends GenericTile {

    protected GenericTileTicking(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPosition, BlockState blockState) {
	super(tileEntityTypeIn, worldPosition, blockState);
    }

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
