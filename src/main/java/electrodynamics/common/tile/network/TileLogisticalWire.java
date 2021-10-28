package electrodynamics.common.tile.network;

import electrodynamics.DeferredRegisters;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.world.level.block.entity.TickableBlockEntity;

public class TileLogisticalWire extends TileWire implements TickableBlockEntity {
    public boolean isPowered = false;

    public TileLogisticalWire() {
	super(DeferredRegisters.TILE_LOGISTICALWIRE.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
    }

    /**
     * Copied from {@link #GenericTileTicking.tick}
     */
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
