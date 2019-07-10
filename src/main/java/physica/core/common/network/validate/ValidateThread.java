package physica.core.common.network.validate;

import java.util.concurrent.ConcurrentLinkedQueue;

import cofh.api.energy.IEnergyReceiver;
import net.minecraftforge.common.util.ForgeDirection;
import physica.core.common.network.EnergyTransferNetwork;
import physica.core.common.network.ITransferNode;

public class ValidateThread extends Thread {

	public ConcurrentLinkedQueue<EnergyTransferNetwork> queue = new ConcurrentLinkedQueue<>();

	@Override
	public void run()
	{
		while (!isInterrupted())
		{
			if (!queue.isEmpty())
			{
				EnergyTransferNetwork network = queue.element();
				if (network.ownerNode != null)
				{
					network.isValidating = true;
					for (ITransferNode<IEnergyReceiver> node : network.transferNodeSet)
					{
						if (node != network.ownerNode)
						{
							node.setTransferNetwork(null);
						}
					}
					network.transferNodeSet.clear();
					network.receiverMap.clear();
					network.transferNodeSet.add(network.ownerNode);
					EnergyTransferNetwork.findNetwork(network, network.ownerNode.getNodeLocation(), ForgeDirection.UNKNOWN, network.ownerNode.getWorld(), network.getType(), network.transferNodeSet, network.receiverMap);
				}
				queue.remove();
				network.isValidating = false;
			} else
			{
				try
				{
					sleep(25);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
					interrupt();
				}
			}
		}
	}
}
