package electrodynamics.common.network;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;

import electrodynamics.api.References;
import electrodynamics.api.network.ITickableNetwork;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.TickEvent.Phase;
import net.neoforged.neoforge.event.TickEvent.ServerTickEvent;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class NetworkRegistry {
	private static final HashSet<ITickableNetwork> networks = new HashSet<>();
	private static final HashSet<ITickableNetwork> remove = new HashSet<>();

	public static void register(ITickableNetwork network) {
		networks.add(network);
	}

	public static void deregister(ITickableNetwork network) {
		if (networks.contains(network)) {
			remove.add(network);
		}
	}

	@SubscribeEvent
	public static void update(ServerTickEvent event) {
		if (event.phase != Phase.END) {
			return;
		}

		try {
			networks.removeAll(remove);
			remove.clear();
			Iterator<ITickableNetwork> it = networks.iterator();
			while (it.hasNext()) {
				ITickableNetwork net = it.next();
				if (net.getSize() == 0) {
					deregister(net);
				} else {
					net.tick();
				}
			}
		} catch (ConcurrentModificationException exception) {
			exception.printStackTrace();
		}
	}

}