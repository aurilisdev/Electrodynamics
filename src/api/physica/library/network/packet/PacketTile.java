package physica.library.network.packet;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import physica.api.core.PhysicaAPI;
import physica.library.network.IPacketReciever;
import physica.library.network.PacketBase;

public class PacketTile extends PacketBase {

	public int x;
	public int y;
	public int z;
	public int id;
	public int customInteger;

	public String name;

	public PacketTile() {
		// Required for Forge
	}

	public PacketTile(String name, int id, int x, int y, int z) {
		this.name = name;
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public PacketTile(String name, int id, int x, int y, int z, int customInteger) {
		this.name = name;
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.customInteger = customInteger;
	}

	public PacketTile(String name, int id, TileEntity tile) {
		this(name, id, tile.xCoord, tile.yCoord, tile.zCoord);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		id = buffer.readInt();
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		customInteger = buffer.readInt();
		super.decodeInto(ctx, buffer);
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(id);
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeInt(customInteger);
		super.encodeInto(ctx, buffer);
	}

	public void handle(EntityPlayer player) {
		if (player.getEntityWorld() == null) {
			if (PhysicaAPI.isDebugMode) {
				PhysicaAPI.logger.error("PacketTile#handle(" + player.getDisplayName() + ") - world is null for player while handling packet. ", new NullPointerException());
				return;
			}
		}
		if (player.getEntityWorld().blockExists(x, y, z)) {
			handle(player, player.getEntityWorld().getTileEntity(x, y, z));
		} else if (PhysicaAPI.isDebugMode) {
			PhysicaAPI.logger.error("PacketTile#handle(" + player.getDisplayName() + ") - block is not loaded for player while handling packet. ");
		}
	}

	public void handle(EntityPlayer player, TileEntity tile) {
		if (tile == null) {
			if (PhysicaAPI.isDebugMode) {
				if (FMLCommonHandler.instance().getSide().isServer()) {
					PhysicaAPI.logger.error(new RuntimeException("PacketTile#handle(" + player.getDisplayName() + ", null) - Null tile"));
				}
			}
		} else if (tile.isInvalid()) {
			if (PhysicaAPI.isDebugMode) {
				if (FMLCommonHandler.instance().getSide().isServer()) {
					PhysicaAPI.logger.error(new RuntimeException("PacketTile#handle(" + player.getDisplayName() + ", TILE[" + tile.getWorldObj().getWorldInfo().getWorldName() + ": " + tile.xCoord + ", " + tile.yCoord + ", " + tile.zCoord + "]) - Invalidated tile"));
				}
			}
		} else if (tile instanceof IPacketReciever) {
			try {
				IPacketReciever receiver = (IPacketReciever) tile;
				receiver.read(readData, id, player, this);
			} catch (IndexOutOfBoundsException e) {
				if (PhysicaAPI.isDebugMode) {
					PhysicaAPI.logger.error(new RuntimeException("PacketTile#handle(" + player.getDisplayName() + ", TILE[" + tile.getWorldObj().getWorldInfo().getWorldName() + ": " + tile.xCoord + ", " + tile.yCoord + ", " + tile.zCoord + "Packet was read past it's size."));
					PhysicaAPI.logger.error("Error: ", e);
				}
			} catch (NullPointerException e) {
				if (PhysicaAPI.isDebugMode) {
					PhysicaAPI.logger.error(new RuntimeException("PacketTile#handle(" + player.getDisplayName() + ", TILE[" + tile.getWorldObj().getWorldInfo().getWorldName() + ": " + tile.xCoord + ", " + tile.yCoord + ", " + tile.zCoord + "Null pointer while reading data", e));
					PhysicaAPI.logger.error("Error: ", e);
				}
			} catch (Exception e) {
				if (PhysicaAPI.isDebugMode) {
					PhysicaAPI.logger.error(new RuntimeException("PacketTile#handle(" + player.getDisplayName() + ", TILE[" + tile.getWorldObj().getWorldInfo().getWorldName() + ": " + tile.xCoord + ", " + tile.yCoord + ", " + tile.zCoord + "Failed to read packet", e));
					PhysicaAPI.logger.error("Error: ", e);
				}
			}
		} else if (PhysicaAPI.isDebugMode) {
			PhysicaAPI.logger.error("Error: " + tile + " rejected packet " + this + " due to invalid conditions.");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClientSide(EntityPlayer player) {
		if (player != null) {
			handle(player);
		} else if (PhysicaAPI.isDebugMode) {
			PhysicaAPI.logger.error("PacketTile#handleClientSide(null) - player was null for packet", new NullPointerException());
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if (player != null) {
			handle(player);
		} else if (PhysicaAPI.isDebugMode) {
			PhysicaAPI.logger.error("PacketTile#handleServerSide(null) - player was null for packet", new NullPointerException());
		}
	}
}
