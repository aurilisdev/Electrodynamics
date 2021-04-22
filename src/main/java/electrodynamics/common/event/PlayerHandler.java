package electrodynamics.common.event;

import electrodynamics.api.References;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.PacketPlayerInformation;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class PlayerHandler {
    private static int last = 0;

    @SubscribeEvent
    public static void tick(ServerTickEvent event) {
	if (event.side == LogicalSide.CLIENT && last++ > 50) {
	    last = 0;
	    NetworkHandler.CHANNEL.sendToServer(new PacketPlayerInformation());
	}
    }
}
