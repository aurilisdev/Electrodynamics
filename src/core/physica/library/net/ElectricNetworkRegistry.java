package physica.library.net;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import physica.library.net.energy.ElectricNetwork;

public class ElectricNetworkRegistry {
	public static ElectricNetworkRegistry	INSTANCE	= new ElectricNetworkRegistry();
	private HashSet<ElectricNetwork>		networks	= new HashSet<>();

	public static ElectricNetworkRegistry getInstance()
	{
		return INSTANCE;
	}

	public void registerNetwork(ElectricNetwork network)
	{
		networks.add(network);
	}

	public void removeNetwork(ElectricNetwork network)
	{
		if (networks.contains(network))
		{
			networks.remove(network);
		}
	}

	public void pruneEmptyNetworks()
	{
		for (ElectricNetwork e : networks)
		{
			if (e.conductorSet.size() == 0)
			{
				removeNetwork(e);
			}
		}
	}

	@SubscribeEvent
	public void update(ServerTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			@SuppressWarnings("unchecked")
			Set<ElectricNetwork> iterNetworks = (Set<ElectricNetwork>) networks.clone();
			for (ElectricNetwork net : iterNetworks)
			{
				if (networks.contains(net))
				{
					net.tick();
				}
			}
		}
	}

}
