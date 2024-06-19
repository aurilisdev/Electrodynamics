package electrodynamics.common.packet.types.server;

import java.util.UUID;

import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.item.gear.armor.types.ItemNightVisionGoggles;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketToggleOnServer implements CustomPacketPayload {

    private final UUID playerId;
    private final Type type;

    public PacketToggleOnServer(UUID uuid, Type type) {
        playerId = uuid;
        this.type = type;
    }

    public static void handle(PacketToggleOnServer message, PlayPayloadContext context) {
        ServerLevel world = (ServerLevel) context.level().get();
        if (world == null) {
            return;
        }
        Player player = world.getPlayerByUUID(message.playerId);
        switch (message.type) {
        case NVGS:
            ItemStack playerHead = player.getItemBySlot(EquipmentSlot.HEAD);
            if (ItemUtils.testItems(playerHead.getItem(), ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES.get()) || ItemUtils.testItems(playerHead.getItem(), ElectrodynamicsItems.ITEM_COMBATHELMET.get())) {
                CompoundTag tag = playerHead.getOrCreateTag();
                tag.putBoolean(NBTUtils.ON, !tag.getBoolean(NBTUtils.ON));
                if (((IItemElectric) playerHead.getItem()).getJoulesStored(playerHead) >= ItemNightVisionGoggles.JOULES_PER_TICK) {
                    if (tag.getBoolean(NBTUtils.ON)) {
                        player.playNotifySound(ElectrodynamicsSounds.SOUND_NIGHTVISIONGOGGLESON.get(), SoundSource.PLAYERS, 1, 1);
                    } else {
                        player.playNotifySound(ElectrodynamicsSounds.SOUND_NIGHTVISIONGOGGLESOFF.get(), SoundSource.PLAYERS, 1, 1);
                    }
                }
            }
            break;
        case SERVOLEGGINGS:
            ItemStack playerLegs = player.getItemBySlot(EquipmentSlot.LEGS);
            if (ItemUtils.testItems(playerLegs.getItem(), ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get()) || ItemUtils.testItems(playerLegs.getItem(), ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get())) {
                CompoundTag tag = playerLegs.getOrCreateTag();
                tag.putBoolean(NBTUtils.ON, !tag.getBoolean(NBTUtils.ON));
                player.playNotifySound(ElectrodynamicsSounds.SOUND_JETPACKSWITCHMODE.get(), SoundSource.PLAYERS, 1, 1);
            }
            break;
        default:
            break;
        }
    }

    public static PacketToggleOnServer read(FriendlyByteBuf buf) {
        return new PacketToggleOnServer(buf.readUUID(), buf.readEnum(Type.class));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(playerId);
        buf.writeEnum(type);
    }

    @Override
    public ResourceLocation id() {
        return NetworkHandler.PACKET_TOGGLEONSERVER_PACKETID;
    }

    public enum Type {
        NVGS,
        SERVOLEGGINGS;
    }

}
