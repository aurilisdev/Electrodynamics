package physica.core.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * This is a packet that provides a location, and is meant to be extended.
 */
public abstract class PacketLocation implements IMessage {
	private BlockPos pos;

	public PacketLocation() {

	}

	public PacketLocation(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf dataStream) {
		pos = new BlockPos(dataStream.readInt(), dataStream.readInt(), dataStream.readInt());
	}

	@Override
	public void toBytes(ByteBuf dataStream) {
		dataStream.writeInt(pos.getX());
		dataStream.writeInt(pos.getY());
		dataStream.writeInt(pos.getZ());
	}

	public BlockPos getPos() {
		return pos;
	}
}