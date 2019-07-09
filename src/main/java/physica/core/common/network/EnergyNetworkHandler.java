package physica.core.common.network;

import java.util.HashSet;
import java.util.Iterator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import physica.core.common.network.validate.ValidateThread;

public class EnergyNetworkHandler {

	public static final EnergyNetworkHandler INSTANCE = new EnergyNetworkHandler();

	public static HashSet<EnergyTransferNetwork> networkSet = new HashSet<>();
	public static ValidateThread thread = new ValidateThread();

	@SubscribeEvent
	public void update(ServerTickEvent event)
	{
		if (thread == null || !thread.isAlive())
		{
			ValidateThread oldThread = thread;
			thread = new ValidateThread();
			if (!oldThread.isAlive())
			{
				thread.queue = oldThread.queue;
			}

			thread.start();
		}
		if (event.phase == Phase.START)
		{
			Iterator<EnergyTransferNetwork> it = networkSet.iterator();
			while (it.hasNext())
			{
				EnergyTransferNetwork network = it.next();
				if (network.ownerNode == null)
				{
					it.remove();
					continue;
				} else
				{
					network.nextTick();
				}
			}
		}
	}

	public static void queueNetworkForValidation(EnergyTransferNetwork energyTransferNetwork)
	{
		thread.queue.add(energyTransferNetwork);
	}
}
