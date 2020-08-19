package electrodynamics.common.electricity.network;

import java.util.HashSet;
import java.util.Set;

import electrodynamics.References;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class ElectricNetworkRegistry {
	private static HashSet<ElectricNetwork> networks = new HashSet<>();

	public static void register(ElectricNetwork network) {
		networks.add(network);
	}

	public static void deregister(ElectricNetwork network) {
		if (networks.contains(network)) {
			networks.remove(network);
		}
	}

	public static void pruneEmptyNetworks() {
		for (ElectricNetwork e : new HashSet<>(networks)) {
			if (e.conductorSet.size() == 0) {
				deregister(e);
			}
		}
	}

	@SubscribeEvent
	public static void update(ServerTickEvent event) {
		if (event.phase == Phase.END) {
			@SuppressWarnings("unchecked")
			Set<ElectricNetwork> iterNetworks = (Set<ElectricNetwork>) networks.clone();
			for (ElectricNetwork net : iterNetworks) {
				if (networks.contains(net)) {
					net.tick();
				}
			}
		}
	}

}