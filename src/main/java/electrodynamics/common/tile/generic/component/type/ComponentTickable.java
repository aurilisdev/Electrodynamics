package electrodynamics.common.tile.generic.component.type;

import java.util.function.Consumer;

import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;

public class ComponentTickable implements Component {
    private GenericTile holder;

    @Override
    public void setHolder(GenericTile holder) {
	this.holder = holder;
    }

    protected Consumer<ComponentTickable> tickCommon;
    protected Consumer<ComponentTickable> tickClient;
    protected Consumer<ComponentTickable> tickServer;
    private long ticks = 0;

    public ComponentTickable addTickCommon(Consumer<ComponentTickable> consumer) {
	Consumer<ComponentTickable> safe = consumer;
	if (tickCommon != null) {
	    safe = safe.andThen(tickCommon);
	}
	tickCommon = safe;
	return this;
    }

    public ComponentTickable addTickClient(Consumer<ComponentTickable> consumer) {
	Consumer<ComponentTickable> safe = consumer;
	if (tickClient != null) {
	    safe = safe.andThen(tickClient);
	}
	tickClient = safe;
	return this;
    }

    public ComponentTickable addTickServer(Consumer<ComponentTickable> consumer) {
	Consumer<ComponentTickable> safe = consumer;
	if (tickServer != null) {
	    safe = safe.andThen(tickServer);
	}
	tickServer = safe;
	return this;
    }

    public void tickCommon() {
	ticks++;
	if (tickCommon != null) {
	    tickCommon.accept(this);
	}
    }

    public void tickServer() {
	if (tickServer != null) {
	    tickServer.accept(this);
	}
	if (ticks % 3 == 0 && holder.hasComponent(ComponentType.PacketHandler)
		&& holder.hasComponent(ComponentType.Inventory)
		&& !holder.<ComponentInventory>getComponent(ComponentType.Inventory).getViewing().isEmpty()) {
	    holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
    }

    public void tickClient() {
	if (tickClient != null) {
	    tickClient.accept(this);
	}
    }

    public long getTicks() {
	return ticks;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Tickable;
    }
}
