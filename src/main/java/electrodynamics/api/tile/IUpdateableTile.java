package electrodynamics.api.tile;

import electrodynamics.packet.NetworkHandler;
import electrodynamics.packet.PacketUpdateTile;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;

public interface IUpdateableTile {
    CompoundNBT writeCustomPacket();

    CompoundNBT writeGUIPacket();

    void readGUIPacket(CompoundNBT nbt);

    void readCustomPacket(CompoundNBT nbt);

    default TileEntity getTile() {
	return (TileEntity) this;
    }

    default void sendCustomPacket() {
	PacketUpdateTile packet = new PacketUpdateTile(this, false);
	TileEntity source = getTile();
	World world = source.getWorld();
	BlockPos pos = source.getPos();
	if (world instanceof ServerWorld) {
	    ((ServerWorld) world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
		    .forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getNetworkManager(),
			    NetworkDirection.PLAY_TO_CLIENT));
	}
    }

    default void sendGUIPacket() {
	PacketUpdateTile packet = new PacketUpdateTile(this, true);
	TileEntity source = getTile();
	World world = source.getWorld();
	BlockPos pos = source.getPos();
	if (world instanceof ServerWorld) {
	    ((ServerWorld) world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
		    .forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getNetworkManager(),
			    NetworkDirection.PLAY_TO_CLIENT));
	}
    }
}
