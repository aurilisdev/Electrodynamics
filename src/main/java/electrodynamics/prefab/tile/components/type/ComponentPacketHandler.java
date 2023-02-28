package electrodynamics.prefab.tile.components.type;

import java.util.List;
import java.util.function.Consumer;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketSendUpdatePropertiesClient;
import electrodynamics.common.packet.types.PacketUpdateTile;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;

public class ComponentPacketHandler implements Component {
    private GenericTile holder;

    @Override
    public void holder(GenericTile holder) {
        this.holder = holder;
    }

    protected Consumer<CompoundTag> customPacketWriter;
    protected Consumer<CompoundTag> guiPacketWriter;
    protected Consumer<CompoundTag> customPacketReader;
    protected Consumer<CompoundTag> guiPacketReader;

    @Deprecated(forRemoval = true)
    public ComponentPacketHandler addCustomPacketWriter(Consumer<CompoundTag> consumer) {
        Consumer<CompoundTag> safe = consumer;
        if (customPacketWriter != null) {
            safe = safe.andThen(customPacketWriter);
        }
        customPacketWriter = safe;
        return this;
    }

    @Deprecated(forRemoval = true)
    public ComponentPacketHandler addGuiPacketWriter(Consumer<CompoundTag> consumer) {
        Consumer<CompoundTag> safe = consumer;
        if (guiPacketWriter != null) {
            safe = safe.andThen(guiPacketWriter);
        }
        guiPacketWriter = safe;
        return this;
    }

    @Deprecated(forRemoval = true)
    public ComponentPacketHandler addCustomPacketReader(Consumer<CompoundTag> consumer) {
        Consumer<CompoundTag> safe = consumer;
        if (customPacketReader != null) {
            safe = safe.andThen(customPacketReader);
        }
        customPacketReader = safe;
        return this;
    }

    @Deprecated(forRemoval = true)
    public ComponentPacketHandler addGuiPacketReader(Consumer<CompoundTag> consumer) {
        Consumer<CompoundTag> safe = consumer;
        if (guiPacketReader != null) {
            safe = safe.andThen(guiPacketReader);
        }
        guiPacketReader = safe;
        return this;
    }

    @Deprecated(since = "Changed to property system. Do not use if possible.")
    public Consumer<CompoundTag> getCustomPacketSupplier() {
        return customPacketWriter;
    }

    @Deprecated(since = "Changed to property system. Do not use if possible.")
    public Consumer<CompoundTag> getGuiPacketSupplier() {
        return guiPacketWriter;
    }

    @Deprecated(since = "Changed to property system. Do not use if possible.")
    public Consumer<CompoundTag> getCustomPacketConsumer() {
        return customPacketReader;
    }

    @Deprecated(since = "Changed to property system. Do not use if possible.")
    public Consumer<CompoundTag> getGuiPacketConsumer() {
        return guiPacketReader;
    }

    @Deprecated(since = "Changed to property system. Do not use if possible.")
    public void sendCustomPacket() {
        if (customPacketWriter != null) {
            PacketUpdateTile packet = new PacketUpdateTile(this, holder.getBlockPos(), false, new CompoundTag());
            Level world = holder.getLevel();
            BlockPos pos = holder.getBlockPos();
            if (world instanceof ServerLevel level) {
                level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
            }
        }
    }

    @Deprecated(since = "Changed to property system. Do not use if possible.")
    public void sendGuiPacketToTracking() {
        if (guiPacketWriter != null) {
            PacketUpdateTile packet = new PacketUpdateTile(this, holder.getBlockPos(), true, new CompoundTag());
            Level world = holder.getLevel();
            BlockPos pos = holder.getBlockPos();
            if (world instanceof ServerLevel level) {
                level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
            }
        }
    }

    public void sendProperties() {
        Level world = holder.getLevel();
        if (world != null) {
            if (!world.isClientSide) {
                if (holder.getPropertyManager().isDirty()) {
                    BlockPos pos = holder.getBlockPos();
                    if (world instanceof ServerLevel level) {
                        List<ServerPlayer> players = level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false);
                        if (!players.isEmpty()) {
                            PacketSendUpdatePropertiesClient packet = new PacketSendUpdatePropertiesClient(holder);
                            players.forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
                            holder.getPropertyManager().clean();
                        }
                    }
                }
            }
        }
    }

    @Override
    public ComponentType getType() {
        return ComponentType.PacketHandler;
    }
}
