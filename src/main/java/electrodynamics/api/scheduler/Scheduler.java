package electrodynamics.api.scheduler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import electrodynamics.api.References;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class Scheduler {
    private static HashMap<Runnable, Integer> scheduled = new HashMap<>();

    @SubscribeEvent
    public static void onChunkLoad(ServerTickEvent event) {
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

    public static void schedule(int timeUntil, Runnable run) {
	scheduled.put(run, timeUntil);
    }
}