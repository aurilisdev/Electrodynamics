package physica.library.net.energy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.ChunkEvent;
import physica.api.core.cable.IConductor;
import physica.library.location.BlockLocation;
import physica.library.net.EnergyNetworkRegistry;

public class EnergyNetwork {
	public HashSet<IConductor>				conductorSet				= new HashSet<>();
	public Set<TileEntity>					acceptorSet					= new HashSet<>();
	public Map<TileEntity, ForgeDirection>	acceptorInputMap			= new HashMap<>();
	private int								energyTransmittedBuffer		= 0;
	private int								energyTransmittedLastTick	= 0;				// TODO: Work on implementing voltage. Is always an concurrent exception???
	private int								ticksSinceNetworkCreate		= 0;
	private boolean							fixed						= false;
	private int								maxPowerRate				= 0;

	public EnergyNetwork(IConductor... varCables) {
		conductorSet.addAll(Arrays.asList(varCables));
		EnergyNetworkRegistry.getInstance().registerNetwork(this);
	}

	public EnergyNetwork(Set<EnergyNetwork> networks) {
		for (EnergyNetwork net : networks)
		{
			if (net != null)
			{
				addAllCables(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		EnergyNetworkRegistry.getInstance().registerNetwork(this);
	}

	public int emit(int energyToSend, ArrayList<TileEntity> ignored)
	{
		energyToSend = Math.min(maxPowerRate - energyTransmittedBuffer, energyToSend);
		if (energyToSend > 0)
		{
			List<Object> availableAcceptors = Arrays.asList(getEnergyAcceptors().toArray());

			Collections.shuffle(availableAcceptors);
			int energySent = 0;
			if (!availableAcceptors.isEmpty())
			{
				int divider = availableAcceptors.size();
				int remaining = energyToSend % divider;
				int sending = (energyToSend - remaining) / divider;
				for (Object obj : availableAcceptors)
				{
					if (obj instanceof TileEntity && !ignored.contains(obj))
					{
						TileEntity acceptor = (TileEntity) obj;
						int currentSending = sending + remaining;

						remaining = 0;
						if (acceptor instanceof IEnergyReceiver)
						{
							energySent += currentSending - ((IEnergyReceiver) acceptor).receiveEnergy(acceptorInputMap.get(acceptor).getOpposite(), currentSending, false);
						}
					}
				}
			}
			energyTransmittedBuffer += energySent;
			return energySent;
		}
		return 0;
	}

	public Set<TileEntity> getEnergyAcceptors()
	{
		Set<TileEntity> toReturn = new HashSet<>();
		for (TileEntity acceptor : acceptorSet)
		{
			if (acceptor instanceof IEnergyReceiver)
			{
				IEnergyReceiver receiver = (IEnergyReceiver) acceptor;
				ForgeDirection direction = acceptorInputMap.get(acceptor).getOpposite();
				if (receiver.canConnectEnergy(direction))
				{
					if (receiver.receiveEnergy(direction, Integer.MAX_VALUE, true) > 0)
					{
						toReturn.add(acceptor);
					}
				}
			}
		}
		return toReturn;
	}

	public TileEntity[] getConnectedEnergyAcceptors(TileEntity tileEntity)
	{
		TileEntity[] acceptors = { null, null, null, null, null, null };
		for (ForgeDirection orientation : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity acceptor = new BlockLocation(tileEntity).TranslateTo(orientation).getTile(tileEntity.getWorldObj());
			if (acceptor instanceof IEnergyReceiver && !(acceptor instanceof IConductor))
			{
				acceptors[orientation.ordinal()] = acceptor;
			}
		}
		return acceptors;
	}

	public void refresh()
	{
		@SuppressWarnings("unchecked")
		Set<IConductor> iterCables = (Set<IConductor>) conductorSet.clone();
		Iterator<IConductor> it = iterCables.iterator();

		acceptorSet.clear();
		acceptorInputMap.clear();
		while (it.hasNext())
		{
			IConductor conductor = it.next();
			if (conductor == null || ((TileEntity) conductor).isInvalid())
			{
				it.remove();
				conductorSet.remove(conductor);
			} else
			{
				conductor.setNetwork(this);
			}
		}
		for (IConductor cable : iterCables)
		{
			TileEntity[] acceptors = getConnectedEnergyAcceptors((TileEntity) cable);
			for (TileEntity acceptor : acceptors)
			{
				if (acceptor != null && !(acceptor instanceof IConductor))
				{
					acceptorSet.add(acceptor);
					acceptorInputMap.put(acceptor, ForgeDirection.getOrientation(Arrays.asList(acceptors).indexOf(acceptor)));
				}
			}
		}
		updatePowerRate();
	}

	private void updatePowerRate()
	{
		maxPowerRate = 0;
		for (IConductor cable : conductorSet)
		{
			maxPowerRate += cable.getCableType().getTransferRate();
		}
	}

	public void merge(EnergyNetwork network)
	{
		if (network != null && network != this)
		{
			Set<EnergyNetwork> networks = new HashSet<>();
			networks.add(this);
			networks.add(network);
			EnergyNetwork newNetwork = new EnergyNetwork(networks);
			newNetwork.refresh();
		}
	}

	public void addAllCables(Set<IConductor> newCables)
	{
		conductorSet.addAll(newCables);
	}

	public void split(IConductor splitPoint)
	{
		if (splitPoint instanceof TileEntity)
		{
			removeCable(splitPoint);

			TileEntity[] connectedBlocks = new TileEntity[6];
			boolean[] dealtWith = { false, false, false, false, false, false };
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
			{
				TileEntity sideTile = new BlockLocation((TileEntity) splitPoint).TranslateTo(direction).getTile(((TileEntity) splitPoint).getWorldObj());
				if (sideTile != null)
				{
					connectedBlocks[Arrays.asList(ForgeDirection.values()).indexOf(direction)] = sideTile;
				}
			}
			for (int countOne = 0; countOne < connectedBlocks.length; countOne++)
			{
				TileEntity connectedBlockA = connectedBlocks[countOne];
				if (connectedBlockA instanceof IConductor && dealtWith[countOne] == false)
				{
					NetworkFinder finder = new NetworkFinder(((TileEntity) splitPoint).getWorldObj(), new BlockLocation(connectedBlockA), new BlockLocation[] { new BlockLocation((TileEntity) splitPoint) });
					List<BlockLocation> partNetwork = finder.exploreNetwork();
					for (int countTwo = countOne + 1; countTwo < connectedBlocks.length; countTwo++)
					{
						TileEntity connectedBlockB = connectedBlocks[countTwo];
						if (connectedBlockB instanceof IConductor && dealtWith[countTwo] == false)
						{
							if (partNetwork.contains(new BlockLocation(connectedBlockB)))
							{
								dealtWith[countTwo] = true;
							}
						}
					}
					EnergyNetwork newNetwork = new EnergyNetwork(new IConductor[0]);
					for (BlockLocation node : finder.iterated)
					{
						TileEntity nodeTile = node.getTile(((TileEntity) splitPoint).getWorldObj());
						if (nodeTile instanceof IConductor)
						{
							if (nodeTile != splitPoint)
							{
								newNetwork.conductorSet.add((IConductor) nodeTile);
							}
						}
					}
					newNetwork.refresh();
				}
			}
			deregister();
		}
	}

	public void fixMessedUpNetwork(IConductor cable)
	{
		System.out.println("Fixing Network");
		if (cable instanceof TileEntity)
		{
			NetworkFinder finder = new NetworkFinder(((TileEntity) cable).getWorldObj(), new BlockLocation((TileEntity) cable));
			List<BlockLocation> partNetwork = finder.exploreNetwork();
			Set<IConductor> newCables = new HashSet<>();
			for (BlockLocation node : partNetwork)
			{
				TileEntity nodeTile = node.getTile(((TileEntity) cable).getWorldObj());
				if (nodeTile instanceof IConductor)
				{
					((IConductor) nodeTile).removeFromNetwork();
					newCables.add((IConductor) nodeTile);
				}
			}
			EnergyNetwork newNetwork = new EnergyNetwork(newCables.toArray(new IConductor[0]));
			newNetwork.refresh();
			newNetwork.fixed = true;
			deregister();
		}
	}

	public void removeCable(IConductor cable)
	{
		conductorSet.remove(cable);
		if (conductorSet.size() == 0)
		{
			deregister();
		}
	}

	public void deregister()
	{
		conductorSet.clear();
		EnergyNetworkRegistry.getInstance().removeNetwork(this);
	}

	public static class NetworkFinder {
		public World				worldObj;
		public BlockLocation		start;
		public List<BlockLocation>	iterated	= new ArrayList<>();
		public List<BlockLocation>	toIgnore	= new ArrayList<>();

		public NetworkFinder(World world, BlockLocation location, BlockLocation... ignore) {
			worldObj = world;
			start = location;
			if (ignore.length > 0)
			{
				toIgnore = Arrays.asList(ignore);
			}
		}

		public void loopAll(BlockLocation location)
		{
			if (location.getTile(worldObj) instanceof IConductor)
			{
				iterated.add(location);
			}
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
			{
				BlockLocation obj = location.TranslateTo(direction);
				if (!iterated.contains(obj) && !toIgnore.contains(obj))
				{
					TileEntity tileEntity = obj.getTile(worldObj);
					if (tileEntity instanceof IConductor)
					{
						loopAll(obj);
					}
				}
			}
		}

		public List<BlockLocation> exploreNetwork()
		{
			loopAll(start);

			return iterated;
		}
	}

	public static class NetworkLoader {
		@SubscribeEvent
		public void onChunkLoad(ChunkEvent.Load event)
		{
			if (event.getChunk() != null)
			{
				for (Object obj : event.getChunk().chunkTileEntityMap.values())
				{
					if (obj instanceof TileEntity)
					{
						TileEntity tileEntity = (TileEntity) obj;
						if (tileEntity instanceof IConductor)
						{
							((IConductor) tileEntity).refreshNetwork();
						}
					}
				}
			}
		}
	}

	public void tick()
	{
		clearJoulesTransmitted();
		if (!fixed)
		{
			ticksSinceNetworkCreate += 1;
			if (ticksSinceNetworkCreate > 1200)
			{
				ticksSinceNetworkCreate = 0;
				fixMessedUpNetwork(conductorSet.toArray(new IConductor[0])[0]);
			}
		}

	}

	public void clearJoulesTransmitted()
	{
		energyTransmittedLastTick = energyTransmittedBuffer;
		energyTransmittedBuffer = 0;
	}

}
