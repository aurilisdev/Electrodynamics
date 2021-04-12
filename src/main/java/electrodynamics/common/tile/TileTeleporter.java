package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;

public class TileTeleporter extends GenericTileTicking {

    public TileTeleporter() {
	super(DeferredRegisters.TILE_TELEPORTER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).maxJoules(1999 * 20 /* TODO: What should this be? */)
		.voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4).input(Direction.DOWN));

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	return super.getRenderBoundingBox().expand(0, 2, 0);
    }

    protected void tickServer(ComponentTickable tickable) {
	if (tickable.getTicks() % 40 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
    }
}
