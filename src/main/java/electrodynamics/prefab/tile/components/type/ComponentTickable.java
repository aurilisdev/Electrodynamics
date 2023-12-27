package electrodynamics.prefab.tile.components.type;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponent;
import electrodynamics.prefab.tile.components.IComponentType;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ComponentTickable implements IComponent {
    
	private GenericTile holder;

	public ComponentTickable(GenericTile holder) {
		this.holder = holder;
	}
	
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
        // TODO remove

        if (ticks % 3 == 0 && holder.hasComponent(IComponentType.PacketHandler) && holder.hasComponent(IComponentType.Inventory) && !holder.<ComponentInventory>getComponent(IComponentType.Inventory).getViewing().isEmpty()) {
            holder.<ComponentPacketHandler>getComponent(IComponentType.PacketHandler).sendGuiPacketToTracking();
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

    public void performTick(World level) {
        if (level != null) {
            tickCommon();
            if (!level.isClientSide) {
                tickServer();
                if (holder != null && (holder.getPropertyManager().isDirty() || holder.isChanged)) {
                    holder.setChanged();
                    holder.getPropertyManager().clean();
                    holder.isChanged = false;
                }
            } else {
                tickClient();
            }
        }
    }

    @Override
    public IComponentType getType() {
        return IComponentType.Tickable;
    }
}
