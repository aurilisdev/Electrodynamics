package electrodynamics.common.tile.network;

import electrodynamics.DeferredRegisters;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileLogisticalWire extends TileWire {
    public boolean isPowered = false;

    public TileLogisticalWire(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_LOGISTICALWIRE.get(), pos, state);
	addComponent(new ComponentTickable().tickServer(this::tickServer));
    }

    /**
     * Copied from {@link #GenericTileTicking.tick}
     */
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

    protected void tickServer(ComponentTickable component) {
	if (component.getTicks() % 10 == 0) {
	    boolean shouldPower = getNetwork().getActiveTransmitted() > 0;
	    if (shouldPower != isPowered) {
		isPowered = shouldPower;
		level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
	    }
	}
    }
}
