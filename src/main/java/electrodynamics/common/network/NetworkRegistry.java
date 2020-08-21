package electrodynamics.common.network;

import java.util.HashSet;
import java.util.Set;

import electrodynamics.api.References;
import electrodynamics.api.network.ITickableNetwork;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class NetworkRegistry {
	private static HashSet<ITickableNetwork> networks = new HashSet<>();

	public static void register(ITickableNetwork network) {
		networks.add(network);
	}

	public static void deregister(ITickableNetwork network) {
		if (networks.contains(network)) {
			networks.remove(network);
		}
	}

	public static void pruneEmptyNetworks() {
		for (ITickableNetwork e : new HashSet<>(networks)) {
			if (e.getSize() == 0) {
				deregister(e);
			}
		}
	}

	@SubscribeEvent
	public static void update(ServerTickEvent event) {
		if (event.phase == Phase.END) {
			@SuppressWarnings("unchecked")
			Set<ITickableNetwork> iterNetworks = (Set<ITickableNetwork>) networks.clone();
			for (ITickableNetwork net : iterNetworks) {
				if (networks.contains(net)) {
					net.tick();
				}
			}
		}
	}

}