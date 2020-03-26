package physica.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;

public interface ITileNetwork {
	void handlePacketData(ByteBuf dataStream);

	List<Object> getPacketData(List<Object> objects);
}
