package electrodynamics.common.packet.types;

import java.util.UUID;
import java.util.function.Supplier;

import electrodynamics.prefab.sound.TickableSoundJetpack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketJetpackEquipedSound {
	
	private final UUID player;
	
	public PacketJetpackEquipedSound(UUID uuid) {
		player = uuid;
	}
	
	public static void handle(PacketJetpackEquipedSound message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			Minecraft minecraft = Minecraft.getInstance();
			ClientLevel world = minecraft.level;
			if (world != null && minecraft.player != null) {
				Player origin = minecraft.level.getPlayerByUUID(message.player);
				if(origin != null) {
					minecraft.getSoundManager().play(new TickableSoundJetpack(origin));
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketJetpackEquipedSound message, FriendlyByteBuf buf) {
		buf.writeUUID(message.player);
	}

	public static PacketJetpackEquipedSound decode(FriendlyByteBuf buf) {
		return new PacketJetpackEquipedSound(buf.readUUID());
	}

}
