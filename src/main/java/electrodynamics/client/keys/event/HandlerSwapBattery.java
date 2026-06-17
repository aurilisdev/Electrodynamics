package electrodynamics.client.keys.event;

import electrodynamics.client.keys.KeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent.Key;
import voltaic.api.item.IItemElectric;
import voltaic.client.event.AbstractKeyPressHandler;
import voltaic.common.packet.NetworkHandler;
import voltaic.common.packet.types.server.PacketSwapBattery;

public class HandlerSwapBattery extends AbstractKeyPressHandler {

    @Override
    public void handler(Key event, Minecraft minecraft) {
	Player player = minecraft.player;
	if (KeyBinds.swapBattery.matches(event.getKey(), event.getScanCode()) && KeyBinds.swapBattery.isDown()) {
	    ItemStack playerHand = player.getItemInHand(InteractionHand.MAIN_HAND);
	    if (playerHand.getItem() instanceof IItemElectric) {
		NetworkHandler.CHANNEL.sendToServer(new PacketSwapBattery(player.getUUID()));
	    }
	}
    }

}
