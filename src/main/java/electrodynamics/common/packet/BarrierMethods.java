package electrodynamics.common.packet;

import java.util.HashSet;
import java.util.UUID;

import electrodynamics.common.item.gear.armor.types.ItemCombatArmor;
import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.prefab.sound.TickableSoundJetpack;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;

/**
 * Apparently with packets, certain class calls cannot be called within the packet itself because Java
 * 
 * SoundInstance for example is an exclusively client class only
 * 
 * Place methods that need to use those here
 */
public class BarrierMethods {

	public static void handlePacketJetpackEquipedSound(UUID messageId) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel world = minecraft.level;
		if (world != null) {
			minecraft.getSoundManager().play(new TickableSoundJetpack(messageId));
		}
	}

	public static void handleJetpackParticleRendering(UUID player, boolean bool) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel world = minecraft.level;
		if (world != null) {
			Player clientPlayer = minecraft.player;
			if (clientPlayer != null && !clientPlayer.getUUID().equals(player)) {
				Player ownerPlayer = world.getPlayerByUUID(player);
				if (ownerPlayer != null) {
					ItemJetpack.renderClientParticles(world, ownerPlayer, bool ? ItemCombatArmor.OFFSET : ItemJetpack.OFFSET);
				}
			}
		}
	}

	public static void handlerClientCombustionFuels(HashSet<CombustionFuelSource> fuels) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level != null && minecraft.player != null) {
			CombustionFuelRegister.INSTANCE.setClientValues(fuels);
		}
	}

}
