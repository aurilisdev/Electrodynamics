package physica.api.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public interface IPacketReciever {
	boolean read(ByteBuf buf, int id, EntityPlayer player, IPacket type);
}
