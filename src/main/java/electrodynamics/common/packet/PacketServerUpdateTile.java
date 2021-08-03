package electrodynamics.common.packet;

import java.util.function.Supplier;

import electrodynamics.api.tile.IPacketServerUpdateTile;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketServerUpdateTile {
    private final BlockPos target;
    private final CompoundNBT nbt;

    public PacketServerUpdateTile(CompoundNBT nbt, BlockPos target) {
	this.nbt = nbt;
	this.target = target;
    }

    public static void handle(PacketServerUpdateTile message, Supplier<Context> context) {
	Context ctx = context.get();
	ctx.enqueueWork(() -> {
	    ServerWorld world = context.get().getSender().getServerWorld();
	    if (world != null) {
		TileEntity tile = world.getTileEntity(message.target);
		if (tile instanceof IPacketServerUpdateTile) {
		    ((IPacketServerUpdateTile) tile).readCustomUpdate(message.nbt);
		}
	    }
	});
	ctx.setPacketHandled(true);
    }

    public static void encode(PacketServerUpdateTile pkt, PacketBuffer buf) {
	buf.writeCompoundTag(pkt.nbt);
	buf.writeBlockPos(pkt.target);
    }

    public static PacketServerUpdateTile decode(PacketBuffer buf) {
	return new PacketServerUpdateTile(buf.readCompoundTag(), buf.readBlockPos());
    }
}