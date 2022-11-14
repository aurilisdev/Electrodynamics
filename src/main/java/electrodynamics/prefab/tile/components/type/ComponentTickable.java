package electrodynamics.prefab.tile.components.type;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import net.minecraft.world.level.Level;

public class ComponentTickable implements Component {
	private GenericTile holder;

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	protected Consumer<ComponentTickable> tickCommon;
	protected Consumer<ComponentTickable> tickClient;
	protected Consumer<ComponentTickable> tickServer;
	private long ticks = 0;

	public ComponentTickable tickCommon(@Nonnull Consumer<ComponentTickable> consumer) {
		Consumer<ComponentTickable> safe = consumer;
		if (tickCommon != null) {
			safe = safe.andThen(tickCommon);
		}
		tickCommon = safe;
		return this;
	}

	public ComponentTickable tickClient(@Nonnull Consumer<ComponentTickable> consumer) {
		Consumer<ComponentTickable> safe = consumer;
		if (tickClient != null) {
			safe = safe.andThen(tickClient);
		}
		tickClient = safe;
		return this;
	}

	public ComponentTickable tickServer(@Nonnull Consumer<ComponentTickable> consumer) {
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
		if (ticks % 3 == 0 && holder.hasComponent(ComponentType.PacketHandler) && holder.hasComponent(ComponentType.Inventory) && !holder.<ComponentInventory>getComponent(ComponentType.Inventory).getViewing().isEmpty()) {
			holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		if (ticks % 3 == 0 && holder.getPropertyManager().isDirty()) {
			holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendProperties();
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

	public void performTick(Level level) {
		tickCommon();
		if (!level.isClientSide) {
			tickServer();
		} else {
			tickClient();
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.Tickable;
	}
}
