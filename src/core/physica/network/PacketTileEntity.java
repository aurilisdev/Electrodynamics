package physica.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import physica.Physica;
import physica.tile.ITileNetwork;

/**
 * This packet i used by tile tile entities to send custom information from
 * server to client.
 */
public class PacketTileEntity extends PacketLocation {
	private List<Object> objects;
	private ByteBuf storedBuffer = null;

	public PacketTileEntity() {

	}

	public PacketTileEntity(final BlockPos pos, final List<Object> objects) {
		super(pos);

		this.objects = objects;
	}

	public <T extends TileEntity & ITileNetwork> PacketTileEntity(T tile) {
		this(tile.getPos(), tile.getPacketData(new ArrayList<>()));
	}

	@Override
	public void fromBytes(final ByteBuf dataStream) {
		super.fromBytes(dataStream);

		storedBuffer = dataStream.copy();
	}

	@Override
	public void toBytes(final ByteBuf dataStream) {
		super.toBytes(dataStream);

		PacketHandler.writeObjects(objects, dataStream);
	}

	public static class PacketTileEntityMessage implements IMessageHandler<PacketTileEntity, IMessage> {
		@Override
		public IMessage onMessage(final PacketTileEntity message, final MessageContext messageContext) {
			final World world = PacketHandler.getWorld(messageContext);

			Physica.proxy.addScheduledTask(() -> {
				final TileEntity tile = world.getTileEntity(message.getPos());

				if (tile instanceof ITileNetwork) {
					final ITileNetwork tileNetwork = (ITileNetwork) tile;

					try {
						tileNetwork.handlePacketData(message.storedBuffer);
					} catch (Exception e) {
						e.printStackTrace();
					}

					message.storedBuffer.release();
				}
			}, world);

			return null;
		}
	}
}