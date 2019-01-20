package physica.api.network;

import org.apache.commons.lang3.NotImplementedException;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public interface IPacket {

	default <T extends IPacket> T addData(Object... objects) {
		return (T) this;
	}

	void decodeInto(ChannelHandlerContext handler, ByteBuf buffer);

	void encodeInto(ChannelHandlerContext handler, ByteBuf buffer);

	@SideOnly(Side.CLIENT)
	default void handleClientSide() {
		handleClientSide(Minecraft.getMinecraft().thePlayer);
	}

	default void handleClientSide(EntityPlayer player) {
		throw new NotImplementedException("Unsupported non-implemented operation for Packet: " + getClass().getSimpleName() + " from player " + player.getDisplayName());
	}

	default void handleServerSide(EntityPlayer player) {
		throw new NotImplementedException("Unsupported non-implemented operation for Packet: " + getClass().getSimpleName() + " from player " + player.getDisplayName());
	}
}
