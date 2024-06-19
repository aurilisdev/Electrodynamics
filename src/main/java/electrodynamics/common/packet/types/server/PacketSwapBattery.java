package electrodynamics.common.packet.types.server;

import java.util.UUID;

import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.packet.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketSwapBattery implements CustomPacketPayload {

    private final UUID playerId;

    public PacketSwapBattery(UUID uuid) {
        playerId = uuid;
    }

    public static void handle(PacketSwapBattery message, PlayPayloadContext context) {
        ServerLevel world = (ServerLevel) context.level().get();
        if (world == null) {
            return;
        }
        Player player = world.getPlayerByUUID(message.playerId);
        ItemStack handItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!handItem.isEmpty() && handItem.getItem() instanceof IItemElectric electric) {
            electric.swapBatteryPackFirstItem(handItem, player);
        }
    }

    public static PacketSwapBattery read(FriendlyByteBuf buf) {
        return new PacketSwapBattery(buf.readUUID());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(playerId);
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_SWAPBATTER_PACKETID;
    }

}
