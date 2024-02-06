package electrodynamics.common.packet.types.server;

import java.util.UUID;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketUpdateCarriedItemServer implements CustomPacketPayload {

    private final ItemStack carriedItem;
    private final BlockPos tilePos;
    private final UUID playerId;

    public PacketUpdateCarriedItemServer(ItemStack carriedItem, BlockPos tilePos, UUID playerId) {
        this.carriedItem = carriedItem;
        this.tilePos = tilePos;
        this.playerId = playerId;
    }

    public static void handle(PacketUpdateCarriedItemServer message, PlayPayloadContext context) {
        ServerLevel world = (ServerLevel) context.level().get();
        if (world == null) {
            return;
        }
        GenericTile tile = (GenericTile) world.getBlockEntity(message.tilePos);
        if (tile != null) {
            tile.updateCarriedItemInContainer(message.carriedItem, message.playerId);
        }
    }

    public static PacketUpdateCarriedItemServer read(FriendlyByteBuf buf) {
        return new PacketUpdateCarriedItemServer(buf.readItem(), buf.readBlockPos(), buf.readUUID());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeItem(carriedItem);
        buf.writeBlockPos(tilePos);
        buf.writeUUID(playerId);
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_UPDATECARRIEDITEMSERVER_PACKETID;
    }

}
