package electrodynamics.common.tile.generic.component.type;

import java.util.function.Consumer;
import java.util.function.Supplier;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.PacketUpdateTile;
import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;

public class ComponentPacketHandler implements Component {
    private GenericTile holder;

    @Override
    public void setHolder(GenericTile holder) {
	this.holder = holder;
    }

    protected Supplier<CompoundNBT> customPacketSupplier;
    protected Supplier<CompoundNBT> guiPacketSupplier;
    protected Consumer<CompoundNBT> customPacketConsumer;
    protected Consumer<CompoundNBT> guiPacketConsumer;

    public ComponentPacketHandler setCustomPacketSupplier(Supplier<CompoundNBT> supplier) {
	this.customPacketSupplier = supplier;
	return this;
    }

    public ComponentPacketHandler setGuiPacketSupplier(Supplier<CompoundNBT> supplier) {
	this.guiPacketSupplier = supplier;
	return this;
    }

    public ComponentPacketHandler setCustomPacketConsumer(Consumer<CompoundNBT> consumer) {
	this.customPacketConsumer = consumer;
	return this;
    }

    public ComponentPacketHandler setGuiPacketConsumer(Consumer<CompoundNBT> consumer) {
	this.guiPacketConsumer = consumer;
	return this;
    }

    public Supplier<CompoundNBT> getCustomPacketSupplier() {
	return customPacketSupplier;
    }

    public Supplier<CompoundNBT> getGuiPacketSupplier() {
	return guiPacketSupplier;
    }

    public Consumer<CompoundNBT> getCustomPacketConsumer() {
	return customPacketConsumer;
    }

    public Consumer<CompoundNBT> getGuiPacketConsumer() {
	return guiPacketConsumer;
    }

    public void sendCustomPacket() {
	PacketUpdateTile packet = new PacketUpdateTile(this, holder.getPos(), false);
	World world = holder.getWorld();
	BlockPos pos = holder.getPos();
	if (world instanceof ServerWorld) {
	    ((ServerWorld) world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
		    .forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getNetworkManager(),
			    NetworkDirection.PLAY_TO_CLIENT));
	}
    }

    public void sendGUIPacket() {
	PacketUpdateTile packet = new PacketUpdateTile(this, holder.getPos(), true);
	World world = holder.getWorld();
	BlockPos pos = holder.getPos();
	if (world instanceof ServerWorld) {
	    ((ServerWorld) world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
		    .forEach(p -> NetworkHandler.CHANNEL.sendTo(packet, p.connection.getNetworkManager(),
			    NetworkDirection.PLAY_TO_CLIENT));
	}
    }

    @Override
    public ComponentType getType() {
	return ComponentType.PacketHandler;
    }
}
