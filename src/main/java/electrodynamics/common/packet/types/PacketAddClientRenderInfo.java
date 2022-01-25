package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.client.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketAddClientRenderInfo {

	private final UUID playerId;
	private final BlockPos pos;
	
	public PacketAddClientRenderInfo(UUID uuid, BlockPos pos) {
		this.playerId = uuid;
		this.pos = pos;
	}
	
	public static void handle(PacketAddClientRenderInfo message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			Minecraft minecraft = Minecraft.getInstance();
			ClientLevel world = minecraft.level;
			if (world != null && minecraft.player != null) {
				if(minecraft.player.getUUID().equals(message.playerId)){
					ClientEvents.addRenderLocation(message.pos);
				}
				
			}
		});
		ctx.setPacketHandled(true);
	}
	
	public static void encode(PacketAddClientRenderInfo message, FriendlyByteBuf buf) {
		buf.writeUUID(message.playerId);
		buf.writeInt(message.pos.getX());
		buf.writeInt(message.pos.getY());
		buf.writeInt(message.pos.getZ());
	}
	
	public static PacketAddClientRenderInfo decode(FriendlyByteBuf buf) {
		return new PacketAddClientRenderInfo(buf.readUUID(), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
	}
	
}
