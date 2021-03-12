package electrodynamics.common.tile.generic.component.type;

import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;

public class ComponentTickable implements Component {
    private GenericTile holder;

    @Override
    public void setHolder(GenericTile holder) {
	this.holder = holder;
    }

    protected Runnable tickCommon;
    protected Runnable tickClient;
    protected Runnable tickServer;

    public ComponentTickable setTickCommon(Runnable tickCommon) {
	this.tickCommon = tickCommon;
	return this;
    }

    public ComponentTickable setTickClient(Runnable tickClient) {
	this.tickClient = tickClient;
	return this;
    }

    public ComponentTickable setTickServer(Runnable tickServer) {
	this.tickServer = tickServer;
	return this;
    }

    public void tickCommon() {
	if (tickCommon != null) {
	    tickCommon.run();
	}
    }

    public void tickServer() {
	if (tickServer != null) {
	    tickServer.run();
	}
	if (holder.getWorld().getWorldInfo().getDayTime() % 3 == 0 && holder.hasComponent(ComponentType.PacketHandler)
		&& holder.hasComponent(ComponentType.Inventory)
		&& !holder.<ComponentInventory>getComponent(ComponentType.Inventory).getViewing().isEmpty()) {
	    holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sentGuiPacketToTracking();
	}
    }

    public void tickClient() {
	if (tickClient != null) {
	    tickClient.run();
	}
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Tickable;
    }
}
