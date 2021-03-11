package electrodynamics.common.tile.generic.component.type;

import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;

public class ComponentTickable implements Component {
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
