package electrodynamics.prefab.tile.components.type;

import java.util.function.Consumer;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.PacketUpdateTile;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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

    public ComponentPacketHandler customPacketWriter(Consumer<CompoundTag> consumer) {
	Consumer<CompoundTag> safe = consumer;
	if (customPacketWriter != null) {
	    safe = safe.andThen(customPacketWriter);
	}
	customPacketWriter = safe;
	return this;
    }

    public ComponentPacketHandler guiPacketWriter(Consumer<CompoundTag> consumer) {
	Consumer<CompoundTag> safe = consumer;
	if (guiPacketWriter != null) {
	    safe = safe.andThen(guiPacketWriter);
	}
	guiPacketWriter = safe;
	return this;
    }

    public ComponentPacketHandler customPacketReader(Consumer<CompoundTag> consumer) {
	Consumer<CompoundTag> safe = consumer;
	if (customPacketReader != null) {
	    safe = safe.andThen(customPacketReader);
	}
	customPacketReader = safe;
	return this;
    }

    public ComponentPacketHandler guiPacketReader(Consumer<CompoundTag> consumer) {
	Consumer<CompoundTag> safe = consumer;
	if (guiPacketReader != null) {
	    safe = safe.andThen(guiPacketReader);
	}
	guiPacketReader = safe;
	return this;
    }

    public Consumer<CompoundTag> getCustomPacketSupplier() {
	return customPacketWriter;
    }

    public Consumer<CompoundTag> getGuiPacketSupplier() {
	return guiPacketWriter;
    }

    public Consumer<CompoundTag> getCustomPacketConsumer() {
	return customPacketReader;
    }

    public Consumer<CompoundTag> getGuiPacketConsumer() {
	return guiPacketReader;
    }

    public void sendCustomPacket() {
	PacketUpdateTile packet = new PacketUpdateTile(this, holder.getBlockPos(), false, new CompoundTag());
	Level world = holder.getLevel();
	BlockPos pos = holder.getBlockPos();
	if (world instanceof ServerLevel) {
	    ((ServerLevel) world).getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)
		    .forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
	}
    }

    public void sendGuiPacketToTracking() {
	PacketUpdateTile packet = new PacketUpdateTile(this, holder.getBlockPos(), true, new CompoundTag());
	Level world = holder.getLevel();
	BlockPos pos = holder.getBlockPos();
	if (world instanceof ServerLevel) {
	    ((ServerLevel) world).getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)
		    .forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
	}
    }

    @Override
    public ComponentType getType() {
	return ComponentType.PacketHandler;
    }
}
