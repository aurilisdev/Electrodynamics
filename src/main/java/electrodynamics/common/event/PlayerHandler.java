package electrodynamics.common.event;

import electrodynamics.api.References;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.PacketPlayerInformation;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class PlayerHandler {

	@SubscribeEvent
	public static void tick(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT && event.player.level.getLevelData().getDayTime() % 50 == 10) {
			NetworkHandler.CHANNEL.sendToServer(new PacketPlayerInformation());
		}
	}
}
