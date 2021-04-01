package electrodynamics.common.network;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import electrodynamics.api.References;
import electrodynamics.api.network.AbstractNetwork;
import electrodynamics.api.network.IAbstractConductor;
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
	    try {
		Iterator<ITickableNetwork> it = networks.iterator();
		while (it.hasNext()) {
		    ITickableNetwork net = it.next();
		    if (net instanceof AbstractNetwork<?, ?, ?, ?>) {
			AbstractNetwork<?, ?, ?, ?> abs = (AbstractNetwork<?, ?, ?, ?>) net;
			Iterator<?> it2 = abs.conductorSet.iterator();
			while (it2.hasNext()) {
			    IAbstractConductor conductor = (IAbstractConductor) it2.next();
			    if (conductor.getAbstractNetwork() != net) {
				it2.remove();
			    }
			}
		    }
		    if (net.getSize() == 0) {
			it.remove();
		    }
		}
		Set<ITickableNetwork> iterNetworks = (Set<ITickableNetwork>) networks.clone();
		for (ITickableNetwork net : iterNetworks) {
		    if (networks.contains(net)) {
			net.tick();
		    }
		}
		pruneEmptyNetworks();
	    } catch (ConcurrentModificationException exception) {
		exception.printStackTrace();
	    }
	}
    }

}