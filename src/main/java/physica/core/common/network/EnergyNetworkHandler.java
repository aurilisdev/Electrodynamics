package physica.core.common.network;

import java.util.HashSet;
import java.util.Iterator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class EnergyNetworkHandler {

	public static final EnergyNetworkHandler INSTANCE = new EnergyNetworkHandler();

	public static HashSet<EnergyTransferNetwork> networkSet = new HashSet<>();

	@SubscribeEvent
	public void update(ServerTickEvent event)
	{
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
}
