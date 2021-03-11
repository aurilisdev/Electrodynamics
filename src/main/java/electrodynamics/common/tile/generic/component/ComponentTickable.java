package electrodynamics.common.tile.generic.component;

import java.util.function.Supplier;

public class ComponentTickable implements Component {
    private Supplier<Void> tickCommon;
    private Supplier<Void> tickClient;
    private Supplier<Void> tickServer;

    public ComponentTickable setTickCommon(Supplier<Void> tickCommon) {
	this.tickCommon = tickCommon;
	return this;
    }

    public ComponentTickable setTickClient(Supplier<Void> tickClient) {
	this.tickClient = tickClient;
	return this;
    }

    public ComponentTickable setTickServer(Supplier<Void> tickServer) {
	this.tickServer = tickServer;
	return this;
    }

    public Supplier<Void> getTickCommon() {
	return tickCommon;
    }

    public Supplier<Void> getTickClient() {
	return tickClient;
    }

    public Supplier<Void> getTickServer() {
	return tickServer;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Tickable;
    }
}
