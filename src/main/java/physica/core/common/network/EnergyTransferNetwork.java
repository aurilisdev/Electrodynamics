package physica.core.common.network;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import physica.core.common.block.BlockEnergyCable.EnumEnergyCable;
import physica.library.location.BlockLocation;

public class EnergyTransferNetwork {

	public HashMap<IEnergyReceiver, Set<ForgeDirection>>	receiverMap		= new HashMap<>();
	public HashSet<ITransferNode<IEnergyReceiver>>			transferNodeSet	= new HashSet<>();
	public ITransferNode<IEnergyReceiver>					ownerNode;
	public EnumEnergyCable									type;
	public boolean											isValidating;

	public int												energyBuffer	= 0;

	public EnumEnergyCable getType()
	{
		return type;
	}

	public EnergyTransferNetwork(ITransferNode<IEnergyReceiver> owner, EnumEnergyCable cable) {
		ownerNode = owner;
		type = cable;
		transferNodeSet.add(ownerNode);
		EnergyNetworkHandler.networkSet.add(this);
	}

	public void validateNetwork()
	{
		if (ownerNode == null || !ownerNode.isValid())
		{
			for (ITransferNode<IEnergyReceiver> node : transferNodeSet)
			{
				node.setTransferNetwork(null);
			}
			transferNodeSet.clear();
			receiverMap.clear();
			ownerNode = null;
		} else
		{
			transferNodeSet.clear();
			receiverMap.clear();
			transferNodeSet.add(ownerNode);
			if (!isValidating)
			{
				isValidating = true;
				EnergyNetworkHandler.queueNetworkForValidation(this);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void findNetwork(BlockLocation startLocation, ForgeDirection from)
	{
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
		{
			if (dir != from)
			{
				ForgeDirection receivedDirection = dir.getOpposite();
				BlockLocation check = startLocation.TranslateTo(dir);
				if (ownerNode != null)
				{
					TileEntity tile = check.getTile(ownerNode.getWorld());
					if (tile instanceof ITransferNode<?>)
					{
						ITransferNode<IEnergyReceiver> node = (ITransferNode<IEnergyReceiver>) tile;
						if (node.getTransferNetwork().type == type)
						{
							if (!transferNodeSet.contains(node))
							{
								transferNodeSet.add(node);
								node.setTransferNetwork(this);
								findNetwork(check, receivedDirection);
							}
						} else if (node.getTransferNetwork().type.ordinal() > type.ordinal())
						{
							IEnergyReceiver receiver = (IEnergyReceiver) node;
							if (receiverMap.containsKey(receiver))
							{
								receiverMap.get(receiver).add(receivedDirection);
							} else
							{
								HashSet<ForgeDirection> set = new HashSet<>();
								set.add(receivedDirection);
								receiverMap.put(receiver, set);
							}
						}
					} else if (tile instanceof IEnergyReceiver)
					{
						IEnergyReceiver receiver = (IEnergyReceiver) tile;
						if (receiver.canConnectEnergy(receivedDirection) && receiver.receiveEnergy(receivedDirection, 10, true) > 0)
						{
							if (receiverMap.containsKey(receiver))
							{
								receiverMap.get(receiver).add(receivedDirection);
							} else
							{
								HashSet<ForgeDirection> set = new HashSet<>();
								set.add(receivedDirection);
								receiverMap.put(receiver, set);
							}
						}
					}
				}
			}
		}
	}

	public int receiveAndDistributeEnergy(int maxEnergy, ITransferNode<IEnergyReceiver> sender, boolean simulate)
	{
		int calculatedVoltage = type.getVoltage() > 0 ? maxEnergy / (type.getTransferRate() / type.getVoltage()) : -10;
		if (calculatedVoltage < type.getVoltage())
		{
			HashMap<IEnergyReceiver, ForgeDirection> cached = new HashMap<>();
			int size = 0;
			Iterator<IEnergyReceiver> it = receiverMap.keySet().iterator();
			while (it.hasNext())
			{
				IEnergyReceiver next = it.next();

				if (!((TileEntity) next).isInvalid())
				{
					Iterator<ForgeDirection> connectedIterator = receiverMap.get(next).iterator();
					while (connectedIterator.hasNext())
					{
						ForgeDirection check = connectedIterator.next();
						if (next.canConnectEnergy(check))
						{
							if (next.getEnergyStored(check) < next.getMaxEnergyStored(check))
							{
								cached.put(next, check);
								size++;
							}
						} else
						{
							it.remove();
						}
					}
				} else
				{
					it.remove();
				}
			}
			int totalReceived = 0;
			if (size > 0)
			{
				;
			}
			{
				maxEnergy = Math.min(getTransferRate(), maxEnergy);
				if (maxEnergy > 0)
				{
					int perEnergy = size == 0 ? 0 : maxEnergy / size;

					for (IEnergyReceiver receiver : cached.keySet())
					{
						totalReceived += receiver.receiveEnergy(cached.get(receiver), perEnergy, simulate);
					}
				}
			}
			energyBuffer += totalReceived;
			return totalReceived;
		} else
		{
			for (ITransferNode<IEnergyReceiver> node : transferNodeSet)
			{
				node.destroyNode();
			}
			return 0;
		}
	}

	public int getVisualTransferRate()
	{
		return Math.max(0, getType().getTransferRate() * transferNodeSet.size());
	}

	public int getTransferRate()
	{
		return Math.max(0, getType().getTransferRate() * transferNodeSet.size() - energyBuffer);
	}

	public void nextTick()
	{
		energyBuffer = 0;
	}
}
