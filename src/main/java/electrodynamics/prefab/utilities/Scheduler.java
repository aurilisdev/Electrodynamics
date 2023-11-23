package electrodynamics.prefab.utilities;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import electrodynamics.api.References;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class Scheduler {
	private static ConcurrentHashMap<Runnable, Integer> scheduled = new ConcurrentHashMap<>();

	@SubscribeEvent
	public static void onTick(ServerTickEvent event) {
		if (!scheduled.isEmpty()) {
			Iterator<Entry<Runnable, Integer>> it = scheduled.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Runnable, Integer> next = it.next();
				if (next.getValue() <= 0) {
					next.getKey().run();
					it.remove();
				} else {
					next.setValue(next.getValue() - 1);
				}
			}
		}
	}

	public static void schedule(int timeUntil, Runnable run) {
		scheduled.put(run, timeUntil);
	}
}