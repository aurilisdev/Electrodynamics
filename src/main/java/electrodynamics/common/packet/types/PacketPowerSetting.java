package electrodynamics.common.packet.types;

import java.util.function.Supplier;

import electrodynamics.common.tile.TileCreativePowerSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketPowerSetting {

	private final int voltage;
	private final int power;

	private final BlockPos pos;

	public PacketPowerSetting(int voltage, int power, BlockPos target) {
		this.voltage = voltage;
		this.power = power;
		pos = target;
	}

	public static void handle(PacketPowerSetting message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ServerLevel world = context.get().getSender().getLevel();
			if (world != null) {
				TileCreativePowerSource tile = (TileCreativePowerSource) world.getBlockEntity(message.pos);
				if (tile != null) {
					tile.voltage.set(message.voltage);
					tile.power.set(message.power);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketPowerSetting pkt, FriendlyByteBuf buf) {
		buf.writeInt(pkt.voltage);
		buf.writeInt(pkt.power);
		buf.writeBlockPos(pkt.pos);
	}

	public static PacketPowerSetting decode(FriendlyByteBuf buf) {
		return new PacketPowerSetting(buf.readInt(), buf.readInt(), buf.readBlockPos());
	}

}
