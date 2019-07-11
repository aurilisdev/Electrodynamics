package physica.library.net;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import physica.library.net.energy.EnergyNetwork;

public class EnergyNetworkRegistry {
	public static EnergyNetworkRegistry	INSTANCE	= new EnergyNetworkRegistry();
	private HashSet<EnergyNetwork>		networks	= new HashSet<>();

	public static EnergyNetworkRegistry getInstance()
	{
		return INSTANCE;
	}

	public void registerNetwork(EnergyNetwork network)
	{
		networks.add(network);
	}

	public void removeNetwork(EnergyNetwork network)
	{
		if (networks.contains(network))
		{
			networks.remove(network);
		}
	}

	public void pruneEmptyNetworks()
	{
		for (EnergyNetwork e : networks)
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
			Set<EnergyNetwork> iterNetworks = (Set<EnergyNetwork>) networks.clone();
			for (EnergyNetwork net : iterNetworks)
			{
				if (networks.contains(net))
				{
					net.tick();
				}
			}
		}
	}

}
