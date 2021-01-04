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
import net.minecraftforge.fml.network.PacketDistributor;

public interface IUpdateableTile {
	CompoundNBT createUpdateTag();

	void handleUpdatePacket(CompoundNBT nbt);

	default TileEntity getTile() {
		return (TileEntity) this;
	}

	default void sendUpdatePacket() {
		PacketUpdateTile packet = new PacketUpdateTile(this);
		TileEntity source = getTile();
		World world = source.getWorld();
		BlockPos pos = source.getPos();
		if (world instanceof ServerWorld) {
			((ServerWorld) world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false).forEach(p -> {
				NetworkHandler.CHANNEL.sendTo(packet, p.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
			});
		} else {
			NetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunk(pos.getX() >> 4, pos.getZ() >> 4)), packet);
		}
	}
}
