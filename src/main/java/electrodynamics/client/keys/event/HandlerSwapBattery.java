package electrodynamics.client.keys.event;

import electrodynamics.api.item.IItemElectric;
import electrodynamics.client.keys.KeyBinds;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.server.PacketSwapBattery;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;

public class HandlerSwapBattery extends AbstractKeyPressHandler {

	@Override
	public void handler(KeyInputEvent event, Minecraft minecraft) {
		Player player = minecraft.player;
		if (KeyBinds.swapBattery.matches(event.getKey(), event.getScanCode()) && KeyBinds.swapBattery.isDown()) {
			ItemStack playerHand = player.getItemInHand(InteractionHand.MAIN_HAND);
			if (playerHand.getItem() instanceof IItemElectric) {
				NetworkHandler.CHANNEL.sendToServer(new PacketSwapBattery(player.getUUID()));
			}
		}
	}

}