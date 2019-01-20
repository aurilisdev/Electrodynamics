package physica.library.network.netty;

import java.util.EnumMap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.api.core.IContent;
import physica.api.core.PhysicaAPI;
import physica.library.network.IPacket;

/**
 * Modified to work with Physica
 *
 * @author tgame14
 * @since 26/05/14
 */
public class PacketSystem implements IContent {

	public static PacketSystem INSTANCE = new PacketSystem("physicapacketsystem");

	public final String channel;
	protected EnumMap<Side, FMLEmbeddedChannel> channelEnumMap;

	public PacketSystem(String channel) {
		this.channel = channel;
	}

	@Override
	public void init() {
		channelEnumMap = NetworkRegistry.INSTANCE.newChannel(channel, new PacketEncoderDecoderHandler(), new PacketChannelInboundHandler());
	}

	/**
	 * sends to all clients connected to the server
	 *
	 * @param packet
	 *            the packet to send.
	 */
	public void sendToAll(IPacket packet) {
		// Null check is for JUnit
		if (channelEnumMap != null) {
			channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
			channelEnumMap.get(Side.SERVER).writeAndFlush(packet);
		} else {
			PhysicaAPI.logger.error("Packet sent to all");
		}
	}

	public void sendToAllAround(IPacket message, NetworkRegistry.TargetPoint point) {
		// Null check is for JUnit
		if (channelEnumMap != null) {
			channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
			channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
			channelEnumMap.get(Side.SERVER).writeAndFlush(message);
		} else {
			PhysicaAPI.logger.error("Packet sent to target point: " + point);
		}
	}

	public void sendToAllAround(IPacket message, TileEntity tile) {
		sendToAllAround(message, tile, 64);
	}

	public void sendToAllAround(IPacket message, TileEntity tile, double range) {
		sendToAllAround(message, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, range);
	}

	public void sendToAllAround(IPacket message, World world, double x, double y, double z, double range) {
		if (world != null) {
			sendToAllAround(message, new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, range));
		}
	}

	/**
	 * @param packet
	 *            the packet to send to the players in the dimension
	 * @param dimId
	 *            the dimension ID to send to.
	 */
	public void sendToAllInDimension(IPacket packet, int dimId) {
		// Null check is for JUnit
		if (channelEnumMap != null) {
			channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
			channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimId);
			channelEnumMap.get(Side.SERVER).writeAndFlush(packet);
		} else {
			PhysicaAPI.logger.error("Packet sent to dim[" + dimId + "]");
		}
	}

	public void sendToAllInDimension(IPacket packet, World world) {
		sendToAllInDimension(packet, world.provider.dimensionId);
	}

	/**
	 * @param packet
	 *            the packet to send to the player
	 * @param player
	 *            the player MP object
	 */
	public void sendToPlayer(IPacket packet, EntityPlayerMP player) {
		// Null check is for JUnit
		if (channelEnumMap != null) {
			channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
			channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
			channelEnumMap.get(Side.SERVER).writeAndFlush(packet);
		} else {
			PhysicaAPI.logger.error("Packet sent to player[" + player + "]");
		}
	}

	public void sendToServer(IPacket packet) {
		// Null check is for JUnit
		if (channelEnumMap != null) {
			if (channelEnumMap.get(Side.CLIENT) != null) {
				channelEnumMap.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
				channelEnumMap.get(Side.CLIENT).writeAndFlush(packet);
			} else {
				PhysicaAPI.logger.error("PacketManager#sendToServer(packet): Attempted to fire client to server packet on server, this is not allowed. Packet = " + packet);
			}
		} else {
			PhysicaAPI.logger.error("PacketManager#sendToServer(packet): Channel enum map is empty, can't send packet. Packet = " + packet);
		}
	}

	public Packet toMCPacket(IPacket packet) {
		return channelEnumMap.get(FMLCommonHandler.instance().getEffectiveSide()).generatePacketFrom(packet);
	}
}
