package electrodynamics.prefab.tile.components.type;

import java.util.function.Consumer;
import java.util.stream.Stream;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.client.PacketSendUpdatePropertiesClient;
import electrodynamics.common.packet.types.client.PacketUpdateTile;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponent;
import electrodynamics.prefab.tile.components.IComponentType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;

public class ComponentPacketHandler implements IComponent {

	private GenericTile holder;

	public ComponentPacketHandler(GenericTile tile) {
		holder = tile;
	}

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	protected Consumer<CompoundNBT> customPacketWriter;
	protected Consumer<CompoundNBT> guiPacketWriter;
	protected Consumer<CompoundNBT> customPacketReader;
	protected Consumer<CompoundNBT> guiPacketReader;

	@Deprecated
	public ComponentPacketHandler addCustomPacketWriter(Consumer<CompoundNBT> consumer) {
		Consumer<CompoundNBT> safe = consumer;
		if (customPacketWriter != null) {
			safe = safe.andThen(customPacketWriter);
		}
		customPacketWriter = safe;
		return this;
	}

	@Deprecated
	public ComponentPacketHandler addGuiPacketWriter(Consumer<CompoundNBT> consumer) {
		Consumer<CompoundNBT> safe = consumer;
		if (guiPacketWriter != null) {
			safe = safe.andThen(guiPacketWriter);
		}
		guiPacketWriter = safe;
		return this;
	}

	@Deprecated
	public ComponentPacketHandler addCustomPacketReader(Consumer<CompoundNBT> consumer) {
		Consumer<CompoundNBT> safe = consumer;
		if (customPacketReader != null) {
			safe = safe.andThen(customPacketReader);
		}
		customPacketReader = safe;
		return this;
	}

	@Deprecated
	public ComponentPacketHandler addGuiPacketReader(Consumer<CompoundNBT> consumer) {
		Consumer<CompoundNBT> safe = consumer;
		if (guiPacketReader != null) {
			safe = safe.andThen(guiPacketReader);
		}
		guiPacketReader = safe;
		return this;
	}

	@Deprecated // "Changed to property system. Do not use if possible."
	public Consumer<CompoundNBT> getCustomPacketSupplier() {
		return customPacketWriter;
	}

	@Deprecated // "Changed to property system. Do not use if possible."
	public Consumer<CompoundNBT> getGuiPacketSupplier() {
		return guiPacketWriter;
	}

	@Deprecated // "Changed to property system. Do not use if possible."
	public Consumer<CompoundNBT> getCustomPacketConsumer() {
		return customPacketReader;
	}

	@Deprecated // "Changed to property system. Do not use if possible."
	public Consumer<CompoundNBT> getGuiPacketConsumer() {
		return guiPacketReader;
	}

	@Deprecated // "Changed to property system. Do not use if possible."
	public void sendCustomPacket() {
		if (customPacketWriter != null) {
			PacketUpdateTile packet = new PacketUpdateTile(this, holder.getBlockPos(), false, new CompoundNBT());
			World world = holder.getLevel();
			BlockPos pos = holder.getBlockPos();
			if (world instanceof ServerWorld) {
				ServerWorld level = (ServerWorld) world;
				level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
			}
		}
	}

	@Deprecated // "Changed to property system. Do not use if possible."
	public void sendGuiPacketToTracking() {
		if (guiPacketWriter != null) {
			PacketUpdateTile packet = new PacketUpdateTile(this, holder.getBlockPos(), true, new CompoundNBT());
			World world = holder.getLevel();
			BlockPos pos = holder.getBlockPos();
			if (world instanceof ServerWorld) {
				ServerWorld level = (ServerWorld) world;
				level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
			}
		}
	}

	public void sendProperties() {
		World world = holder.getLevel();
		if (world != null) {
			if (!world.isClientSide) {
				if (holder.getPropertyManager().isDirty()) {
					BlockPos pos = holder.getBlockPos();
					if (world instanceof ServerWorld) {
						ServerWorld level = (ServerWorld) world;
						Stream<ServerPlayerEntity> players = level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false);
						PacketSendUpdatePropertiesClient packet = new PacketSendUpdatePropertiesClient(holder);
						players.forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT));
						holder.getPropertyManager().clean();
					}
				}
			}
		}
	}

	@Override
	public IComponentType getType() {
		return IComponentType.PacketHandler;
	}
}
