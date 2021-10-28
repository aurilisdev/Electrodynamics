package electrodynamics.prefab.tile;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.PacketUpdateTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkDirection;

public interface IUpdateableTile {
    CompoundTag writeCustomPacket();

    CompoundTag writeGUIPacket();

    void readGUIPacket(CompoundTag nbt);

    void readCustomPacket(CompoundTag nbt);

    default BlockEntity getTile() {
	return (BlockEntity) this;
    }

    default void sendCustomPacket() {
	PacketUpdateTile packet = new PacketUpdateTile(this, false);
	BlockEntity source = getTile();
	Level world = source.getLevel();
	BlockPos pos = source.getBlockPos();
	if (world instanceof ServerLevel) {
	    ((ServerLevel) world).getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)
		    .forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
	}
    }

    default void sendGUIPacket() {
	PacketUpdateTile packet = new PacketUpdateTile(this, true);
	BlockEntity source = getTile();
	Level world = source.getLevel();
	BlockPos pos = source.getBlockPos();
	if (world instanceof ServerLevel) {
	    ((ServerLevel) world).getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)
		    .forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
	}
    }
}
