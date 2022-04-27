package electrodynamics.common.packet;

import java.util.UUID;

import electrodynamics.prefab.sound.TickableSoundJetpack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

/**
 * Apparently with packets, certain class calls cannot be called within the packet itself 
 * because Java
 * 
 * SoundInstance for example is an exclusivly client class only
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
	
}
