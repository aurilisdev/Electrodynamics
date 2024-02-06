package electrodynamics.common.packet.types.server;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.tile.electricitygrid.generators.TileCreativePowerSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketPowerSetting implements CustomPacketPayload {

    private final int voltage;
    private final int power;

    private final BlockPos pos;

    public PacketPowerSetting(int voltage, int power, BlockPos target) {
        this.voltage = voltage;
        this.power = power;
        pos = target;
    }

    public static void handle(PacketPowerSetting message, PlayPayloadContext context) {
        ServerLevel world = (ServerLevel) context.level().get();
        if (world == null) {
            return;
        }
        TileCreativePowerSource tile = (TileCreativePowerSource) world.getBlockEntity(message.pos);
        if (tile == null) {
            return;
        }
        tile.voltage.set(message.voltage);
        tile.power.set(message.power);
    }

    public static PacketPowerSetting read(FriendlyByteBuf buf) {
        return new PacketPowerSetting(buf.readInt(), buf.readInt(), buf.readBlockPos());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(voltage);
        buf.writeInt(power);
        buf.writeBlockPos(pos);
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_POWERSETTING_PACKETID;
    }

}
