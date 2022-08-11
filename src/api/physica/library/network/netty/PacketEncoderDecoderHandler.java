package physica.library.network.netty;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import physica.api.core.PhysicaAPI;
import physica.library.network.IPacket;
import physica.library.network.packet.PacketTile;

/**
 * Modified to work with Physica
 *
 * @author tgame14
 * @since 31/05/14
 */
public class PacketEncoderDecoderHandler extends FMLIndexedMessageToMessageCodec<IPacket> {

	public boolean silenceStackTrace = false;
	private int nextID = 0;

	public PacketEncoderDecoderHandler() {
		addPacket(PacketTile.class);
	}

	public void addPacket(Class<? extends IPacket> clazz) {
		addDiscriminator(nextID++, clazz);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, IPacket packet) {
		try {
			packet.decodeInto(ctx, source);
		} catch (Exception e) {
			if (!silenceStackTrace) {
				PhysicaAPI.logger.error("Failed to decode packet " + packet, e);
			} else {
				PhysicaAPI.logger.error("Failed to decode packet " + packet + " Exception: " + e.getMessage());
			}
		}
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, IPacket packet, ByteBuf target) throws Exception {
		try {
			packet.encodeInto(ctx, target);
		} catch (Exception e) {
			if (!silenceStackTrace) {
				PhysicaAPI.logger.error("Failed to encode packet " + packet, e);
			} else {
				PhysicaAPI.logger.error("Failed to encode packet " + packet + " Exception: " + e.getMessage());
			}
		}
	}
}
